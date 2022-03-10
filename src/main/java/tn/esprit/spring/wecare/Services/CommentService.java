package tn.esprit.spring.wecare.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.wecare.Entities.Comment;
import tn.esprit.spring.wecare.Repositories.CommentRepository;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    //add comment
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // get event comments
    public List<Comment> getEventComments(Long eventId) {
        return commentRepository.findByEvent_EventId(eventId);
    }

    // delete comment
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    // delete event comments
    public void deleteEventComments(Long eventId){
        List<Comment> commentsToDelete = getEventComments(eventId);
        for(Comment comment : commentsToDelete){
            deleteComment(comment.getCommentId());
        }
    }

}
