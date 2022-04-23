package tn.esprit.spring.wecare;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class WecareApplication {

	public static void main(String[] args) {
		SpringApplication.run(WecareApplication.class, args);
	}

}
