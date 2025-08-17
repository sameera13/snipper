package com.sinppr.snippets.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    // Public endpoint, accessible to anyone
    @GetMapping("/public")
    public String publicEndpoint() {
        return "Anyone can see this.";
    }

    // Private endpoint, should be secured via JWT or other authentication
    @GetMapping("/private")
    public String privateEndpoint() {
        return "Only logged-in users with Auth0 JWT can see this.";
    }
}
