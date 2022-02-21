package tn.esprit.spring.wecare.Services.Forum;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Comment;
import tn.esprit.spring.wecare.Entities.Forum.Post;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Repositories.Forum.CommentRepository;
import tn.esprit.spring.wecare.Repositories.Forum.PostRepository;
@Service
public class CommentServiceImp implements CommentService{
	@Autowired
	CommentRepository commentRepo;
	@Autowired
	PostRepository postRepository;
	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public ResponseEntity  CommentPost(User user, Comment comment, Long id){
		List<Post> posts = postRepository.findAll();

		 for(Post p: posts){
			 if(p.getIdPost().equals(id))
			comment.setPost(p);
			comment.setUser(user);
			//comment.setTimestamp(timestamp);
			 p.getComments().add(comment);
			 commentRepo.save(comment);
			 postRepository.save(p);
			 user.getComments().add(comment);
			 userRepository.save(user);
			 return new ResponseEntity<String>("Post was commented successfully!",HttpStatus.OK);
			 }
		 
		 return new ResponseEntity<String>("Post was not commented!",HttpStatus.CONFLICT);
	}

}
