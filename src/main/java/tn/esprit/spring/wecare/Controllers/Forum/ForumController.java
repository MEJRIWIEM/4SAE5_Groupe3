package tn.esprit.spring.wecare.Controllers.Forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Post;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Services.Forum.PostServiceImp;

@RestController
@RequestMapping("/api/forumCrud")
public class ForumController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostServiceImp PostService;
	
	@PostMapping("/addPost")
	public void addPost(@RequestBody Post post) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
		User us= userRepository.findByUsername(username).orElse(null);
		PostService.addPost(post,us);
		
	}
	

}
