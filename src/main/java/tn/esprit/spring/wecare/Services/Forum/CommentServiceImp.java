package tn.esprit.spring.wecare.Services.Forum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.BadWordFilter;
import tn.esprit.spring.wecare.Entities.Forum.Comment;
import tn.esprit.spring.wecare.Entities.Forum.Notification;
import tn.esprit.spring.wecare.Entities.Forum.Post;
import tn.esprit.spring.wecare.Entities.Forum.StringSimilarity;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Repositories.Forum.CommentRepository;
import tn.esprit.spring.wecare.Repositories.Forum.NotificationRepository;
import tn.esprit.spring.wecare.Repositories.Forum.PostRepository;
@Service
public class CommentServiceImp implements CommentService{
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	PostRepository postRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	NotificationRepository notificationRepository;
	StringSimilarity stringSimilarity;
	
	@Override
	@Transactional
	public ResponseEntity  CommentPost(User user, Comment comment, Long id){
		Notification notification = new Notification();
		String output = BadWordFilter.getCensoredText(comment.getText());


		Post post =postRepository.getById(id);
		comment.setPost(post);
			comment.setText(output);
			comment.setPost(comment.getPost());
			comment.setUser(user);
			comment.setTimestamp(LocalDateTime.now());
			comment.setNotification(notification);
			commentRepository.save(comment);
			
			 notification.setComment(comment);
			 notification.setUser(comment.getPost().getUser());
			 notificationRepository.save(notification);
			 
			 user.getComments().add(comment);
			 userRepository.save(user);
			 return new ResponseEntity<String>("Post was commented successfully!",HttpStatus.OK);
			 
		 
	}

	@Override
	public ResponseEntity DeleteComment(User user, Long id) {
		List<Comment> comments = commentRepository.findAll();
		for(Comment c : comments)
		{
			 if(c.getIdComment().equals(id) && c.getUser().equals(user)){
				 commentRepository.deleteById(id);
				 return new ResponseEntity<String>("Comment deleted successfully!",HttpStatus.OK);

			 }

		}
		return new ResponseEntity<String>("Failed to delete comment!",HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity EditComment(User user, Long id, Comment comment) {
		List<Comment> comments = commentRepository.findAll();
		for(Comment c : comments)
		{
			 if(c.getIdComment().equals(id) && c.getUser().equals(user)){
				 c.setText(comment.getText());
				c.setTimestamp(LocalDateTime.now());
				 commentRepository.save(c);
				 return new ResponseEntity<String>("Comment edited successfully!",HttpStatus.OK);

			 }

		}
		return new ResponseEntity<String>("Failed to edit comment!",HttpStatus.BAD_REQUEST);
	}

	@Override
	public String RetrieveCommentById(Long id) {
		Comment comment = commentRepository.findById(id).orElse(null);
		String c= comment.getUser().getUsername() +" : "+comment.getText()  ;
		return c;
	}

	@Override
	public Set<Comment> commentsByPost(Post post) {
		
return (Set<Comment>) post.getComments();
}

	

}
