package tn.esprit.spring.wecare.Entities.Forum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.UniqueElements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import tn.esprit.spring.wecare.Entities.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Likes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idLikes;

	private LocalDateTime timestamp;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_us_liked", referencedColumnName = "id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "the_post_id")
	private Post post;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "notif_id", referencedColumnName = "idNotification")
	private Notification notif;

}
