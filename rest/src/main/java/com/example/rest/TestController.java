package com.example.rest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping(value = "test")
    String test(HttpServletRequest request) {
        Cookie sessionCookie = WebUtils.getCookie(request, "SESSION");
        return "Session: " + (sessionCookie == null ? "" : sessionCookie.getValue());
    }
}
