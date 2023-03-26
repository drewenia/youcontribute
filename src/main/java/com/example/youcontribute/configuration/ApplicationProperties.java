package com.example.youcontribute.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationProperties {
    /* application yml icerisinde ki import-frequency degerini buraya map ediyoruz */
    public Integer importFrequency;
}
