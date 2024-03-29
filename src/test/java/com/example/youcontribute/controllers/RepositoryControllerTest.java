package com.example.youcontribute.controllers;

import com.example.youcontribute.controllers.requests.RepositoryRequest;
import com.example.youcontribute.models.RepositoryModel;
import com.example.youcontribute.service.RepositoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/* Burada bir test calistiracagim ama Spring'in ozellikleri ile genisletiyorum */
/* WebMvcTest'de diyorum ki cok fazla component ile ugrasma benim muhattap olmak istediğim bu */
/* Yazdigimiz controller testlerinde, authentication tarzı seylere takilmamak için addFilters=false kullaniyorum */
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RepositoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RepositoryControllerTest {
    /* Spring'de mock diye bir kavram var
    Elinizde test yapmak istediğiniz bir hedef var bu bir class olabilir. Ve bu class'ın methodlarını test etmek istiyorsunuz
    Bu class'ın başka service'lere bağımlılığı var ise bunları mock'lamak gerekiyor
    * */
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean //context'e RepositoryController'in bağımlılığı olan RepositoryService'i bu şekilde ekliyorum
    private RepositoryService repositoryService;

    @Test
    public void it_should_list_repositories() throws Exception {
        RepositoryModel repositoryModel = RepositoryModel.builder()
                .organization("drewenia")
                .repository("youcontribute")
                .build();
        given(this.repositoryService.getRepositoryList())
                .willReturn(Collections.singletonList(repositoryModel));

        /* repositories uri adresine bir get gönderiyorum
        status ok olması lazım ve donen content'in içerisin de Hello World olması lazım
        Dolayısıyla bu test fail verecek cunku bizim content'imizde drewenia ya da youcontribute var
        * */
        /*this.mockMvc.perform(get("/repositories")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")));*/

        /* repositories linkinden name olarak youcontribute, value olarak da drewenia gelmesi gerekiyor */
        this.mockMvc.perform(get("/repositories")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].repository").value("youcontribute"))
                .andExpect(jsonPath("$[0].organization").value("drewenia"));
    }

    @Test
    public void it_should_create_repository() throws Exception {
        //given
        String organization = "github";
        String repository = "youcontribute";
        RepositoryRequest request = RepositoryRequest.builder()
                .organization(organization)
                .repository(repository)
                .build();

        /* create methodu bana birsey dondurmeyen bir method oldugu icin doNothing methodunu kullaniyorum */
        doNothing().when(this.repositoryService).create(repository, organization);

        //when
        /* Content denilen şey request object'inin content'e cevirilmis hali demektir
        * Bu map'i yapabilmek icin objectMapper kütüphanesi kullanılmaktadır
        * Content-Type APPLICATION_JSON
        * Accept APPLICATION_JSON
        * dönecek olan status code'u isCreated'mı?
        * */
        this.mockMvc.perform(post("/repositories")
                        .content(this.objectMapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated());
        //then
    }


}