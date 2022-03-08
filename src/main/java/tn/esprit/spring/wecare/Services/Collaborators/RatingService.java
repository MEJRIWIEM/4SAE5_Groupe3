package tn.esprit.spring.wecare.Services.Collaborators;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Offer;
import tn.esprit.spring.wecare.Entities.Collaborators.Rating;

public interface RatingService {
	
	public  ResponseEntity ratingOffer(User user,Rating rating, Long id);
	public ResponseEntity DeleteRating(  Long id);
	public ResponseEntity EditRating(  Long id, Rating rating);
	
	public List<Rating> RetrieveRating();
	
	public List<Rating> getMyRating( User user);
	
	public List<Rating>  getRatingWithOfferId(Long id);
	
	public Double  AvgRatingByOffer(Offer id);
	
	public Integer nbrOfRatingUserByOffer(Offer id);
	
	

}
