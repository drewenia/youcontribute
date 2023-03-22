package com.example.youcontribute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
/* Spring ayaga kalkarken scheduling ile ilgili islem yapacagimi anlayacak
* Normalde bir configuration class'i gibi de tanimlanabilir
* */
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
