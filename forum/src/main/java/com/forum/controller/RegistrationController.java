package com.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forum.dto.RegistrationDTO;
import com.forum.service.RegistrationService;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("api/registration")
public class RegistrationController {

	@Autowired
    private RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationDTO request) {
        return registrationService.register(request);
    }


}