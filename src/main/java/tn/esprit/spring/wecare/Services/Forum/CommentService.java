package tn.esprit.spring.wecare.Services.Forum;

import org.springframework.http.ResponseEntity;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Comment;

public interface CommentService {
	public  ResponseEntity CommentPost(User user, Comment comment, Long id);
	public ResponseEntity DeleteComment(User user,  Long id);
	public ResponseEntity EditComment(User user,  Long id, Comment comment);

}
