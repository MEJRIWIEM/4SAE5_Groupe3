package tn.esprit.spring.wecare.Services.Forum;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Comment;
import tn.esprit.spring.wecare.Entities.Forum.Likes;
import tn.esprit.spring.wecare.Entities.Forum.Notification;
import tn.esprit.spring.wecare.Entities.Forum.Post;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Repositories.Forum.LikesRepository;
import tn.esprit.spring.wecare.Repositories.Forum.NotificationRepository;
import tn.esprit.spring.wecare.Repositories.Forum.PostRepository;
@Service
public class LikeServiceImp implements LikesService{



	@Autowired
	PostRepository postRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	LikesRepository likesRepository;
	@Override
	public ResponseEntity LikePost(User user, Long id) {
		Notification notification = new Notification();
		Likes like = new Likes();
		List<Post> posts = postRepository.findAll();

		 for(Post p: posts){
			 if(p.getIdPost().equals(id))
			 {
				 like.setPost(p);
				 like.setPost(p);
				 like.setUser(user);
				 like.setTimestamp(LocalDateTime.now());
				// like.setNotification(notification);
			
			likesRepository.save(like);
			/*
			 notification.setLike(like);
			 notification.setUser(p.getUser());
			 notificationRepository.save(notification);*/
			 
			 user.setLikes(like);
			 userRepository.save(user);
			 return new ResponseEntity<String>("Post was liked successfully!",HttpStatus.OK);
			 }}
		 
		 return new ResponseEntity<String>("Post was not liked!",HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity RemoveLikePost(User user, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
