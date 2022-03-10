package tn.esprit.spring.wecare.Controllers.Collaborators;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import tn.esprit.spring.wecare.Entities.Collaborators.Advertising;
import tn.esprit.spring.wecare.Entities.Collaborators.Offer;
import tn.esprit.spring.wecare.Repositories.UserRepository;

import tn.esprit.spring.wecare.Services.Collaborators.CollaboratorService;
import tn.esprit.spring.wecare.Services.Collaborators.OfferService;

@RestController
@RequestMapping("/api/offer")
public class OfferController {
	
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	OfferService offerService;
	
	@Autowired

	CollaboratorService collaboratorService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addOffer/{id}")
	public ResponseEntity<?>  addOffer(@RequestBody Offer offer, @PathVariable("id") Long id){
		
		return offerService.addOffer( offer, id);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateOffer/{id}")
	public ResponseEntity EditOffer(@PathVariable("id") Long id, @RequestBody Offer offer) {
		
		return offerService.EditOffer( id, offer);

	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/DeleteOffer/{id}")
	public ResponseEntity DeleteOffer(@PathVariable("id") Long id) {
		
		return offerService.DeleteOffer( id);
	}
	
	
 	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/ListOfOffer")
	public List<Offer> RetrieveOffer() {
		return 	offerService.RetrieveOffer();
	}
 	
 	
 	
}
