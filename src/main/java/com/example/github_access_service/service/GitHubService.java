package com.example.github_access_service.service;

import com.example.github_access_service.client.GitHubClient;
import com.example.github_access_service.model.Repo;
import com.example.github_access_service.model.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class GitHubService {

    private final GitHubClient client;

    public GitHubService(GitHubClient client) {
        this.client = client;
    }

    public Mono<Map<String, List<String>>> getUserAccessMap(String org) {

        return client.getRepos(org)
                .collectList()
                .flatMap(repos ->
                        client.getMembers(org)
                                .collectList()
                                .map(users -> {

                                    Map<String, List<String>> result = new HashMap<>();

                                    for (User user : users) {
                                        List<String> repoNames = new ArrayList<>();

                                        for (Repo repo : repos) {
                                            repoNames.add(repo.getName());
                                        }

                                        result.put(user.getLogin(), repoNames);
                                    }

                                    return result;
                                })
                )
                .onErrorResume(e -> {
                    // fallback if GitHub API fails
                    Map<String, List<String>> fallback = new HashMap<>();
                    fallback.put("error", List.of("GitHub API failed: " + e.getMessage()));
                    return Mono.just(fallback);
                });
    }
}