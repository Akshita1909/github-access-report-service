package com.example.github_access_service.client;

import com.example.github_access_service.model.Repo;
import com.example.github_access_service.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class GitHubClient {

    private final WebClient webClient;

    // 🔥 Inject token from application.properties
    public GitHubClient(WebClient.Builder builder,
                        @Value("${github.token}") String token) {

        this.webClient = builder
                .baseUrl("https://api.github.com")
                .defaultHeader("Authorization", "Bearer " + token)   // ✅ AUTH FIX
                .defaultHeader("Accept", "application/vnd.github.v3+json")
                .defaultHeader("User-Agent", "SpringBoot-App")
                .build();
    }

    // ✅ Fetch repositories of org
    public Flux<Repo> getRepos(String org) {
        return webClient.get()
                .uri("/orgs/{org}/repos", org)
                .retrieve()
                .bodyToFlux(Repo.class);
    }

    // ✅ Fetch public members of org
    public Flux<User> getMembers(String org) {
        return webClient.get()
                .uri("/orgs/{org}/members", org)
                .retrieve()
                .bodyToFlux(User.class);
    }
}