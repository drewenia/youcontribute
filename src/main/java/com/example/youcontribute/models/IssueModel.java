package com.example.youcontribute.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    private String title;
    @Column(columnDefinition="text")
    private String body;
}
