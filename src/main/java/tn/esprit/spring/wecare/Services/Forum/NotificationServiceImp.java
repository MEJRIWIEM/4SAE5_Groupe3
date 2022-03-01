package tn.esprit.spring.wecare.Services.Forum;

import java.security.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Comment;
import tn.esprit.spring.wecare.Entities.Forum.Likes;
import tn.esprit.spring.wecare.Entities.Forum.Notification;
import tn.esprit.spring.wecare.Repositories.Forum.CommentRepository;
import tn.esprit.spring.wecare.Repositories.Forum.LikesRepository;
import tn.esprit.spring.wecare.Repositories.Forum.NotificationRepository;
@Service
public class NotificationServiceImp implements NotificationService {
	@Autowired
	LikesRepository likesRepository;
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	CommentRepository commentRepository;
	
	
	@Override
	@Transactional
	public List<String> showMyNotif(User user) {
		List<Notification> notifications = notificationRepository.getByUser(user);
		List<Likes> likes = likesRepository.findAll();
		List<Comment> comments = commentRepository.findAll();
		List<String> messages = new ArrayList<String>();
		for (Notification n : notifications) {
			//likes.add(likesRepository.getLikesByNotif(n));
			for(Likes likes2 : likes){
				Duration dur = Duration.between(likes2.getTimestamp(),LocalDateTime.now());
				long millis = dur.toMillis();
				long value = TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
				if(likes2.getNotif().equals(n))
				{
					if(value < 60)
						messages.add(likes2.getUser().getFirstname()+" liked your post "+
								String.format("%02d minutes ago",
										TimeUnit.MILLISECONDS.toMinutes(millis)
												- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))));
					else 
						messages.add(likes2.getUser().getFirstname()+" liked your post");
				}
					

			}
			for(Comment comment : comments){
				Duration dur = Duration.between(comment.getTimestamp(),LocalDateTime.now());
				long millis = dur.toMillis();
				long value = TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
				
				if(comment.getNotification().equals(n))
				{
					if(value < 60)
						messages.add(comment.getUser().getFirstname()+" commented your post "+
								String.format("%02d minutes ago",
										TimeUnit.MILLISECONDS.toMinutes(millis)
												- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))));
					else 
						messages.add(comment.getUser().getFirstname()+" commented your post");
				}
					

				
			}
		}/*
		for (Likes l : likes) {
			Duration dur = Duration.between(l.getTimestamp(),LocalDateTime.now());
			long millis = dur.toMillis();
			long value = TimeUnit.MILLISECONDS.toMinutes(millis)
					- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
			if(value < 60)
			messages.add(l.getUser().getFirstname() + " liked your post "
					+ String.format("%02d minutes ago",
							TimeUnit.MILLISECONDS.toMinutes(millis)
									- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))
							));
			else messages.add(l.getUser().getFirstname() + " liked your post ");
		}*/
	
		

		
		return messages;
	}

}
