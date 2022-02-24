package tn.esprit.spring.wecare.Controllers.Forum;

import java.util.List;
import java.util.Set;

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

	// add a post
	@PostMapping("/addPost")
	public ResponseEntity<Object> addPost(@RequestBody Post post) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User us = userRepository.findByUsername(username).orElse(null);
		return PostService.addPost(post, us);

	}

	// see the list of posts
	@GetMapping("/ListOfPosts")
	public List<Post> RetrievePosts() {
		return PostService.RetrievePosts();
	}

	// see my posts
	@GetMapping("/ListOfMyPosts")
	public List<Post> RetrieveMyPosts() {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User us = userRepository.findByUsername(username).orElse(null);
		return PostService.RetrieveMyPosts(us);
	}

	// see a specific post with his id
	@GetMapping("/RetrivePost/{id}")
	public Post RetrievePost(@PathVariable("id") Long id) {
		return PostService.RetrievePost(id);
	}

	// delete his post
	@DeleteMapping("/DeletePost/{id}")
	public ResponseEntity<Object> DeletePost(@PathVariable("id") Long id) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User us = userRepository.findByUsername(username).orElse(null);
		return PostService.DeletePost(id, us);
	}

	// edit his post
	@PutMapping("/EditPost/{id}")
	public ResponseEntity<Object> EditPost(@PathVariable("id") Long id, @RequestBody Post post) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User us = userRepository.findByUsername(username).orElse(null);
		return PostService.EditPost(id, us, post);
	}

}
