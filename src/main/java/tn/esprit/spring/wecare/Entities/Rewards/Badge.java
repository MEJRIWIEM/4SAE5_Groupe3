package tn.esprit.spring.wecare.Entities.Rewards;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import tn.esprit.spring.wecare.Configuration.Files.FileDB;
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
	private String fileURL;
	// 0 or many badges can belong to a user
	@ManyToMany(mappedBy = "badges")
	@JsonIgnore
	private Set<User> users = new HashSet<>();
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="file_id")
	@JsonIgnore
	private FileDB fileDB;


}
