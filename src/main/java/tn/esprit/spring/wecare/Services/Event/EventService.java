package tn.esprit.spring.wecare.Services.Event;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Event.Event;
import tn.esprit.spring.wecare.Entities.Forum.Post;

public interface EventService {
	public ResponseEntity addEvent(MultipartFile file,Event event, User user) throws IOException;
	public List<Event> RetrieveEvents();
	public Event RetrieveEvent( Long id);
	public ResponseEntity EditEvent(MultipartFile file,Long id, User user, Event event) throws IOException;
	public ResponseEntity DeleteEvent( Long id, User user);



}
