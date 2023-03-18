package com.example.youcontribute.service;

import com.example.youcontribute.configuration.GithubProperties;
import com.example.youcontribute.service.models.GithubIssueResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(SpringExtension.class)
/* Initializer olarak burada yarattigimiz initializer'i kullaniyoruz */
@ContextConfiguration(initializers = GithubClientServiceTest.Initializer.class)
@SpringBootTest(classes = {GithubClientService.class, RestTemplate.class, GithubProperties.class})
/* GithubProperties class icerisinde ki api-url ve token degerlerini kullaniyorum */
@EnableConfigurationProperties(value = GithubProperties.class)
public class GithubClientServiceTest {

    @Autowired
    private GithubClientService githubClientService;

    /* WireMockServer spring icin gelistirilen sanki github'a gidiyormus gibi bir sanal github yaratan mekanizmadir */
    public static WireMockServer wiremock = new WireMockServer(WireMockSpring.options().dynamicPort());

    @BeforeAll
    static void setupClass() {
        wiremock.start();
    }

    @AfterEach
    void after() {
        wiremock.resetAll();
    }

    @AfterAll
    static void clean() {
        wiremock.shutdown();
    }

    @Test
    public void it_should_list_issues() {
        /* issues.json dosyasini bu path ile ayni github uzerindeymisiz gibi test edip
        * gelen value json'mu ve status 200'mu artı token degeri ssshhh'mi diye kontrol ediyoruz
        * */
        //given
        wiremock.stubFor(get(urlPathEqualTo("/repos/octocat/Hello-World/issues"))
                .withHeader("Authorization", equalTo("token ssshhh"))
                .willReturn(aResponse().withBodyFile("issues.json")
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)
                )
        );

        //when
        /* listIssues methodu ile gelen response'u once burada response object'i olarak aliyoruz */
        GithubIssueResponse[] response = this.githubClientService.listIssues("octocat", "Hello-World");

        //then
        /* gelen response bos degil mi? */
        then(response).isNotNull();
        /* issues.json'dan donen degerler 30'a esitmi */
        then(response.length).isEqualTo(30);
        /* tek bir response nesnesini burada aliyoruz */
        GithubIssueResponse githubIssueResponse = response[0];
        /* tek bir response icerisinde getTitle degiskeni Hello, world! mu? */
        then(githubIssueResponse.getTitle()).isEqualTo("Hello, world!");

    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            /* Wiremock'un otomatik url'sini burada override ediyoruz.
            * arti token bilgisi gonderiyoruz (test methodunda kullanilmak uzere
            * applicationContext uzerinden de getEnviroment methodu ile application.yml icerisinde ki degerlere ulasiyoruz
            * */
            TestPropertyValues.of("github.api-url=" + wiremock.baseUrl(),
                            "github.token=ssshhh")
                    .applyTo(applicationContext.getEnvironment());

        }
    }
}