package tn.esprit.spring.wecare.Services.Rewards;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Rewards.Badge;
import java.util.List;
import org.springframework.http.ResponseEntity;


public interface BadgeService {
	public void addBadge(Badge badge, User user);
	//see the list of badge
		public List<Badge> RetrieveBadges();
		//see the list of user's badges
		public List<Badge> RetrieveMyBadges( User user);
		//see a specific badge with his id
		public Badge RetrieveBadge( Long id);
		//delete his badge
		public ResponseEntity DeleteBadge( Long id, User user);
		//edit his badge
		public ResponseEntity EditBadge( Long id, User user, Badge badge);

}
