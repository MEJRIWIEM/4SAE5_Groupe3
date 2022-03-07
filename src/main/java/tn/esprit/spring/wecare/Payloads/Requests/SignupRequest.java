package tn.esprit.spring.wecare.Payloads.Requests;
import java.util.Set;

import javax.validation.constraints.*;

import tn.esprit.spring.wecare.Configuration.Files.FileDB;
import tn.esprit.spring.wecare.Entities.Departement;

public class SignupRequest {
	@NotBlank
	  @Size(min = 3, max = 20)
	  private String username;

	  @NotBlank
	  @Size(max = 50)
	  @Email
	  private String email;
	  
	 private String firstname;
	 private String lastname;
	 private String photo;
	 private FileDB fileDB;
	 private Long numTel;
	 private Departement departement;
		
	  

	  private Set<String> role;

	  @NotBlank
	  @Size(min = 6, max = 40)
	  private String password;

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

	  public Set<String> getRole() {
	    return this.role;
	  }

	  public void setRole(Set<String> role) {
	    this.role = role;
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

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public FileDB getFileDB() {
		return fileDB;
	}

	public void setFileDB(FileDB fileDB) {
		this.fileDB = fileDB;
	}

}
