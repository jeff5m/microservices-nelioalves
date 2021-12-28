package com.devsuperior.hrworker.controllers;

import com.devsuperior.hrworker.entities.Worker;
import com.devsuperior.hrworker.repositories.WorkerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workers")
public class WorkerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerController.class);

    private final WorkerRepository workerRepository;
    private final Environment env;

    public WorkerController(WorkerRepository workerRepository, Environment env) {
        this.workerRepository = workerRepository;
        this.env = env;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Worker>> findAll() {
        return ResponseEntity.ok(workerRepository.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id) {

        LOGGER.info("Processing request with service running on port => {}", env.getProperty("local.server.port"));

        Optional<Worker> optionalWorker = workerRepository.findById(id);

        if (optionalWorker.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalWorker.get());
    }

}
