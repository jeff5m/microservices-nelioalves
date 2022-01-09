package com.devsuperior.hroauth.services;

import com.devsuperior.hroauth.entities.User;
import com.devsuperior.hroauth.feignclients.UserFeignClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserFeignClient userFeignClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserService(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    public User findByEmail(String email) throws IllegalAccessException {
        ResponseEntity<?> response;
        try {
            response = userFeignClient.findByEmail(email);
        } catch (FeignException ex) {
            LOGGER.error("Error requesting User with email {} => Status: {}", email, ex.status());
            throw new IllegalAccessException("Error requesting user");
        }

        User user = objectMapper.convertValue(response.getBody(), new TypeReference<>() {
        });

        LOGGER.info("User found with email => {}", email);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<?> response;
        try {
            response = userFeignClient.findByEmail(username);
        } catch (FeignException ex) {
            LOGGER.error("Error requesting User with username {} => Status: {}", username, ex.status());
            throw new UsernameNotFoundException("Error requesting user");
        }

        User user = objectMapper.convertValue(response.getBody(), new TypeReference<>() {
        });

        LOGGER.info("User found with username => {}", username);
        return user;
    }
}
