package com.example.youcontribute.schedulers;

import com.example.youcontribute.managers.RepositoryManager;
import com.example.youcontribute.models.RepositoryModel;
import com.example.youcontribute.service.RepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ImportIssuesScheduler {

    private final RepositoryService repositoryService;

    private final RepositoryManager repositoryManager;

    /* application.yml icerisinde 60000ms yani 1 dakikada bir calisacak
     * Baslangicta calismamasi icinde initialDelay belirtildi
     * */
    @Scheduled(fixedRateString = "${application.import-frequency}", initialDelay = 10000)
    public void importIssuesScheduler() {
        List<RepositoryModel> repositoryList = this.repositoryService.getRepositoryList();
        /* importIssues methodu Async olarak atanmış bir methoddur */
        repositoryList.forEach(repositoryManager::importIssues);
    }
}
