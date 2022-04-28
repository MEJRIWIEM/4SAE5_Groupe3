package tn.esprit.spring.wecare.Controllers.Event;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Event.Event;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Services.Event.EventService;

@RestController
@RequestMapping("/api/eventCrud")
public class EventController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	EventService eventService;
	@PostMapping("/addEvent")
	public ResponseEntity addEvent(@RequestPart(value = "file", required = false)  MultipartFile file, 
			@RequestPart("event") Event event) throws IOException {
		User us = getTheCurrentUser();
		return eventService.addEvent(file, event, us);

	}
	
	@GetMapping("/Events")
	public List<Event> RetrieveEvents(){
		return eventService.RetrieveEvents();
	}
	
	@GetMapping("/Events/{id}")
	public Event RetrieveEvent(@PathVariable("id")Long id){
		return eventService.RetrieveEvent(id);
		
	}
	@PutMapping("/editEvent/{id}")
	public ResponseEntity EditEvent(@RequestPart(value = "file", required = false)MultipartFile file,
			@PathVariable("id") Long id, @RequestPart("event") Event event) throws IOException{
		User us = getTheCurrentUser();

				return eventService.EditEvent(file, id, us, event);
		
	}
	public User getTheCurrentUser() {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User us = userRepository.findByUsername(username).orElse(null);
		return us;
	}

}
