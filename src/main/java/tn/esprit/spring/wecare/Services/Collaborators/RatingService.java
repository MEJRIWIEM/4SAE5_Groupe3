package tn.esprit.spring.wecare.Services.Collaborators;

import java.util.List;

import org.springframework.http.ResponseEntity;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Rating;

public interface RatingService {
	
	public  ResponseEntity addRating(User user, Rating rating, Long id);
	public ResponseEntity DeleteRating(User user,  Long id);
	public ResponseEntity EditRating(User user,  Long id, Rating rating);
	
	public List<Rating> RetrieveRating();

}
