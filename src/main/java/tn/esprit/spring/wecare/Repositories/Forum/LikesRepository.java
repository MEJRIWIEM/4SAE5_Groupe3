package tn.esprit.spring.wecare.Repositories.Forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.Forum.Likes;
import tn.esprit.spring.wecare.Entities.Forum.Notification;
import tn.esprit.spring.wecare.Entities.Forum.Post;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long>{
	 @Query("SELECT COUNT(*) FROM Likes l WHERE l.post=:post")
	    long likesNumber(@Param("post") Post post);
	 
	 

}
