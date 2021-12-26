package com.devsuperior.hrpayroll.services;

import com.devsuperior.hrpayroll.entities.Payment;
import com.devsuperior.hrpayroll.entities.Worker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PaymentService {

    private final RestTemplate restTemplate;
    private final String workerServiceUrl;

    public PaymentService(RestTemplate restTemplate,
                          @Value("${hr-worker.host}") String workerServiceUrl) {
        this.restTemplate = restTemplate;
        this.workerServiceUrl = workerServiceUrl;
    }

    public Payment getPayment(Long workerId, Integer days) {
        Map<String, String> uriVariables = Map.of("id", workerId.toString());
        Worker worker = restTemplate.getForObject(workerServiceUrl + "/{id}", Worker.class, uriVariables);
        return new Payment(worker.getName(), worker.getDailyIncome(), days);
    }

}
