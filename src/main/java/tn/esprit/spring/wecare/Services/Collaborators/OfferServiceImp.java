package tn.esprit.spring.wecare.Services.Collaborators;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Sort;
import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Advertising;
import tn.esprit.spring.wecare.Entities.Collaborators.Collaborator;
import tn.esprit.spring.wecare.Entities.Collaborators.Offer;
import tn.esprit.spring.wecare.Entities.Collaborators.Rating;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Repositories.Collaborators.CollaboratorRepository;
import tn.esprit.spring.wecare.Repositories.Collaborators.OfferRepository;
import tn.esprit.spring.wecare.Repositories.Collaborators.RatingRepository;

import tn.esprit.spring.wecare.helper.offerExcelExporter;


@Service
public class OfferServiceImp implements OfferService{
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	CollaboratorRepository collaboratorRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RatingRepository ratingRepository;
	
	
	private offerExcelExporter offerExcel;
	
	private RatingService ratingService;
	
	  static final Logger LOGGER = 
			    Logger.getLogger(OfferServiceImp.class.getName());
	  
	  
	
	Double totale ;
	
	

	@Override
	@Transactional
	public ResponseEntity addOffer( Offer offer, Long id) {
		List<Collaborator> collaborators =  collaboratorRepository.findAll();

		 for(Collaborator c: collaborators){
			 if(c.getIdCollaborator().equals(id))
			 {
				 offer.setCollaborator(c);
				 
				// offer.setRatingAvg(totale);
				  offerRepository.save(offer);
				  //RatingServiceImp.AvgRatingByOffer( Long id);
				 // totale= this.ratingService.AvgRatingByOffer(offer);
				  //offer.setRatingAvg(totale);
				//  offerRepository.save(offer);
			 return new ResponseEntity<String>("offer created successfully!",HttpStatus.OK);
			 }}
		 
		 return new ResponseEntity<String>("offer was not created!",HttpStatus.BAD_REQUEST);
	}
	
	@Autowired
	  public void setOtherService(RatingService ratingService) {
	    this.ratingService = ratingService;
	  }

	@Override
	public ResponseEntity EditOffer( Long id, Offer offer) {
		List<Offer> offers = offerRepository.findAll();
		for(Offer o : offers)
		{
			 if(o.getIdOffer().equals(id)){
				 o.setName(offer.getName());
				 o.setTypeOffer(offer.getTypeOffer());
				 //o.setDateCreated(LocalDateTime.now());
				 //o.setDateEnd(LocalDateTime.now());
				 o.setPercent(offer.getPercent());
				 offerRepository.save(o);
				 return new ResponseEntity<String>("offer edited successfully!",HttpStatus.OK);

			 }

		}
		return new ResponseEntity<String>("Failed to edit offer!",HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity DeleteOffer( Long id) {
		List<Offer> offers = offerRepository.findAll();
		for(Offer o : offers)
		{
			 if(o.getIdOffer().equals(id)){
				 offerRepository.deleteById(id);
				 return new ResponseEntity<String>("Offer deleted successfully!",HttpStatus.OK);

			 }

		}
		return new ResponseEntity<String>("Failed to delete Offer!",HttpStatus.BAD_REQUEST);
	}

	@Override
	public List<Offer> RetrieveOffer() {
		return 	 offerRepository.findAll();
	}

	@Override
	public List<Offer> getOffersWithCollabortorId(Long id) {
		List<Offer> offers = offerRepository.findAll();
		List<Collaborator> collaborators =collaboratorRepository.findAll();
		Collaborator collaborator = collaboratorRepository.getById(id);
		List<Offer> myOffers = new ArrayList<Offer>();
        
			for ( Offer o : offers) {
				if ((o.getCollaborator().equals(collaborator))) {
					
					myOffers.add(o);	
				}
			}
        return myOffers;

	}

	@Override
	public List<Offer> listAll() {
		
		
	        return offerRepository.findAll(Sort.by("ratingAvg").descending());
	    }
	
	
	
	
	//@Scheduled(cron = "*/10 * * * * *")
	  public void downloadOfferExcel() throws InterruptedException {
	   
		//load();
		List<Offer> offers = offerRepository.findAll(Sort.by("ratingAvg").descending());

	   // ByteArrayInputStream in = ExcelHelper.offerToExcel(offers);
	    LOGGER.info("download offer xsl "+ 
	      LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));  
	  }
	
	

	
	
	

}
