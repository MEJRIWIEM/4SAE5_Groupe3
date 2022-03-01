package tn.esprit.spring.wecare.Entities.Forum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import tn.esprit.spring.wecare.Configuration.Files.FileDB;
import tn.esprit.spring.wecare.Entities.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPost;
	@NonNull
	private String title;
	@NonNull
	private String text;
	private LocalDateTime timestamp;
	private String fileURL;
	// 0 or many posts can belong to a user
	 
	 //ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
		//.path(p.getFileDB().getId()).toUriString()+ System.lineSeparator()
	@JsonIgnore
	@ManyToOne
	private User user;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	private Set<Comment> comments;
	@JsonIgnore
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	private Set<Likes> likes;
	
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="file_id")
	@JsonIgnore
	private FileDB fileDB;
	




	
	

}
