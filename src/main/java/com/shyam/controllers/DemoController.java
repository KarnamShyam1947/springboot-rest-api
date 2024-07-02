package com.shyam.controllers;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

// @CrossOrigin
@RestController
@RequiredArgsConstructor
@Tag(
    name = "Demo Controller",
    description = "This controller for testing purpose"
)
public class DemoController {

    @GetMapping("/")
    public String home() {
        return "Home";
    }
    
    @GetMapping("/about")
    public Object about() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @GetMapping("/public")
    public Object publicMethod() {
        return Map.of("test", "ok");
    }
}
