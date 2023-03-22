package com.example.youcontribute.service;

import com.example.youcontribute.models.IssueModel;
import com.example.youcontribute.repositories.IssueRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    @Transactional
    public void saveAll(List<IssueModel> issues) {
        this.issueRepository.saveAll(issues);
    }
}
