package com.devsuperior.hruser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableEurekaClient
@SpringBootApplication
public class HrUserApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(HrUserApplication.class);

    private final BCryptPasswordEncoder bCrypt;

    public HrUserApplication(BCryptPasswordEncoder bCrypt) {
        this.bCrypt = bCrypt;
    }

    public static void main(String[] args) {
        SpringApplication.run(HrUserApplication.class, args);
    }


    @Override
    public void run(String... args) {
        String encodedPassword = bCrypt.encode("123456");
        LOGGER.info("BCRYPT = {}", encodedPassword);
    }

}
