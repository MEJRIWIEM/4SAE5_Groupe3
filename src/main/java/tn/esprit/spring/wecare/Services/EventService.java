package tn.esprit.spring.wecare.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tn.esprit.spring.wecare.Entities.Event;
import tn.esprit.spring.wecare.Repositories.EventRepository;

import java.util.Date;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // add event
    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    // get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // get event by id
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    // get events by name
    public List<Event> getEventsByName(String eventName) {
        return eventRepository.findByNameEquals(eventName);
    }

    // get Events sorted ascendant
    public List<Event> getAscendantSortedEvents(String field) {
        return eventRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    // get Events sorted descendant
    public List<Event> getDescendantSortedEvents(String field) {
        return eventRepository.findAll(Sort.by(Sort.Direction.DESC, field));
    }

    // filter by date equal
    public List<Event> getEventsByDatesEqual(Date date) {
        return eventRepository.findByDateEquals(date);
    }

    // filter by date greater
    public List<Event> getEventsByDatesGreater(Date date) {
        return eventRepository.findByDateIsGreaterThanEqual(date);
    }

    // filter by date less
    public List<Event> getEventsByDatesLess(Date date) {
        return eventRepository.findByDateIsLessThanEqual(date);
    }

    // delete event
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }




}
