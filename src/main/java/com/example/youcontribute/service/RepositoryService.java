package com.example.youcontribute.service;

import com.example.youcontribute.models.RepositoryModel;
import com.example.youcontribute.repositories.RepositoryCrud;
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
    public void create(String name, String organization) {
        RepositoryModel repositoryModel = RepositoryModel.builder()
                .name(name)
                .organization(organization)
                .build();
        this.repositoryCrud.save(repositoryModel);
    }

    public List<RepositoryModel> getRepositoryList() {
        return this.repositoryCrud.findAll();
    }
}
