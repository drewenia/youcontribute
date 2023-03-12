package com.example.youcontribute.controllers.requests;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
/* Bu class request'ler için kullanılıyor */
public class RepositoryRequest {
    String name;
    String organization;
}
