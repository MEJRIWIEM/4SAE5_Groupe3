package tn.esprit.spring.wecare.Services.Collaborators;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Advertising;
import tn.esprit.spring.wecare.Entities.Collaborators.Collaborator;
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
		List<Collaborator> collaborators = (List<Collaborator>) collaboratorRepository.findAll();

		 for(Collaborator c: collaborators){
			 if(c.getIdCollaborator().equals(id))
			 {
				 
				 ads.setCollaborator(c);
				 ads.setUser(user);
				ads.setDateCreated(LocalDateTime.now());
				ads.setDateEnd(LocalDateTime.now());
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
				 a.setDateEnd(LocalDateTime.now());
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
	public ResponseEntity getAllAds(String name, int page, int size) {
		try {
		      List<Advertising> tutorials = new ArrayList<Advertising>();
		      Pageable paging = PageRequest.of(page, size);
		      
		      Page<Advertising> pageTuts;
		      if (name == null)
		        pageTuts = adsRepository.findAll(paging);
		      else
		        pageTuts = adsRepository.findByNameContaining(name, paging);
		      tutorials = pageTuts.getContent();
		      Map<String, Object> response = new HashMap<>();
		      
		      response.put("ads", tutorials);
		      response.put("currentPage", pageTuts.getNumber());
		      response.put("totalItems", pageTuts.getTotalElements());
		      response.put("totalPages", pageTuts.getTotalPages());
		      return new ResponseEntity<>(response, HttpStatus.OK);
		      
		    } catch (Exception e) {
		    	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}



	





}
	
	
	
	


