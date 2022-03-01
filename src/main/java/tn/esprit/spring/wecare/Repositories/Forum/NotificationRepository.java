package tn.esprit.spring.wecare.Repositories.Forum;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Notification;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
	public List<Notification> getByUser(User user);

}
