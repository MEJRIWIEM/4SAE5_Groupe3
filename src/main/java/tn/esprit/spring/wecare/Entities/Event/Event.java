package tn.esprit.spring.wecare.Entities.Event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import tn.esprit.spring.wecare.Configuration.Files.FileDB;
import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Comment;
import tn.esprit.spring.wecare.Entities.Forum.Likes;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event  implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEvent;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dateTime;
	private String title;
	private String description;
	@Enumerated(EnumType.STRING)
	private eventType type;
	private String fileURL;

	@ManyToMany(cascade= CascadeType.ALL)
	@JoinTable(name="EVENT_PARTICIPANT"
    , joinColumns={
        @JoinColumn(name="EVENT_ID")
        }
    , inverseJoinColumns={
        @JoinColumn(name="PARTICIPANT_ID")
        }
    )
	@JsonIgnore
	private Set<User> participants;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JsonIgnore
	private User eventCreator;
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="file_id")
	@JsonIgnore
	private FileDB fileDB;


}
