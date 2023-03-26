package com.example.youcontribute.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/* Bu class request'ler için kullanılıyor */
public class RepositoryRequest {
    String repository;
    String organization;
}
