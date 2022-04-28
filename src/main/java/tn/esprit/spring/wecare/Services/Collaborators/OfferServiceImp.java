package tn.esprit.spring.wecare.Services.Collaborators;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import java.time.LocalDateTime;

import java.util.List;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Service;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Advertising;
import tn.esprit.spring.wecare.Entities.Collaborators.Collaborator;
import tn.esprit.spring.wecare.Entities.Collaborators.Offer;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Repositories.Collaborators.CollaboratorRepository;
import tn.esprit.spring.wecare.Repositories.Collaborators.OfferRepository;

@Service
public class OfferServiceImp implements OfferService{
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	CollaboratorRepository collaboratorRepository;
	@Autowired
	UserRepository userRepository;
	@Override
	@Transactional
	public ResponseEntity addOffer( Offer offer, Long id) {
		List<Collaborator> collaborators = collaboratorRepository.findAll();

		 for(Collaborator c: collaborators){
			 if(c.getIdCollaborator().equals(id))
			 {
				 
				 offer.setCollaborator(c);
				 //ads.setUser(user);
				// offer.setDateCreated(LocalDateTime.now());
				// offer.setDateEnd(LocalDateTime.now());
			      offerRepository.save(offer);
			
			 
			 
			 //user.getAds().add(offer);
			 //userRepository.save(user);
			 return new ResponseEntity<String>("offer created successfully!",HttpStatus.OK);
			 }}
		 
		 return new ResponseEntity<String>("offer was not created!",HttpStatus.BAD_REQUEST);
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
				 //o.setTargetNbrViews(offer.getTargetNbrViews());
				 //o.setFinalNbrViews(offer.getFinalNbrViews());
				 
				 
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
	
	@Autowired JavaMailSender javaMailSender;
	public void sendMailWithAttachment(String toEmail,
            String body,
            String subject,
            String attachment) throws MessagingException {
		MimeMessage mimeMessage=javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
		mimeMessageHelper.setFrom("noreply.wecare.tn@gmail.com");
		mimeMessageHelper.setTo(toEmail);
		mimeMessageHelper.setText(body);
		mimeMessageHelper.setSubject(subject);

		FileSystemResource fileSystemResource=
		new FileSystemResource(new File(attachment));
		mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),
		fileSystemResource);
		javaMailSender.send(mimeMessage);
		System.out.printf("Mail with attachment sent successfully..");


}
	
	
	//@Scheduled(cron = "0 0 0 1 * *")
	//@Scheduled(cron = "*/10 * * * * *")
	  public void rememberMeToExportExcel() throws InterruptedException {
	   
	    LOGGER.info("download offer xsl "+ 
	      LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));  
	  }

	@Override
	//@Scheduled(cron = "*/10 * * * * *")
	public void sendExcel() throws MessagingException {
		
		sendMailWithAttachment("raoudha.zid@esprit.tn","the list of Offers by rating of this moth ","u can transfer to our collaborators","C:/Users/zidra/Downloads/ListOffersByRating.xlsx");
	}
	
	
	

	
	
	

}
