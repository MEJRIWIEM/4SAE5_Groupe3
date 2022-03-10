package tn.esprit.spring.wecare.Controllers.Collaborators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Advertising;
import tn.esprit.spring.wecare.Entities.Collaborators.Collaborator;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Services.Collaborators.AdsService;
import tn.esprit.spring.wecare.Services.Collaborators.CollaboratorService;

@RestController
@RequestMapping("/api/ads")
public class AdsController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	CollaboratorService collaboratorService;
	
	@Autowired
	
	AdsService adsService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addAds/{id}")
	public ResponseEntity<?>  addAdvirtising(@RequestBody Advertising ads, @PathVariable("id") Long id){
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
		User us= userRepository.findByUsername(username).orElse(null);	
		return adsService.addAdvirtising(us, ads, id);
		
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateAds/{id}")
	public ResponseEntity EditAdvertising(@PathVariable("id") Long id, @RequestBody Advertising ads) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
		User us= userRepository.findByUsername(username).orElse(null);	
		return adsService.EditAdvertising(us, id, ads);

	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/DeleteAds/{id}")
	public ResponseEntity DeleteAdvertising(@PathVariable("id") Long id) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
		User us= userRepository.findByUsername(username).orElse(null);	
		return adsService.DeleteAdvertising(us, id);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/ListOfAds")
	public List<Advertising> RetrieveAds() {
		return 	adsService.RetrieveAds();
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/GetAllAds")
	public ResponseEntity getAllAds(@RequestParam(required = false) String name,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size) {
		return 	adsService.getAllAds(name, page, size);
		
	}

	
}
