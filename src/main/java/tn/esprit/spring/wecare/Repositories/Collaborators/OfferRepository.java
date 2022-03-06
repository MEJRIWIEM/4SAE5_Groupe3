package tn.esprit.spring.wecare.Repositories.Collaborators;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.Collaborators.Offer;



@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {
	
	

}
