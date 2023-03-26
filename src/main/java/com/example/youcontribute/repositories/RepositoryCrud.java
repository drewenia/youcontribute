package com.example.youcontribute.repositories;

import com.example.youcontribute.models.RepositoryModel;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface RepositoryCrud extends ListCrudRepository<RepositoryModel, Integer> {

    /* asagida ki ornek gercek bir sql sorgusu calistirir. sql syntax'inda */
    /* naviteQuery parametresi verilmeseydi hibernate'e özel query gonderilecekti */
    /*
        @Query(value = "select * from RepositoryModel",nativeQuery = true)
        List<RepositoryModel> findAll;
    */
    List<RepositoryModel> findAll();

    Optional<RepositoryModel> findByOrganizationAndRepository(String organization, String repository);
}
