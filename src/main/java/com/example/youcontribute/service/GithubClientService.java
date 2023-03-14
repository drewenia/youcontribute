package com.example.youcontribute.service;

import lombok.AllArgsConstructor;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GithubClientService {
    private final GitHubClient gitHubClient;
}
