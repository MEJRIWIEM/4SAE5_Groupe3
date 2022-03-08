package tn.esprit.spring.wecare.Entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import tn.esprit.spring.wecare.Entities.Collaborators.Advertising;

import tn.esprit.spring.wecare.Configuration.Files.FileDB;

import tn.esprit.spring.wecare.Entities.Collaborators.Collaborator;
import tn.esprit.spring.wecare.Entities.Collaborators.Rating;
import tn.esprit.spring.wecare.Entities.Collaborators.TypeAds;
import tn.esprit.spring.wecare.Entities.Forum.Comment;
import tn.esprit.spring.wecare.Entities.Forum.Likes;
import tn.esprit.spring.wecare.Entities.Forum.Notification;
import tn.esprit.spring.wecare.Entities.Forum.Post;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
@Getter
@Setter
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	private String firstname;
	private String lastname;
	private String photo;
	@OneToOne(cascade= CascadeType.ALL)
	private FileDB fileDB;
	
	private Long numTel;
	
	@Enumerated(EnumType.STRING)
	private Departement departement;
	

	@NotBlank
	@Size(max = 120)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	//A user can post 0 or many posts
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
	private Set<Post> posts= new HashSet<>();
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Comment> comments;
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Notification> notifications;

	
	//relation with collaborator 
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
	private Set<Collaborator> collaborators= new HashSet<>();
	
	//relation with ads 
	
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Advertising> ads;
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Rating> ratings;
	public User() {
	}
	
	

	public User(String username, String email, String password,String firstname) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
	}

	

	public User(String username, String email,
			String firstname, String lastname, String photo,FileDB fileDB, Long numTel, Departement departement,
			String password) {
		this.username = username;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.photo = photo;
		this.fileDB=fileDB;
		this.numTel = numTel;
		this.departement = departement;
		this.password = password;
	}
	

	public User(String username,String email,String firstname, String lastname,String password, Set<Role> roles) {
		super();
		this.username = username;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.roles = roles;
	}
	
	
	

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Long getNumTel() {
		return numTel;
	}

	public void setNumTel(Long numTel) {
		this.numTel = numTel;
	}




}
