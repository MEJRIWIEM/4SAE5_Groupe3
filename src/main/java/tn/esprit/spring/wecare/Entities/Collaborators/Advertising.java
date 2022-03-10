package tn.esprit.spring.wecare.Entities.Collaborators;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Advertising  implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAd;

	
	private String name;
	
	@Enumerated(EnumType.STRING)
	
	private TypeAds typeAd ;
	//@CreatedDate
	private LocalDateTime dateCreated;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp  dateEnd;
	@Column(columnDefinition = "integer default 0")
	private Integer targetNbrViews;
	private Integer finalNbrViews;
	private Float cost;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
		
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "collaborator_id")
    private Collaborator collaborator;
}
