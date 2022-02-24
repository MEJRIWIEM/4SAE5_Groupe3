package tn.esprit.spring.wecare.Controllers.Rewards;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Rewards.Badge;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Services.Rewards.BadgeServiceImp;

@RestController
public class BadgeController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	BadgeServiceImp BadgeService;
	
	@PostMapping("/addBadge")
	public void addBadge(@RequestBody Badge badge) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
		User us= userRepository.findByUsername(username).orElse(null);
		BadgeService.addBadge(badge,us);
		
	}
	// see the list of badges
		@GetMapping("/ListOfBadges")
		public List<Badge> RetrieveBadges() {
			return BadgeService.RetrieveBadges();
		}

		// see my badges
		@GetMapping("/ListOfMyBadges")
		public List<Badge> RetrieveMyBadges() {
			String username;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}
			User us = userRepository.findByUsername(username).orElse(null);
			return BadgeService.RetrieveMyBadges(us);
		}

		// see a specific badge with his id
		@GetMapping("/RetriveBadge/{id}")
		public Badge RetrieveBadge(@PathVariable("id") Long id) {
			return BadgeService.RetrieveBadge(id);
		}

		// delete his badge
		@DeleteMapping("/DeleteBadge/{id}")
		public ResponseEntity<Object> DeleteBadge(@PathVariable("id") Long id) {
			String username;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}
			User us = userRepository.findByUsername(username).orElse(null);
			return BadgeService.DeleteBadge(id, us);
		}

		// edit his badge
		@PutMapping("/EditBadge/{id}")
		public ResponseEntity<Object> EditBadge(@PathVariable("id") Long id, @RequestBody Badge badge) {
			String username;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}
			User us = userRepository.findByUsername(username).orElse(null);
			return BadgeService.EditBadge(id, us, badge);
		}
}
