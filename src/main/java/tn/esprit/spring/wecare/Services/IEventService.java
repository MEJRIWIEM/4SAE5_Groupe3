package tn.esprit.service;

import java.util.List;

import tn.esprit.entity.Event;

public interface IEventService {
	
	Event saveCategory(Event p);
//	Category updateCategory(Category p);
	void deleteEvent(Event p);
	void deleteEventById(Long id);
	Event getEvent(Long id);
	List<Event> getAllEvents();
	Event updateEvent(Event adv, Long idAd) ;

}
