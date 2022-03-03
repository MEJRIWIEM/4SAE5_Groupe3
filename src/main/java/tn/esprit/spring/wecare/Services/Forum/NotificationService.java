package tn.esprit.spring.wecare.Services.Forum;

import java.util.List;

import tn.esprit.spring.wecare.Entities.User;

public interface NotificationService {
	public List<String> showMyNotif(User user);

}
