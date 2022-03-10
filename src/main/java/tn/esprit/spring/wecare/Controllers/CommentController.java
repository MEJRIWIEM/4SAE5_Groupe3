package tn.esprit.spring.wecare.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.wecare.Entities.Comment;
import tn.esprit.spring.wecare.Services.CommentService;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    // add comment
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public Comment saveComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    // get event comments
    @RequestMapping(value = "/eventComments", method = RequestMethod.GET)
    public List<Comment> retrieveEventComments(@RequestParam(name = "eventId") final Long eventId) {
        return commentService.getEventComments(eventId);
    }

    // delete comment
    @RequestMapping(value = "/deleteComment", method = RequestMethod.DELETE)
    public void deleteComment(@RequestParam(name = "commentId") final Long commentId) {
        commentService.deleteComment(commentId);
    }

}
