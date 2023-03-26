package com.example.youcontribute.controllers;

import com.example.youcontribute.controllers.requests.RepositoryRequest;
import com.example.youcontribute.controllers.resources.RepositoryResource;
import com.example.youcontribute.service.RepositoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("repositories")
/* Bu class REST isteklerine cevap verecek olan Controller class'ı */
public class RepositoryController {

    /* Spring'de 2 tip injection yöntemi bulunmaktadır
     * 1 - @Autowired anotasyonu ile (constructor injection'a gerek kalmamaktadır. Buna field injection adı verilir
     * 2 - Constructor injection aşağı da kullandığımız şekli ile
     */
    /* Constructor Injection yapıyorum */
    private final RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    /* Bu request arkada bir resource yaratacak ama tamamen asenkron
     * Bu request'in sonucu ile ilgilenmiyorum. Request alındı dönmesi lazım...
     * Bunun için flux ya da mono kullanılabilir
     */
    @PostMapping/*RequestMapping'in kısa yolu olarak kullanılmaktadır'*/
    @ResponseStatus(HttpStatus.CREATED) /* method biterse status olarak created döndür */
    /* Tamam ben bunu yaratacağım fakat benden birşey bekleme anlamına geliyor dolasıyla method void */
    public void createRepository(@RequestBody RepositoryRequest request) {
        this.repositoryService.create(request.getRepository(), request.getOrganization());
    }

    @GetMapping
    public List<RepositoryResource> getRepositoryList() {
        return RepositoryResource.createFor(this.repositoryService.getRepositoryList());
    }
}