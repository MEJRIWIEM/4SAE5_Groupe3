package tn.esprit.spring.wecare.Repositories.Collaborators;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.Collaborators.Collaborator;

@Repository
public interface CollaboratorRepository extends  JpaRepository<Collaborator,Long> {

	
	List<Collaborator> findByName(String name);
	  
	 List<Collaborator> findByNameContaining(String name);
	 
	 // List<Collaborator> findByManufacturerAndCategory
	      // (String offers, String category);
}
