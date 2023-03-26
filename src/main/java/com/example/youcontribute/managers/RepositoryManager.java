package com.example.youcontribute.managers;

import com.example.youcontribute.configuration.ApplicationProperties;
import com.example.youcontribute.models.IssueModel;
import com.example.youcontribute.models.RepositoryModel;
import com.example.youcontribute.service.GithubClientService;
import com.example.youcontribute.service.IssueService;
import com.example.youcontribute.service.RepositoryService;
import com.example.youcontribute.service.models.GithubIssueResponse;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
/* Manager class'i create edildi cunku issueService, repositoryService ve githubClientService ayri ayri service katmanlari
 * NonDB isler ayri bir yerde, DB ile ilgili isler ayri yerde halledilmelidir
 * */
/* N katmanli mimarilerde DB'lere ulasmak icin entityler model da yer alirlar
 * Bu datanin serialize edilip yazilmasi ve okunmasi kismini repository'ler hallederler.
 * Service katmani ise transaction yonetimi icin kullanilmaktadir. Transactionlar cok duzgun tasarlanmalidir
 *  */
public class RepositoryManager {
    /* Transactional context'i olan bir service'i transactional context'i olmayan bir client'dan ayırıyorum
     * Transaction'lari boyle manager class'larinda yonetmek best practice olarak tavsiye edilir
     * */

    private final IssueService issueService;
    private final RepositoryService repositoryService;
    private final GithubClientService githubClientService;
    private final ApplicationProperties applicationProperties;

    /* Datayı import ettikten sonra issue'ları cekip db ye yazmasi gerekiyor */
    public void importRepository(String name, String organization) {
        this.repositoryService.create(name, organization);
    }

    @Async
    /* bir bean’in methoduna @Async annotationu eklemek onun main thread’den farklı olarak ayrı bir threadde çalıştırılmasını sağlar.
     * Yani call edilen methodun tamamlanmasını call eden kısım beklemez. Kod böylece async olarak çalışmış olur.
     */
    public void importIssues(RepositoryModel repositoryModel) {
        /* application.yml icerisinde ki import-frequency degerini buraya alip 60000'e boldugumuzde 1 degerini alacagiz bu da 1 dk anlamina gelecek*/
        int importIssuesFrequencyInMinutes = applicationProperties.getImportFrequency() / 60000;
        /* Bir onceki gunun issue'larini almaya calisacagiz */
        /* ZoneOffset.UTC github icin gerekli idi cunku issue'lari kendi yerel saatine gore tutuyor ve aramizda +3 saat fark bulunmakta */
        LocalDate since = LocalDate.ofInstant(Instant.now().minus(importIssuesFrequencyInMinutes, ChronoUnit.MINUTES), ZoneOffset.UTC);

        GithubIssueResponse[] responses = this.githubClientService.listIssues(repositoryModel.getOrganization(), repositoryModel.getRepository(), since);
        List<IssueModel> issues = Arrays.stream(responses).map(issue -> IssueModel.builder().title(issue.getTitle()).body(issue.getBody()).build()).collect(Collectors.toList());
        issueService.saveAll(issues);
    }
}
