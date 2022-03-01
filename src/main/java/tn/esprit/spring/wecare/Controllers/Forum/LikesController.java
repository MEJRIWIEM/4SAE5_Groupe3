package tn.esprit.spring.wecare.Controllers.Forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Services.Forum.LikesService;

@RestController
@RequestMapping("/api/Like")
public class LikesController {
	@Autowired
	LikesService likesService;
	@Autowired
	UserRepository userRepository;

	@PostMapping("/likePost/{id}")
	public ResponseEntity LikePost(@PathVariable("id") Long id) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User us = userRepository.findByUsername(username).orElse(null);
		return likesService.LikePost(us, id);
	}

	

}
