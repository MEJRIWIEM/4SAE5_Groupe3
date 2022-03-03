package tn.esprit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.repos.*;

import tn.esprit.entity.Event;

@Service
public class EventService implements  IEventService {
	
	
	@Autowired
	eventRepos eventRepos;

	@Override
	public Event saveCategory(Event p) {
		return eventRepos.save(p);

	}

	@Override
	public void deleteEvent(Event p) {
		eventRepos.delete(p);

		
	}

	@Override
	public void deleteEventById(Long id) {
		eventRepos.deleteById(id);
		
	}

	@Override
	public Event getEvent(Long id) {
		return eventRepos.findById(id).get();

	}

	@Override
	public List<Event> getAllEvents() {
		List<Event> Categorys =(List<Event>) eventRepos.findAll();
		for(Event Category:Categorys) {
			System.out.println(Category);
		}
		return Categorys;
	}

	@Override
	public Event updateEvent(Event adv, Long idAd) {
		Event a = adv;
		return eventRepos.save(a);
	}
	
	
	







	

	
}
