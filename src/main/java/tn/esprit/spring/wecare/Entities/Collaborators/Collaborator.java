package tn.esprit.spring.wecare.Entities.Collaborators;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;

//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
//@Document(indexName = "collaboratorindex")
public class Collaborator  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCollaborator;
	
//	@Field(type = FieldType.Text, name = "name")
	@NonNull
	private String name;
	
	//@Field(type = FieldType.Text, name = "typeCollaborator")
	@Enumerated(EnumType.STRING)
	
	private TypeCollaborator typeCollaborator ;
	//@Field(type = FieldType.Text, name = "address")
	private String address;
	@Email
	private String email;
	private String logo;
	@JsonIgnore
	@ManyToOne
	private User user;
	@JsonIgnore
	@OneToMany(mappedBy = "collaborator", cascade = CascadeType.ALL)
	private Set<Offer> offers;
	@JsonIgnore
	//@Field(type = FieldType.Keyword, name = "Ads")
	@OneToMany(mappedBy = "collaborator", cascade = CascadeType.ALL)
	private Set<Advertising> Ads;
	

}
