package tn.esprit.spring.wecare.Controllers.Collaborators;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Offer;
import tn.esprit.spring.wecare.Entities.Collaborators.Rating;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Services.Collaborators.RatingService;

@RestController
@RequestMapping("/api/rating")
public class RatingController {
	
	@Autowired
	RatingService ratingService;
	@Autowired
	UserRepository userRepository;

	@PostMapping("/ratingOffer/{id}")
	public ResponseEntity ratingOffer(@PathVariable("id") Long id ,@RequestBody Rating rating) {
		User us = getTheCurrentUser();
		return ratingService.ratingOffer(us,rating, id);
	}
	
	
	@PutMapping("/editRating/{id}")
	public ResponseEntity EditRating(@PathVariable("id") Long id, @RequestBody Rating rating) {
		
		return ratingService.EditRating( id, rating);

	}
	
	@DeleteMapping("/DeleteRating/{id}")
	public ResponseEntity DeleteRating(@PathVariable("id") Long id) {
		//User us = getTheCurrentUser();
		return ratingService.DeleteRating( id);
	}
	
	@GetMapping("/ListOfOfferRating")
	public List<Rating> RetrieveRating() {
		return 	ratingService.RetrieveRating();
	}
	
	@GetMapping("/ListOfMyOfferRating")
	public List<Rating> getMyRating() {
		User us = getTheCurrentUser();
		return ratingService.getMyRating(us);
	}

	//get the current user
		public User getTheCurrentUser() {
			String username;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}
			User us = userRepository.findByUsername(username).orElse(null);
			return us;
		}
	
		@GetMapping("/getRatingWithOfferId/{id}")
		public List<Rating> getRatingWithOfferId(@PathVariable("id") Long id) {
			return ratingService.getRatingWithOfferId(id);
		}
		
		@GetMapping("/AvgRatingByOffer/{id}")
		public Double AvgRatingByOffer(@PathVariable("id") Offer id) {
			return ratingService.AvgRatingByOffer(id);
		}

		
		

}
