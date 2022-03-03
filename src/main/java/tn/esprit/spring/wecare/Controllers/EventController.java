package tn.esprit.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sipios.springsearch.anotation.SearchSpec;

import tn.esprit.entity.*;
import tn.esprit.service.*;
import tn.esprit.repos.*;
@RestController

@RequestMapping("Category")
public  class   EventController {
	@Autowired 
	EventService EventService;
	@Autowired 
	eventRepos eventRepos;

	//http://localhost:8081/SpringMVC/servlet/Category/get-all-Categories
	@GetMapping("/get-all-Categories") 
	@ResponseBody 
	
	 public List<Event> getAllProduits() { 
		
		 List<Event> list = EventService.getAllEvents();
		 return list; 
	} 

	
	
	//http://localhost:8081/SpringMVC/servlet/Category/ajouterCategory
   	/*
   	 *
   	 *{
 
     "titre": "Motez",
    "description": "re",
    "prix": 12,
    "date": null
}
   	 */
	@PostMapping("/ajouterCategory")
	@ResponseBody
	public Event saveEvent(@RequestBody Event e)
	{
		EventService.saveCategory(e) ;
		return e;
	}
	
//http://localhost:8081/SpringMVC/servlet/Category/UpdateEvent/4
	/*
	 {
 "id":4,
     "titre": "Motez KHmiri",
    "description": "re",
    "prix": 12,
    "date": null
}
	 */

	@PutMapping("/UpdateEvent/{id}")  
	private Event updateAdvertisement(@RequestBody Event adv, @PathVariable("id") Long id)   
	{  
		EventService.updateEvent(adv, id) ;
		return adv;
	}
	
	
	
	//http://localhost:8081/SpringMVC/servlet/Category/deleteCat/3
	@DeleteMapping("/deleteEvent/{id}") 
	@ResponseBody 
	void deleteEventtById(@PathVariable("id") Long id){ 
		EventService.deleteEventById(id);
		} 
	
	
	 //http://localhost:8081/SpringMVC/servlet/Category/Event/2
	   @GetMapping(value = "/Event/{id}")
	    public Optional<Event> afficherUnEvent(@PathVariable Long id) {
	        return eventRepos.findById(id);
	    }
	   
	   
	 //http://localhost:8081/SpringMVC/servlet/Category/EventsRecherche?search=(titre:'nn')
		  // http://localhost:8081/SpringMVC/servlet/Category/EventsRecherche?search=(prix:12  ) 
		    //http://localhost:8081/SpringMVC/servlet/Product/cars?search=(NameProduct:'lait'   OR priceProduct<780)
	   @GetMapping("/EventsRecherche")
	   	    public ResponseEntity<List<Event>> searchForCars(@SearchSpec Specification<Event> specs) {
	   	        return new ResponseEntity<>(eventRepos.findAll(Specification.where(specs)), HttpStatus.OK);
	   	    }
	
}
