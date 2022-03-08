package tn.esprit.spring.wecare.Repositories.Collaborators;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.Collaborators.Offer;



@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {
	
	//@Query("SELECT AVG(r.ratings) FROM Offer r ")
	//@Query("SELECT AVG(r.value) FROM Rating r ")
	//public Double AvgRatingByOffer(Offer id);
	
	

}
