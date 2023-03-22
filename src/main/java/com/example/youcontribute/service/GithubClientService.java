package com.example.youcontribute.service;

import com.example.youcontribute.configuration.GithubProperties;
import com.example.youcontribute.service.models.GithubIssueResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class GithubClientService {
    /* RestTemplate HTTP isteklerini gerçekleştirmek için senkronize bir istemcidir
     * RestTemplate, HTTP yöntemiyle ortak senaryolar için şablonlar sunar
     * Spring projelerimizde HTTP isteklerini yapıp yönetebileceğimiz Spring Boot Web kütüphanesinin içerisinde yer alan bir yapıdır.
     * Bu sayede bir client olarak bir servisten veri çekebiliriz.
     */
    private final RestTemplate restTemplate;

    /* githubProperties class'i application.yml icerisin deki degerleri karsilamak icin kullaniliyor */
    private final GithubProperties githubProperties;

    public GithubIssueResponse[] listIssues(String owner, String repository, LocalDate since) {
        /* HttpHeaders application.yml icerisinde ki token key'ini set etmektedir
         * HttpEntity bu header'lari icerisine alir ve bir Entity object'i olusturur
         * restTemplate.exchange bir response entity döner url alir, methodu alir, request'i veriyorsun ve cast edecegin type'i veriyorsun
         * exchange methodu geriye bir ResponseEntity dondurur
         * issueUrl'i string format ile formatliyorum ve ilk parametre olarak githubProperties'de ki api url'sini aliyorum
         * Diger parametreleri de kendim gececegim (owner,repository)
         * response'u getBody methoduyla donduruyorum
         * */
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token " + this.githubProperties.getToken());
        HttpEntity request = new HttpEntity(headers);
        String issuesUrl = String.format("%s/repos/%s/%s/issues?since=%s", this.githubProperties.getApiUrl(), owner, repository,since.toString());
        ResponseEntity<GithubIssueResponse[]> response = this.restTemplate.exchange(issuesUrl, HttpMethod.GET, request, GithubIssueResponse[].class);
        return response.getBody();
    }
}
