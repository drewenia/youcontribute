package com.example.youcontribute.managers;

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
import java.time.ZoneId;
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

    /* Datayı import ettikten sonra issue'ları cekip db ye yazmasi gerekiyor */
    public void importRepository(String name, String organization) {
        this.repositoryService.create(name, organization);
    }

    @Async
    /* bir bean’in methoduna @Async annotationu eklemek onun main thread’den farklı olarak ayrı bir threadde çalıştırılmasını sağlar.
     * Yani call edilen methodun tamamlanmasını call eden kısım beklemez. Kod böylece async olarak çalışmış olur.
     */
    public void importIssues(RepositoryModel repositoryModel) {
        /* Bir onceki gunun issue'larini almaya calisacagiz */
        LocalDate sinceYesterday = LocalDate.ofInstant(Instant.now().minus(1, ChronoUnit.DAYS), ZoneId.systemDefault());

        GithubIssueResponse[] responses = this.githubClientService.listIssues(repositoryModel.getOrganization(), repositoryModel.getName(), sinceYesterday);
        List<IssueModel> issues = Arrays.stream(responses).map(issue -> IssueModel.builder().title(issue.getTitle()).body(issue.getBody()).build()).collect(Collectors.toList());
        issueService.saveAll(issues);
    }
}
