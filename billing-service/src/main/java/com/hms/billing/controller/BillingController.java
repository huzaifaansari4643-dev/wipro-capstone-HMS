package com.hms.billing.controller;

import com.hms.billing.dto.BillCreateRequest;
import com.hms.billing.dto.BillResponse;
import com.hms.billing.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService svc;

    @PostMapping
    public ResponseEntity<BillResponse> create(@RequestBody BillCreateRequest req,
                                               @RequestHeader("X-Auth-User-Role") String role) {
        if (!role.equalsIgnoreCase("PATIENT") && !role.equalsIgnoreCase("ADMIN")) {
            throw new AccessDeniedException("Only PATIENT/ADMIN allowed");
        }
        return ResponseEntity.ok(svc.create(req));
    }

    @PostMapping("/{billId}/pay")
    public ResponseEntity<BillResponse> pay(@PathVariable Long billId,
                                            @RequestHeader("X-Auth-User-Role") String role) {
        if (!role.equalsIgnoreCase("PATIENT") && !role.equalsIgnoreCase("ADMIN")) {
            throw new AccessDeniedException("Only PATIENT/ADMIN allowed");
        }
        return ResponseEntity.ok(svc.pay(billId));
    }

    @GetMapping
    public Page<BillResponse> list(Pageable pageable) {
        return svc.list(pageable);
    }
}
