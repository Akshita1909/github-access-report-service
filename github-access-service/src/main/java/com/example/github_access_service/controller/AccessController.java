package com.example.github_access_service.controller;

import com.example.github_access_service.service.GitHubService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/org")
public class AccessController {

    private final GitHubService service;

    public AccessController(GitHubService service) {
        this.service = service;
    }

    @GetMapping("/{org}/access-report")
    public Mono<Map<String, List<String>>> getAccessReport(@PathVariable String org) {
        return service.getUserAccessMap(org);
    }
}