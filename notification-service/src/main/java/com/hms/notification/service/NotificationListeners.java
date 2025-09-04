package com.hms.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationListeners {

    private final EmailService email;
    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "notification-topic")
    public void onAppointment(Object payload) {
        Map<?,?> json = mapper.convertValue(payload, Map.class);
        if ("APPOINTMENT_ACCEPTED".equals(json.get("eventType"))) {
            String to = (String) json.get("patientEmail");
            String doctorId = (String) json.get("doctorId");
            String when = String.valueOf(json.get("slot"));
            email.send(
                to,
                "Your appointment was accepted ✅",
                "<p>Your appointment with Doctor <b>"+doctorId+"</b> at <b>"+when+"</b> was accepted.</p>"
            );
        }
    }

    @KafkaListener(topics = "billing-topic")
    public void onBilling(Object payload) {
        Map<?,?> json = mapper.convertValue(payload, Map.class);
        if ("BILL_PAID".equals(json.get("eventType"))) {
            String to = (String) json.get("patientEmail");
            String total = String.valueOf(json.get("total"));
            email.send(
                to,
                "Payment received 🧾",
                "<p>We received your payment. Total: <b>"+total+"</b>. Thank you!</p>"
            );
        }
    }
}
