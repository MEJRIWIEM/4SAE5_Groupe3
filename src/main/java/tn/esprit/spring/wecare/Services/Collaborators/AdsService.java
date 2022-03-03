package tn.esprit.spring.wecare.Services.Collaborators;

import java.util.List;

import org.springframework.http.ResponseEntity;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Advertising;
import tn.esprit.spring.wecare.Entities.Collaborators.Collaborator;

public interface AdsService {
	public  ResponseEntity addAdvirtising(User user, Advertising ads, Long id);
	public ResponseEntity DeleteAdvertising(User user,  Long id);
	public ResponseEntity EditAdvertising(User user,  Long id, Advertising ads);
	
	public List<Advertising> RetrieveAds();

}
