package tn.esprit.spring.wecare.Entities.Forum;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.wecare.Entities.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idNotification;

	
	@OneToOne(mappedBy="notification")
	private Comment comment;
	
	@OneToOne(mappedBy="notif")
	private Likes like;
	
	@ManyToOne
	private User user;
}
