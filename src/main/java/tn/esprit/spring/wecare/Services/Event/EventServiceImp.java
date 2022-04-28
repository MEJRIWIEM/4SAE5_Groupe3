package tn.esprit.spring.wecare.Services.Event;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tn.esprit.spring.wecare.Configuration.Files.FileDB;
import tn.esprit.spring.wecare.Configuration.Files.FileDBRepository;
import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Event.Event;
import tn.esprit.spring.wecare.Entities.Event.eventType;
import tn.esprit.spring.wecare.Entities.Forum.Post;
import tn.esprit.spring.wecare.Repositories.Event.EventRepository;

@Service
public class EventServiceImp implements EventService{
	@Autowired
	FileDBRepository fileDBRepository;
	@Autowired
	EventRepository eventRepository;
	
	@Override
	public ResponseEntity addEvent(MultipartFile file, Event event, User user) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
		if (file != null) {
			fileDBRepository.save(FileDB);
			event.setFileDB(FileDB);

		}
		event.setEventCreator(user);
		event.setFileURL(ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(FileDB.getId())
				.toUriString());
		eventRepository.save(event);
		fileDBRepository.save(FileDB);
		return new ResponseEntity("Event created successfully!", HttpStatus.CREATED);
	}

	@Override
	public List<Event> RetrieveEvents() {
		// TODO Auto-generated method stub
		return eventRepository.findAll();
	}

	@Override
	public Event RetrieveEvent(Long id) {
		// TODO Auto-generated method stub
		return eventRepository.findById(id).orElse(null);
	}

	@Override
	public ResponseEntity EditEvent(MultipartFile file, Long id, User user, Event event) throws IOException {
		List<Event> events = eventRepository.findAll();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

		if (file != null) {
			fileDBRepository.save(FileDB);
			event.setFileDB(FileDB);

		}

		for (Event e : events) {
			if (e.getEventCreator().equals(user) && e.getIdEvent().equals(id)) {
				fileDBRepository.delete(e.getFileDB());
				e.setDescription(event.getDescription());
				e.setTitle(event.getTitle());
				e.setDateTime(event.getDateTime());
				e.setType(event.getType());
				e.setFileURL(ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(FileDB.getId())
						.toUriString());
				e.setFileDB(event.getFileDB());
				eventRepository.save(e);
				return new ResponseEntity("Event edited successfully!", HttpStatus.OK);
			}
		}
		return new ResponseEntity("Event was not edited!", HttpStatus.CONFLICT);
	}

	@Override
	public ResponseEntity DeleteEvent(Long id, User user) {
		List<Event> events = eventRepository.findAll();

		for (Event e  : events) {
			if (e.getEventCreator().equals(user) && e.getIdEvent().equals(id)) {
				eventRepository.delete(e);
				return new ResponseEntity("Event deleted successfully!", HttpStatus.OK);
			}
		}
		return new ResponseEntity("Event was not deleted!", HttpStatus.CONFLICT);
	}

}
