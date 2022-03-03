package tn.esprit.spring.wecare.Services.Rewards;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Post;
import tn.esprit.spring.wecare.Entities.Rewards.Badge;

import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface BadgeService {
	
	public ResponseEntity addBadge(MultipartFile file, Badge badge,User user)throws IOException;
	//affecter un badge a un user 
	public ResponseEntity affecterBadgeUser(Long id , User user) ;
	//see the list of badge
		public List<Badge> RetrieveBadges();
		//see the list of user's badges
		public List<Badge> RetrieveMyBadges( User user);
		//see a specific badge with his id
		public Badge RetrieveBadge( Long id);
		public List<Badge> RetrieveBadgesWithFile();
		//delete his badge
		public ResponseEntity DeleteBadge( Long id, User user);
		//edit his badge
		public ResponseEntity EditBadge(MultipartFile file, Long id, User user, Badge badge)throws IOException;
	

}
