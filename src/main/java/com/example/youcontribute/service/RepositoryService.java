package com.example.youcontribute.service;

import com.example.youcontribute.exception.DuplicatedRepositoryException;
import com.example.youcontribute.models.RepositoryModel;
import com.example.youcontribute.repositories.RepositoryCrud;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryService {

    /* Spring'in @Autowired anotasyonu yerine daima Constructor injection tercih edilmelidir */
    private final RepositoryCrud repositoryCrud;

    public RepositoryService(RepositoryCrud repositoryCrud) {
        this.repositoryCrud = repositoryCrud;
    }

    /* Bir repository create edildiginde gidip DB'ye yazmasi icin @Transactional anotasyonu kullanıldı */
    @Transactional
    public void create(String repository, String organization) {
        this.validate(organization, repository);
        RepositoryModel repositoryModel = RepositoryModel.builder()
                .repository(repository)
                .organization(organization)
                .build();
        this.repositoryCrud.save(repositoryModel);
    }

    public List<RepositoryModel> getRepositoryList() {
        return this.repositoryCrud.findAll();
    }

    private void validate(String organization, String repository) {
        this.repositoryCrud.findByOrganizationAndRepository(organization, repository)
                .ifPresent((r) -> {
                    throw new DuplicatedRepositoryException(organization, repository);
                });
    }

    public RepositoryModel findById(Integer repositoryId){
        return this.repositoryCrud.findById(repositoryId).orElseThrow(()-> new EntityNotFoundException("Repository not found"));
    }
}
