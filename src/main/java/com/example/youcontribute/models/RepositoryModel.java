package com.example.youcontribute.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

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
    private String repository;
    private String organization;

    /* @CreationTimestamp bu alana kaydın oluşturulma tarihini atacağını belirtiyor. */
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    /* Bir tane repository'nin birden fazla issue'su olabilir */
    /* Eger repository istedigimiz de issue'larda icerisin de yer alsin istenirse fetch EAGER olarak secilmelidir */
    /* Burada repository'e tikladigimiz da issue'lar gelecek dolayisiyla eager kullanmiyoruz */
    /* Repository silindiginde issues'larin kalmasinin bir mantigi olmadigi icin cascade type remove olarak secildi */
    @OneToMany(mappedBy = "repository", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    /* Json verisi katmanlar arasinda tasinirken serialize hale gelmektedir
     * Buradaki tum deger tipleri seklinde deserialize edildikten sonra Set tipinde ki issues degiskenine geldiginde,
     * IssueModel class'ina gider. Ayni serialize islemi orada da baslar.
     * @JsonBackReference anotasyonu ile bu recursive birbirleri arasinda ki dolasim overflow hatasi vermeden devam ettirtir.
     * */
    @JsonBackReference
    private Set<IssueModel> issues;
}
