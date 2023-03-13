package com.example.youcontribute.repositories;

import com.example.youcontribute.models.RepositoryModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

/* ExtendWith anotasyonu ile SpringExtension class'ina ait tum ozellikleri kullanir hale geliyoruz */
@ExtendWith(SpringExtension.class)
/* Jpa Repository'lerini test etmek icin kullanilan anotasyondur
 * Transaction islemi yapildiktan sonra rollback ile degisiklikler geri alinir
 * yapilandirmayi devre disi birakacak bunun yerine yalnızca JPA testleriyle ilgili yapilanmayi uygulayacaktir
 * test klasoru altinda ki resources icerisinde ki application.yml'yi baz alacaktir
 * default'da embedded database'leri kullanir fakat AutoConfigureTestDatabase anotasyonu ile kullanilirsa istedigimiz db'yi kullanabiliriz
 * */
@DataJpaTest
/* DataJpaTest anotasyonunda embedded database kullanmayacagimizi belirtmek icin kullanilir */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
/* entegrasyon testleri icin bir ApplicationContext'in nasil yuklenecegini, yapilandirilacagini belirlemek için kullanılan sınıf düzeyinde meta verileri tanımlar
 * XML ya da class based bir configuration secilebilir
 * */
@ContextConfiguration(initializers = RepositoryCrudTest_IT.Initializer.class)
/* application.yml icerisinde ki active profile'i secmemize yardim eder */
@ActiveProfiles("it")
/* Bu projede kullanilan test container'inin icerisin de mysql bulunmaktadir
* Bizim docker image'imiz olan mysql'e connect olur
* */
@Testcontainers
public class RepositoryCrudTest_IT {
    //her test case'inden once birsey calissin isteniyorsa bu anotasyon kullanilir
    //@BeforeEach
    //her testten sonra calisir
    //@AfterEach
    //testler baslamadan once bir kere calisiyor
    //@BeforeAll
    //testler calisiyor en son bir kere calisiyor
    //@AfterAll

    /* Docker container'inda kullandigimiz mysql isimli db'yi refere eder */
    public static final DockerImageName MYSQL_8 = DockerImageName.parse("mysql");

    /* Container burada create ediliyor */
    @Container
    public static MySQLContainer<?> mysqlContainer = new MySQLContainer<>(MYSQL_8);

    /* TestEntityManager ile db'ye object'imizi yaziyoruz */
    @Autowired
    private TestEntityManager testEntityManager;

    /*RepositoryCrud repository'miz */
    @Autowired
    private RepositoryCrud repositoryCrud;

    /* Initializer class'i ContextConfiguration anotasyonu icin kullanilmaktadir
    * ApplicationContextInitialize'i implemente eder
    * */
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        /* Burada bizim kendi docker container'imiz icerisinden url,username ve password'u ceker */
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                            "spring.datasource.url=" + mysqlContainer.getJdbcUrl(),
                            "spring.datasource.username=" + mysqlContainer.getUsername(),
                            "spring.datasource.password=" + mysqlContainer.getPassword()
                    )
                    .applyTo(applicationContext.getEnvironment());
        }
    }

    @Test
    public void it_should_find_all_repositories() {
        //given
        /* iki tane model object'i create ediyoruz */
        RepositoryModel repositoryModel1 = RepositoryModel.builder()
                .name("repo1")
                .organization("org1")
                .build();
        RepositoryModel repositoryModel2 = RepositoryModel.builder()
                .name("repo2")
                .organization("org2")
                .build();
        /* Bunlari saveAll ile kaydediyoruz */
        this.repositoryCrud.saveAll(Arrays.asList(repositoryModel1, repositoryModel2));
        /* ve db'ye flush ediyoruz */
        this.testEntityManager.flush();
        //when
        /* yazildimi diye kontrolunu yapiyoruz */
        List<RepositoryModel> repositories = this.repositoryCrud.findAll();
        //then
        /* yazilan gercekten 2 model mi? */
        then(repositories.size()).isEqualTo(2);
        RepositoryModel repo1 = repositories.get(0);
        RepositoryModel repo2 = repositories.get(1);
        /* iclerinde ki data dogru mu? */
        then(repo1.getName()).isEqualTo("repo1");
        then(repo1.getOrganization()).isEqualTo("org1");
        then(repo2.getName()).isEqualTo("repo2");
        then(repo2.getOrganization()).isEqualTo("org2");
    }
}

