package com.example.youcontribute.configuration;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GithubClientConfiguration {
    @Bean
    public GitHubClient gitHubClient(final GithubProperties githubProperties) {
        GitHubClient gitHubClient = new GitHubClient();
        gitHubClient.setOAuth2Token(githubProperties.getToken());
        return gitHubClient;
    }
}
