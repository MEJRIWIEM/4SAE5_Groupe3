package tn.esprit.spring.wecare.Controllers.Collaborators;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Advertising;
import tn.esprit.spring.wecare.Entities.Collaborators.Collaborator;
import tn.esprit.spring.wecare.Entities.Collaborators.Offer;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Repositories.Collaborators.OfferRepository;
import tn.esprit.spring.wecare.Services.Collaborators.CollaboratorService;
import tn.esprit.spring.wecare.Services.Collaborators.OfferService;
import tn.esprit.spring.wecare.helper.offerExcelExporter;

@RestController
@RequestMapping("/api/offer")
@CrossOrigin(origins = "http://localhost:8081")
public class OfferController {
	
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	OfferService offerService;
	
	@Autowired

	CollaboratorService collaboratorService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addOffer/{id}")
	public ResponseEntity<?>  addOffer(@RequestBody Offer offer, @PathVariable("id") Long id){
		
		return offerService.addOffer( offer, id);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateOffer/{id}")
	public ResponseEntity EditOffer(@PathVariable("id") Long id, @RequestBody Offer offer) {
		
		return offerService.EditOffer( id, offer);

	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/DeleteOffer/{id}")
	public ResponseEntity DeleteOffer(@PathVariable("id") Long id) {
		
		return offerService.DeleteOffer( id);
	}
	
	
 	
	@GetMapping("/ListOfOffer")
	public List<Offer> RetrieveOffer() {
		return 	offerService.RetrieveOffer();
	}

 	
 	@GetMapping("/getOffersWithCollabortorId/{id}")
	public List<Offer> getOffersWithCollabortorId(@PathVariable("id") Long id) {
		return offerService.getOffersWithCollabortorId(id);
	}
 	
 	
	@GetMapping("/RetriveOfferById/{id}")
	public Offer RetrieveOfferById(@PathVariable("id") Long id) {
		return offerService.RetrieveOfferById(id);
	}
	

	
	
	@GetMapping("/GetAllOffer")
	  public ResponseEntity<Map<String, Object>> getAllOffers(

	        @RequestParam(required = false) String name,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size
	      ) {
	    try {
	      List<Offer> tutorials = new ArrayList<Offer>();
	      Pageable paging = PageRequest.of(page, size);
	      
	      Page<Offer> pageTuts;
	      if (name == null)
	        pageTuts = offerRepository.findAll(paging);
	      else
	        pageTuts = offerRepository.findByNameContaining(name, paging);
	      tutorials = pageTuts.getContent();
	      Map<String, Object> response = new HashMap<>();
	      response.put("tutorials", tutorials);
	      response.put("currentPage", pageTuts.getNumber());
	      response.put("totalItems", pageTuts.getTotalElements());
	      response.put("totalPages", pageTuts.getTotalPages());
	      return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	
	
 	
 	
 	
 	@GetMapping("/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException, MessagingException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date()).toString();
		String filepath = "C:\\Users\\zidra\\4SAE5_Groupe3\\src\\main\\resources\\";
		
		String headervalue = "attachment; filename=ListOffersByRating.xlsx";
		String paths=filepath + headervalue;

		response.setHeader(headerKey, paths);
		List<Offer> listOffers = offerService.listAll();
		offerExcelExporter exp = new offerExcelExporter(listOffers);
		exp.export(response);
		offerService.sendExcel();
	}
 	
 	

}
