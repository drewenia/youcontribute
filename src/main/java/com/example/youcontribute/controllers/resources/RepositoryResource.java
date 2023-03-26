package com.example.youcontribute.controllers.resources;

import com.example.youcontribute.models.RepositoryModel;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
/* Bu class response object'i yaratmak için kullanılıyor */
public class RepositoryResource {
    private String repository;
    private String organization;

    /* Builder design pattern'i kullanabilmek için Lombok'un @Builder anotasyonunu kullanıyoruz*/
    public static RepositoryResource createFor(RepositoryModel repositoryModel){
        return RepositoryResource.builder()
                .repository(repositoryModel.getRepository())
                .organization(repositoryModel.getOrganization())
                .build();
    }

    public static List<RepositoryResource> createFor(List<RepositoryModel> repositories){
        return repositories.stream().map(RepositoryResource::createFor).collect(Collectors.toList());
    }
}
