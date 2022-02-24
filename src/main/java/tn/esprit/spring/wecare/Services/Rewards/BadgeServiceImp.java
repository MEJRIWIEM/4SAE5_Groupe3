package tn.esprit.spring.wecare.Services.Rewards;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Rewards.Badge;
import tn.esprit.spring.wecare.Entities.Rewards.CategorieB;
import tn.esprit.spring.wecare.Repositories.Rewards.BadgeRepository;


@Service
public class BadgeServiceImp implements BadgeService {

	@Autowired
	BadgeRepository badgeRepository;
	@Override
	public void addBadge(Badge badge, User user) {
		// TODO Auto-generated method stub
		badge.setUser(user);
		badgeRepository.save(badge);
		
	}
	@Override
	public List<Badge> RetrieveBadges() {
		// TODO Auto-generated method stub
		return 	 badgeRepository.findAll();
	}
	@Override
	public List<Badge> RetrieveMyBadges(User user) {
		// TODO Auto-generated method stub
		List<Badge> badges = badgeRepository.findAll();
		 List<Badge> myBadges = new ArrayList<Badge>();
		 for(Badge b: badges){
			 if(b.getUser().equals(user))
			 {
				 myBadges.add(b);
			 }
		 }
		return  myBadges;
	}
	@Override
	public Badge RetrieveBadge(Long id) {
		// TODO Auto-generated method stub
		return  badgeRepository.findById(id).orElse(null);
		
	}
	@Override
	public ResponseEntity DeleteBadge(Long id, User user) {
		// TODO Auto-generated method stub
		List<Badge> badges = badgeRepository.findAll();

		 for(Badge b: badges){
			 if(b.getUser().equals(user)&& b.getId().equals(id))
			 {
				badgeRepository.delete(b);
				return new ResponseEntity("badge deleted successfully!",HttpStatus.OK);
			 }
		 }
		 return new ResponseEntity("badge was not deleted!",HttpStatus.CONFLICT);
	}
	@Override
	public ResponseEntity EditBadge(Long id, User user, Badge badge) {
		// TODO Auto-generated method stub
		List<Badge> badges = badgeRepository.findAll();

		 for(Badge b: badges){
			 if(b.getUser().equals(user)&& b.getId().equals(id))
			 {
				b.setIcon(badge.getIcon());
				b.setMinPoints(badge.getMinPoints());
				b.setMaxPoints(badge.getMaxPoints());
				b.setN_votes(badge.getN_votes());
				b.setCategorie(badge.getCategorie());
				badgeRepository.save(b);
				return new ResponseEntity("badge edited successfully!",HttpStatus.OK);
			 }
		 }
		 return new ResponseEntity("badge was not edited!",HttpStatus.CONFLICT);
	}

}
