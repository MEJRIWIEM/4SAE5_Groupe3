package tn.esprit.spring.wecare.Repositories.Forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.Forum.Notification;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

}
