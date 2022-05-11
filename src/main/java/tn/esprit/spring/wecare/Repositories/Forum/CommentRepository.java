package tn.esprit.spring.wecare.Repositories.Forum;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.Forum.Comment;
import tn.esprit.spring.wecare.Entities.Forum.Post;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long>{
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post=:post")
    long CommentsNumber(@Param("post") Post post);
    


	}
