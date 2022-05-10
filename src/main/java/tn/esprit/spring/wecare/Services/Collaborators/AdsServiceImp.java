package tn.esprit.spring.wecare.Services.Collaborators;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Advertising;
import tn.esprit.spring.wecare.Entities.Collaborators.Collaborator;
import tn.esprit.spring.wecare.Entities.Collaborators.Offer;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Repositories.Collaborators.AdsRepository;
import tn.esprit.spring.wecare.Repositories.Collaborators.CollaboratorRepository;

@Service
public class AdsServiceImp implements AdsService{
	
	@Autowired
	AdsRepository adsRepository;
	@Autowired
	CollaboratorRepository collaboratorRepository;
	@Autowired
	UserRepository userRepository;
	@Override
	@Transactional
	public ResponseEntity addAdvirtising(User user, Advertising ads, Long id) {
		List<Collaborator> collaborators = collaboratorRepository.findAll();

		 for(Collaborator c: collaborators){
			 if(c.getIdCollaborator().equals(id))
			 {
				 
				 ads.setCollaborator(c);
				 ads.setUser(user);
				ads.setDateCreated(LocalDateTime.now());
				//ads.setDateEnd(LocalDateTime.now());
			      adsRepository.save(ads);
			
			 
			 
			 user.getAds().add(ads);
			 userRepository.save(user);
			 return new ResponseEntity<String>("advertising created successfully!",HttpStatus.OK);
			 }}
		 
		 return new ResponseEntity<String>("advertising was not created!",HttpStatus.BAD_REQUEST);
	}


	@Override
	public ResponseEntity EditAdvertising(User user, Long id, Advertising ads) {
		List<Advertising> advertisings = adsRepository.findAll();
		for(Advertising a : advertisings)
		{
			 if(a.getIdAd().equals(id) && a.getUser().equals(user)){
				 a.setCost(ads.getCost());
				 a.setTypeAd(ads.getTypeAd());
				 a.setDateCreated(LocalDateTime.now());
				 a.setDateEnd(ads.getDateEnd());
				 a.setName(ads.getName());
				 a.setTargetNbrViews(ads.getTargetNbrViews());
				 a.setFinalNbrViews(ads.getFinalNbrViews());
				 
				 
				 adsRepository.save(a);
				 return new ResponseEntity<String>("Advertising edited successfully!",HttpStatus.OK);

			 }

		}
		return new ResponseEntity<String>("Failed to edit Advertising!",HttpStatus.BAD_REQUEST);
	}
	
	@Override
	@Transactional
	public ResponseEntity DeleteAdvertising(User user, Long id) {
		List<Advertising> comments = adsRepository.findAll();
		for(Advertising c : comments)
		{
			 if(c.getIdAd().equals(id) && c.getUser().equals(user)){
				 adsRepository.deleteById(id);
				 return new ResponseEntity<String>("Ads deleted successfully!",HttpStatus.OK);

			 }

		}
		return new ResponseEntity<String>("Failed to delete Ads!",HttpStatus.BAD_REQUEST);
	}


	@Override
	public List<Advertising> RetrieveAds() {
		return 	 adsRepository.findAll();
	}


	@Override
	public List<Advertising> getAdsWithCollabortorId(Long id) {
		List<Advertising> ads = adsRepository.findAll();
		List<Collaborator> collaborators =collaboratorRepository.findAll();
		Collaborator collaborator = collaboratorRepository.getById(id);
		List<Advertising> myAds = new ArrayList<Advertising>();
        
			for ( Advertising a : ads) {
				if ((a.getCollaborator().equals(collaborator))) {
					
					myAds.add(a);	
				}
			}
        return myAds;
	}


	@Override
	public Advertising RetrieveAdvertisingById(Long id) {
		return adsRepository.findById(id).orElse(null);
	}
	}
	
	
	
	


