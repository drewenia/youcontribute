package com.example.youcontribute.controllers;

import com.example.youcontribute.controllers.requests.RepositoryRequest;
import com.example.youcontribute.controllers.resources.IssueResource;
import com.example.youcontribute.service.IssueService;
import com.example.youcontribute.service.RepositoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping
    public List<IssueResource> getIssueList(@RequestParam("repository_id") Integer repositoryId) {
        return IssueResource.createFor(this.issueService.getIssues(repositoryId));
    }
}