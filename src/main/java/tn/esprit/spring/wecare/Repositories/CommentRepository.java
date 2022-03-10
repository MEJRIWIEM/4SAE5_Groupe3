package tn.esprit.spring.wecare.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.wecare.Entities.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByEvent_EventId(Long eventId);
}
