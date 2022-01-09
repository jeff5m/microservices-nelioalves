package com.devsuperior.hroauth.controllers;

import com.devsuperior.hroauth.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByEmail(@RequestParam("email") String email) {

        try {
            return ResponseEntity.ok(userService.findByEmail(email));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
