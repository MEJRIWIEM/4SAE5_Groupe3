package tn.esprit.entity;

import javax.persistence.GeneratedValue;

import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
public class Event {

	
	 @Id
	 @GeneratedValue ( strategy = GenerationType.IDENTITY)
	Long id ;
	String titre ;
	java.util.Date Date ;
	String description ;
	Float prix ;
	
	
	@Override
	public String toString() {
		return "Event [id=" + id + ", titre=" + titre + ", Date=" + Date + ", description=" + description + ", prix="
				+ prix + "]";
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}


	public java.util.Date getDate() {
		return Date;
	}


	public void setDate(java.util.Date date) {
		Date = date;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Float getPrix() {
		return prix;
	}


	public void setPrix(Float prix) {
		this.prix = prix;
	}


	public Event(Long id, String titre, java.util.Date date, String description, Float prix) {
		super();
		this.id = id;
		this.titre = titre;
		Date = date;
		this.description = description;
		this.prix = prix;
	}


	public Event() {
		// TODO Auto-generated constructor stub
	}

}
