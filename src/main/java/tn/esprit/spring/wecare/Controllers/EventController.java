package tn.esprit.spring.wecare.Controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.wecare.Entities.Event;
import tn.esprit.spring.wecare.Repositories.EventRepository;
import tn.esprit.spring.wecare.Services.CommentService;
import tn.esprit.spring.wecare.Services.EventService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CommentService commentService;

    // create event
    @RequestMapping(value = "/addEvent", method = RequestMethod.POST)
    public Event saveEvent(@RequestBody Event event) {
        return eventService.addEvent(event);
    }

    //update event
    @RequestMapping(value = "/replaceEvent", method = RequestMethod.PUT)
    public Event replaceEvent(@RequestBody Event newEvent, @RequestParam(name = "eventId") final Long id) {

        return eventRepository.findById(id)
                .map(event -> {
                    event.setName(newEvent.getName());
                    event.setDescription(newEvent.getDescription());
                    event.setType(newEvent.getType());
                    event.setMail(newEvent.getMail());
                    event.setDate(newEvent.getDate());
                    return eventRepository.save(event);
                })
                .orElseGet(() -> {
                    return eventRepository.save(newEvent);
                });
    }

    //get all events
    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public List<Event> retrieveEvents() {
        return eventService.getAllEvents();
    }

    // get event by id
    @RequestMapping(value = "/eventById", method = RequestMethod.GET)
    public Event retrieveEventById(@RequestParam(name = "eventId") final Long eventId) {
        return eventService.getEventById(eventId);
    }

    // get events by name
    @RequestMapping(value = "/eventByName", method = RequestMethod.GET)
    public List<Event> retrieveEventByName(@RequestParam(name = "eventName") final String eventName) {
        return eventService.getEventsByName(eventName);
    }

    // get sorted ascendant events
    @RequestMapping(value = "/sortedEventsAsc", method = RequestMethod.GET)
    public List<Event> retrieveSortedEvent(@RequestParam(name = "field") final String field) {
        return eventService.getAscendantSortedEvents(field);
    }

    // get sorted descendants events
    @RequestMapping(value = "/sortedEventsDesc", method = RequestMethod.GET)
    public List<Event> retrieveDesSortedEvent(@RequestParam(name = "field") final String field) {
        return eventService.getDescendantSortedEvents(field);
    }


    // post rating
    @RequestMapping(value = "/rateEvent", method = RequestMethod.PUT)
    public ResponseEntity<String> postRating(@RequestParam(name = "rate") final float rate, @RequestParam(name = "eventId") final Long eventId) {
        return eventRepository.findById(eventId)
                .map(event -> {
                    event.setRating(rate);
                    eventRepository.save(event);
                    return new ResponseEntity<>("the event is rated successfyll", HttpStatus.OK);
                })
                .orElseGet(() -> {
                    return new ResponseEntity<>("an error occured!", HttpStatus.BAD_REQUEST);
                });
    }

    //get events by date equal
    @RequestMapping(value = "/eventsByDateEqual", method = RequestMethod.GET)
    public List<Event> retrieveEventsByDateEqual(@RequestParam(name = "date") final String date) throws ParseException {
        SimpleDateFormat targetFormatter = new SimpleDateFormat("yyyy-M-dd'T'hh:mm:ss", Locale.ENGLISH);
        targetFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return eventService.getEventsByDatesEqual(targetFormatter.parse(date));
    }

    //get events by date greater
    @RequestMapping(value = "/eventsByDateGreater", method = RequestMethod.GET)
    public List<Event> retrieveEventsByDateGreater(@RequestParam(name = "date") final String date) throws ParseException {
        SimpleDateFormat targetFormatter = new SimpleDateFormat("yyyy-M-dd'T'hh:mm:ss", Locale.ENGLISH);
        targetFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return eventService.getEventsByDatesGreater(targetFormatter.parse(date));
    }

    //get events by date less
    @RequestMapping(value = "/eventsByDateLess", method = RequestMethod.GET)
    public List<Event> retrieveEventsByDateLess(@RequestParam(name = "date") final String date) throws ParseException {
        SimpleDateFormat targetFormatter = new SimpleDateFormat("yyyy-M-dd'T'hh:mm:ss", Locale.ENGLISH);
        targetFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return eventService.getEventsByDatesLess(targetFormatter.parse(date));
    }

    // delete event by id
    @RequestMapping(value = "/deleteEvent", method = RequestMethod.DELETE)
    public void deleteEvent(@RequestParam(name = "eventId") final Long eventId) {
        commentService.deleteEventComments(eventId);
        eventService.deleteEvent(eventId);
    }

}
