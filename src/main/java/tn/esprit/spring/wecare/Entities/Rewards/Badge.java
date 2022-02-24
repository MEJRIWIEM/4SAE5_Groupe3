package tn.esprit.spring.wecare.Entities.Rewards;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.TypeCollaborator;
import tn.esprit.spring.wecare.Entities.Forum.Comment;
import tn.esprit.spring.wecare.Entities.Forum.Likes;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Badge implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String icon;
	private Long minPoints;
	private Long maxPoints;
	private Long n_votes;
	private CategorieB categorie ;
	// 0 or many badges can belong to a user
	@JsonIgnore
	@ManyToOne
	private User user;


}
