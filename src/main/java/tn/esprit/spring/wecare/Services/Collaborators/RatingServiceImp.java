package tn.esprit.spring.wecare.Services.Collaborators;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Offer;
import tn.esprit.spring.wecare.Entities.Collaborators.Rating;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Repositories.Collaborators.OfferRepository;
import tn.esprit.spring.wecare.Repositories.Collaborators.RatingRepository;

@Service
public class RatingServiceImp  implements RatingService{
	
	@Autowired 
	RatingRepository ratingRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	OfferRepository offerRepository;
	//private RatingService ratingService;
	
	Double totale=5.0 ;
	
	@Override
	@Transactional
	public ResponseEntity ratingOffer(User user,Rating rating, Long id  ) {
		
		
		List<Offer> offers = offerRepository.findAll();
		List<Rating> ratings = ratingRepository.findAll();
		Offer offe = offerRepository.getById(id);
         for (Offer o : offers) {
			for (Rating r : ratings) {
				if ((r.getOffer().equals(offe))) {
					if (r.getUser().equals(user))
					{
				          //ratingRepository.delete(r);
						//Rating rat = new Rating();
						r.setValue(rating.getValue());
						offe.setRatingAvg(this.ratingRepository.AvgRatingByOffer(offe ));
						offe.setCountUser(this.ratingRepository.nbrOfRatingUserByOffer(offe));
						offerRepository.save(offe);
					
						
						return new ResponseEntity<String>("Rated already  edited !  ", HttpStatus.OK);
					}
						
				}
			}
			if (o.getIdOffer().equals(id)) {
				rating.setOffer(o);
				rating.setUser(user);
				
                ratingRepository.save(rating);
				userRepository.save(user);
				offe.setRatingAvg(this.ratingRepository.AvgRatingByOffer(offe ));
				offe.setCountUser(this.ratingRepository.nbrOfRatingUserByOffer(offe));
				offerRepository.save(offe);
				return new ResponseEntity<String>("offer was rated successfully!", HttpStatus.OK);
			}

		}

		return new ResponseEntity<String>("Offer was not rated!", HttpStatus.BAD_REQUEST);
			
		}
	
	
	
	@Override
	public ResponseEntity EditRating( Long id, Rating rating) {
		List<Rating> ratings = ratingRepository.findAll();
		for(Rating r : ratings)
		{
			 if(r.getIdRating().equals(id)){
				 r.setValue(rating.getValue());
				 
				 
				 ratingRepository.save(r);
				 return new ResponseEntity<String>("Offer rating edited successfully!",HttpStatus.OK);

			 }

		}
		return new ResponseEntity<String>("Failed to edit Offer rating!",HttpStatus.BAD_REQUEST);
	
	}

	@Override
	public ResponseEntity DeleteRating( Long id) {
		List<Rating> ratings = ratingRepository.findAll();
		for(Rating r : ratings)
		{
			 if(r.getIdRating().equals(id)){
				
				 ratingRepository.deleteById(id);
				 return new ResponseEntity<String>("Rating deleted successfully!",HttpStatus.OK);

			 }

		}
		return new ResponseEntity<String>("Failed to delete Offer rating!",HttpStatus.BAD_REQUEST);
	}

    @Override
	public List<Rating> RetrieveRating() {
		return 	 ratingRepository.findAll();
	}


	@Override
	public List<Rating> getMyRating(User user) {
		List<Rating> ratings = ratingRepository.findAll();
		List<Rating> myRatings = new ArrayList<Rating>();
		for (Rating r : ratings) {
			if (r.getUser().equals(user)) {
				myRatings.add(r);
			}
		}
		return myRatings;

	}


	@Override
	public List<Rating> getRatingWithOfferId(Long id) {
		List<Offer> offers = offerRepository.findAll();
		List<Rating> ratings = ratingRepository.findAll();
		Offer offer = offerRepository.getById(id);
		List<Rating> myRatings = new ArrayList<Rating>();
        
			for (Rating r : ratings) {
				if ((r.getOffer().equals(offer))) {
					
					myRatings.add(r);	
				}
			}
		
        return myRatings;

	}


	@Override
	public Double  AvgRatingByOffer(Offer id  ) {
		//Double  df = new DecimalFormat("#.####");
		 //	 df=ratingRepository.AvgRatingByOffer(id);
		 	///return df.setRoundingMode(RoundingMode.CEILING);
		return ratingRepository.AvgRatingByOffer(id);
		
		
	}


	@Override
	public Integer nbrOfRatingUserByOffer(Offer id) {
		// TODO Auto-generated method stub
		return ratingRepository.nbrOfRatingUserByOffer(id);
	}

}
