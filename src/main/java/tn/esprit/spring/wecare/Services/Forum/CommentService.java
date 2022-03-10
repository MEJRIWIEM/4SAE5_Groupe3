package tn.esprit.spring.wecare.Services.Forum;

import org.springframework.http.ResponseEntity;


public interface CommentService {
	public  ResponseEntity CommentPost(User user, Comment comment, Long id);
	public ResponseEntity DeleteComment(User user,  Long id);
	public ResponseEntity EditComment(User user,  Long id, Comment comment);
	public String RetrieveCommentById(Long id);

}
