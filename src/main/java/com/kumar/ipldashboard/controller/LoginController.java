package com.kumar.ipldashboard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @GetMapping("/login")
    public String test()
    {
        log.info("Into login function");
        return "Loggin";
    }
}
