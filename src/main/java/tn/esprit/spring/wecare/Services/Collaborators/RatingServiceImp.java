package tn.esprit.spring.wecare.Services.Collaborators;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Rating;

@Service
public class RatingServiceImp  implements RatingService{

	@Override
	public ResponseEntity addRating(User user, Rating rating, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity DeleteRating(User user, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity EditRating(User user, Long id, Rating rating) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rating> RetrieveRating() {
		// TODO Auto-generated method stub
		return null;
	}

}
