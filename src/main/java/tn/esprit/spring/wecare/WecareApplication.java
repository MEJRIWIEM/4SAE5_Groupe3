package tn.esprit.spring.wecare;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import tn.esprit.spring.wecare.Entities.Collaborators.Collaborator;
import tn.esprit.spring.wecare.Repositories.Collaborators.CollaboratorRepository;

@SpringBootApplication
@EnableBatchProcessing


public class WecareApplication {
	
	//private static final String COMMA_DELIMITER = ",";

	//@Autowired
	//private ElasticsearchOperations esOps;

	//@Autowired
	//private CollaboratorRepository collaboratorRepository;

	public static void main(String[] args) {
		SpringApplication.run(WecareApplication.class, args);
	}
	
	
	
	//@PreDestroy
	//public void deleteIndex() {
	//	esOps.indexOps(Collaborator.class).delete();
	//}
	
	
	//@PostConstruct
	//public void buildIndex() {

	//	esOps.indexOps(Collaborator.class).refresh();
		//collaboratorRepository.deleteAll();
		//collaboratorRepository.saveAll(prepareDataset());
	//}

	
	
}
