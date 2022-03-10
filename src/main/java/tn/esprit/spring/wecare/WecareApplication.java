package tn.esprit.spring.wecare;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling 


public class WecareApplication {
	


	public static void main(String[] args) {
		SpringApplication.run(WecareApplication.class, args);
	}
	
	
	
	
	
}
