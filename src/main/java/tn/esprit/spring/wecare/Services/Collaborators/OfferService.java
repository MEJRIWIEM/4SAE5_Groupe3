package tn.esprit.spring.wecare.Services.Collaborators;

import java.util.List;

import org.springframework.http.ResponseEntity;
import tn.esprit.spring.wecare.Entities.Collaborators.Offer;

public interface OfferService {
	public  ResponseEntity addOffer( Offer offer, Long id);
	public ResponseEntity EditOffer(  Long id, Offer offer);
	public ResponseEntity DeleteOffer(  Long id);
	public List<Offer> RetrieveOffer();
}
