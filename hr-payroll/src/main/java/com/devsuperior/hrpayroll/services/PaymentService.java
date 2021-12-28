package com.devsuperior.hrpayroll.services;

import com.devsuperior.hrpayroll.entities.Payment;
import com.devsuperior.hrpayroll.entities.Worker;
import com.devsuperior.hrpayroll.feignclients.WorkerFeignClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentService {

    private final WorkerFeignClient workerFeignClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PaymentService(WorkerFeignClient workerFeignClient) {
        this.workerFeignClient = workerFeignClient;
    }

    public ResponseEntity<?> getPayment(Long workerId, Integer days) {

        ResponseEntity<?> response;
        try {
            response = workerFeignClient.findById(workerId);
        } catch (FeignException ex) {
            return ResponseEntity.status(ex.status()).build();
        }

        Worker worker = objectMapper.convertValue(response.getBody(), new TypeReference<>() {});

        if (Objects.isNull(worker)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new Payment(worker.getName(), worker.getDailyIncome(), days));
    }

}
