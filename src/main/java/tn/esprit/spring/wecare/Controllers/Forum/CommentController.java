package tn.esprit.spring.wecare.Controllers.Forum;

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
import tn.esprit.spring.wecare.Entities.Forum.Comment;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Services.Forum.CommentService;

@RestController
@RequestMapping("/api/Comment")
public class CommentController {
	@Autowired
	CommentService commentService;
	@Autowired
	UserRepository userRepository;
	@PostMapping("/addComment/{id}")
	public ResponseEntity<?>  CommentPost(@RequestBody Comment comment, @PathVariable("id") Long id){
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
		User us= userRepository.findByUsername(username).orElse(null);	
		return commentService.CommentPost(us, comment, id);
		
	}
	@DeleteMapping("/deleteComment/{id}")
	public ResponseEntity DeleteComment(@PathVariable("id") Long id) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
		User us= userRepository.findByUsername(username).orElse(null);	
		return commentService.DeleteComment(us, id);
	}
	@PutMapping("/updateComment/{id}")
	public ResponseEntity EditComment(@PathVariable("id") Long id, @RequestBody Comment comment) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
		User us= userRepository.findByUsername(username).orElse(null);	
		return commentService.EditComment(us, id, comment);

	}

	@GetMapping("/getComment/{id}")
	public String RetrieveCommentById(@PathVariable("id") Long id){
		return (String) commentService.RetrieveCommentById(id);
	}
}
