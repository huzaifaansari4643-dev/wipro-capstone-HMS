package com.hms.billing.service;

import com.hms.billing.dto.BillCreateRequest;
import com.hms.billing.dto.BillResponse;
import com.hms.billing.entity.Bill;
import com.hms.billing.event.BillingEvent;
import com.hms.billing.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final BillRepository repo;
    private final KafkaTemplate<String, Object> kafka;

    @Transactional
    @Override
    public BillResponse create(BillCreateRequest req) {
        Bill bill = new Bill();
        bill.setAppointmentId(req.appointmentId());
        bill.setPatientId(req.patientId());
        bill.setPatientEmail(req.patientEmail());
        bill.setDrugsAmount(req.drugsAmount());
        bill.setConsultationFee(req.consultationFee());
        bill.setTotal(req.drugsAmount().add(req.consultationFee()));
        bill = repo.save(bill);
        return map(bill);
    }

    @Transactional
    @Override
    public BillResponse pay(Long billId) {
        Bill bill = repo.findById(billId).orElseThrow();
        bill.setStatus(Bill.Status.PAID);
        Bill saved = repo.save(bill);

        BillingEvent evt = new BillingEvent(
                saved.getId(),
                saved.getAppointmentId(),
                saved.getPatientId(),
                saved.getPatientEmail(),
                saved.getTotal(),
                saved.getStatus().name(),
                "BILL_PAID",
                Instant.now()
        );

        kafka.send("billing-topic", "bill-" + saved.getId(), evt);
        kafka.send("audit-topic", "bill-" + saved.getId(), evt);

        return map(saved);
    }

    @Override
    public Page<BillResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(this::map);
    }

    private BillResponse map(Bill b) {
        return new BillResponse(
                b.getId(),
                b.getAppointmentId(),
                b.getPatientId(),
                b.getPatientEmail(),
                b.getDrugsAmount(),
                b.getConsultationFee(),
                b.getTotal(),
                b.getStatus().name()
        );
    }
}
