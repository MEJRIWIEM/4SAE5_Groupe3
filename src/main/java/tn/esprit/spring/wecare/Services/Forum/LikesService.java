package tn.esprit.spring.wecare.Services.Forum;

import org.springframework.http.ResponseEntity;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Comment;
import tn.esprit.spring.wecare.Entities.Forum.Likes;

public interface LikesService {
	public  ResponseEntity LikePost(User user,  Long id);
}
