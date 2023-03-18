package com.example.youcontribute.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/* application yml icerisinde ki name'i github olan key'i burada kullanmaya basliyoruz */
/* application.yml icerisinde ki github anahtarinin altinda ki token'i ve apiUrl'i otomatik olarak alacak */
@ConfigurationProperties(prefix = "github")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class GithubProperties {

    private String token;
    private String apiUrl;
}

