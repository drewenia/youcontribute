package com.example.youcontribute.service;

import com.example.youcontribute.models.IssueModel;
import com.example.youcontribute.models.RepositoryModel;
import com.example.youcontribute.repositories.IssueRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;

    private final RepositoryService repositoryService;

    @Transactional
    public void saveAll(List<IssueModel> issues) {
        this.issueRepository.saveAll(issues);
    }

    public List<IssueModel> getIssues(Integer repositoryId) {
        RepositoryModel repositoryModel = this.repositoryService.findById(repositoryId);
        return this.issueRepository.findByRepository(repositoryModel);
    }
}
