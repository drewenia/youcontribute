package com.example.youcontribute.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
/* application yml icerisinde ki name'i github olan key'i burada kullanmaya basliyoruz */
@ConfigurationProperties(prefix = "github")
@Getter
public class GithubProperties {
    /* application.yml icerisnde ki github anahtarinin altinda ki token'i otomatik olarak alacak */
    private String token;
}
