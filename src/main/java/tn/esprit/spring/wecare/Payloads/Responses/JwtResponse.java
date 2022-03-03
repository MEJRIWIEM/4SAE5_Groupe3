package tn.esprit.spring.wecare.Payloads.Responses;


import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import tn.esprit.spring.wecare.Entities.Departement;
public class JwtResponse {
	private String token;
	  private String type = "Bearer";
		private String refreshToken;

	  private Long id;
	  private String username;
	  private String firstname;
	  private String lastname;
		private String photo;
		private Long numTel;
		
		@Enumerated(EnumType.STRING)
		private Departement departement;
	  private String email;
	  private List<String> roles;
	  
	  public JwtResponse(String accessToken, String refreshToken,Long id, String username, String firstname,String lastname, String photo, Long numTel, Departement departement,String email, List<String> roles) {
		    this.token = accessToken;
		    this.refreshToken=refreshToken;
		    this.id = id;
		    this.username = username;
		    this.email = email;
		    this.roles = roles;
		    this.firstname=firstname;
		    this.lastname=lastname;
		    this.photo=photo;
		    this.numTel=numTel;
		    this.departement=departement;
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



	public Departement getDepartement() {
		return departement;
	}



	public void setDepartement(Departement departement) {
		this.departement = departement;
	}



	public String getAccessToken() {
		    return token;
		  }
	  

		  public void setAccessToken(String accessToken) {
		    this.token = accessToken;
		  }

		  public String getTokenType() {
		    return type;
		  }

		  public void setTokenType(String tokenType) {
		    this.type = tokenType;
		  }

		  public Long getId() {
		    return id;
		  }

		  public void setId(Long id) {
		    this.id = id;
		  }

		  public String getEmail() {
		    return email;
		  }

		  public void setEmail(String email) {
		    this.email = email;
		  }

		  public String getUsername() {
		    return username;
		  }

		  public void setUsername(String username) {
		    this.username = username;
		  }

		  public List<String> getRoles() {
		    return roles;
		  }

		public String getFirstname() {
			return firstname;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}



		public String getRefreshToken() {
			return refreshToken;
		}



		public void setRefreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
		}

}
