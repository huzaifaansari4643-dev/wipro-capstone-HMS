package com.hms.billing.service;

import com.hms.billing.dto.BillCreateRequest;
import com.hms.billing.dto.BillResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BillingService {
    BillResponse create(BillCreateRequest req);
    BillResponse pay(Long billId);
    Page<BillResponse> list(Pageable pageable);
}
