package tn.esprit.spring.wecare.Services.Forum;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Comment;
import tn.esprit.spring.wecare.Entities.Forum.Post;

public interface CommentService {
	public  ResponseEntity CommentPost(User user, Comment comment, Long id);
	public ResponseEntity DeleteComment(User user,  Long id);
	public ResponseEntity EditComment(User user,  Long id, Comment comment);
	public String RetrieveCommentById(Long id);
	public Set<Comment> commentsByPost( Post post);

	

}
