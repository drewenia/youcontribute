package com.example.youcontribute.models;

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
/* Class entity olarak işaretlendiğinden dolayı DB'de otomatik olarak create edilecek */
/* Bu class ayni zamanda model class'i olarak calisacak */
public class RepositoryModel {
    /* Database'e birsey yazacak isem model class'im bu olacak */

    @Id
    @GeneratedValue /* strategy belirtilmediginde default olarak AUTO calisacaktir */
    private Integer id;
    private String name;
    private String organization;
}
