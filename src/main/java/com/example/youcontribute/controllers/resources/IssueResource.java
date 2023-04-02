package com.example.youcontribute.controllers.resources;

import com.example.youcontribute.models.IssueModel;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
/* Bu class response object'i yaratmak için kullanılıyor */
public class IssueResource {

    private Integer id;
    private Long githubIssueId;
    private String title;
    private String body;
    private String url;
    private Integer issueNumber;

    /* Builder design pattern'i kullanabilmek için Lombok'un @Builder anotasyonunu kullanıyoruz*/
    public static IssueResource createFor(IssueModel issueModel) {
        return IssueResource.builder()
                .id(issueModel.getId())
                .title(issueModel.getTitle())
                .body(issueModel.getBody())
                .githubIssueId(issueModel.getGithubIssueId())
                .url(issueModel.getUrl())
                .issueNumber(issueModel.getIssueNumber())
                .build();
    }

    public static List<IssueResource> createFor(List<IssueModel> issueModel) {
        return issueModel.stream().map(IssueResource::createFor).collect(Collectors.toList());
    }

}