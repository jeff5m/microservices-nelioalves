package com.devsuperior.hroauth.services;

import com.devsuperior.hroauth.entities.User;
import com.devsuperior.hroauth.feignclients.UserFeignClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserFeignClient userFeignClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserService(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    public ResponseEntity<?> findByEmail(String email) {
        ResponseEntity<?> response;
        try {
            response = userFeignClient.findByEmail(email);
        } catch (FeignException ex) {
            LOGGER.error("Error requesting User with email {} => Status: {}", email, ex.status());
            return ResponseEntity.status(ex.status()).build();
        }

        User user = objectMapper.convertValue(response.getBody(), new TypeReference<>() {});

        LOGGER.info("User found with email => {}", email);
        return ResponseEntity.ok(user);
    }
}
