package com.devsuperior.hrpayroll.controllers;

import com.devsuperior.hrpayroll.entities.Payment;
import com.devsuperior.hrpayroll.services.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @HystrixCommand(fallbackMethod = "getPaymentByWorkerIdAndDaysFallback")
    @GetMapping(value = "/{worker-id}/days/{days}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPaymentByWorkerIdAndDays(@PathVariable(name = "worker-id") Long workerId,
                                                         @PathVariable(name = "days") Integer days) {
        return paymentService.getPayment(workerId, days);
    }

    public ResponseEntity<?> getPaymentByWorkerIdAndDaysFallback(Long workerId, Integer days) {
        return ResponseEntity.ok().body(new Payment("Brann", 400.0, days));
    }

}
