package tn.esprit.spring.wecare.Controllers.Rewards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.wecare.Configuration.Files.FileStorageService;
import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.EmployeeList.EmployeeList;
import tn.esprit.spring.wecare.Entities.Forum.Post;
import tn.esprit.spring.wecare.Entities.Rewards.Badge;
import tn.esprit.spring.wecare.Payloads.Responses.MessageResponse;
import tn.esprit.spring.wecare.Repositories.SaveEmployeeToDb;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Services.Rewards.BadgeServiceImp;

@RestController
public class BadgeController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	BadgeServiceImp BadgeService;
	@Autowired
	private FileStorageService storageService;
	@Autowired
    private JavaMailSender emailSender;
	@Autowired
    SaveEmployeeToDb employeeRepo;
	
	// add a post with a file
	@PostMapping("/addBadge")
	public ResponseEntity<Object> addBadgeUploadFile(@RequestPart(value = "file", required = false) MultipartFile file,
			@RequestPart("badge") Badge badge) throws IOException {
		User us = getTheCurrentUser();
		return BadgeService.addBadge(file, badge,us);


	}
	
	
	@PostMapping("/affecterBadgeUser/{id}")
	public void affecterBadgeUser(@PathVariable("id") Long id) {
		User us = getTheCurrentUser();
		BadgeService.affecterBadgeUser(id, us);
		
	}
	// see the list of badges
		@GetMapping("/ListOfBadges")
		public List<Badge> RetrieveBadges() {
			return (List<Badge>) BadgeService.RetrieveBadgesWithFile();
		}

		// see my badges
		@GetMapping("/ListOfMyBadges")
		public List<Badge> RetrieveMyBadges() {
			User us = getTheCurrentUser();
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
			User us = getTheCurrentUser();
			return BadgeService.DeleteBadge(id, us);
		}

		// edit his badge
		@PutMapping("/EditBadge/{id}")
		public ResponseEntity<Object> EditPost(@RequestPart(value = "file", required = false) MultipartFile file,@PathVariable("id")Long id, @RequestPart("badge") Badge badge) throws IOException {
			User us = getTheCurrentUser();
			return BadgeService.EditBadge(file, id, us, badge);
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
		
		
		



	

}
