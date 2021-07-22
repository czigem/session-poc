package com.example.rest;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping(value = "test")
    String test(@CookieValue(value = "SESSION", defaultValue = "") String session) {
        return "Session: " + session;
    }
}
