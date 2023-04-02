package com.example.youcontribute.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssueModel {
    @Id
    @GeneratedValue /* strategy belirtilmediginde default olarak AUTO calisacaktir */
    private Integer id;

    private Integer issueNumber;
    /* github'dan gelecek olan id icin bu field kullanilacak */
    private Long githubIssueId;

    private String title;
    @Column(columnDefinition = "text")

    private String body;

    private String url;

    /* Bir tane repository'nin birden fazla issue'su olabilir */
    @ManyToOne
    @JoinColumn
    /* Json verisi katmanlar arasinda tasinirken serialize hale gelmektedir
     * Buradaki tum deger tipleri seklinde deserialize edildikten sonra RepositoryModel tipinde ki degiskene gelir,
     * RepositoryModel class'ina gider. Ayni serialize islemi orada da baslar.
     * @JsonManagedReference anotasyonu ile bu recursive birbirleri arasinda ki dolasim overflow hatasi vermeden devam ettirtir.
     * */
    @JsonManagedReference
    private RepositoryModel repository;
}
