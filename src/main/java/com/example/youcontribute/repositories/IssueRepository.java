package com.example.youcontribute.repositories;

import com.example.youcontribute.models.IssueModel;
import com.example.youcontribute.models.RepositoryModel;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface IssueRepository extends ListCrudRepository<IssueModel, Integer> {
    List<IssueModel> findAll();

    List<IssueModel> findByRepository(RepositoryModel repositoryModel);
}
