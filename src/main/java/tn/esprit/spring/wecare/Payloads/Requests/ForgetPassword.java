package tn.esprit.spring.wecare.Payloads.Requests;

import javax.validation.constraints.NotBlank;

public class ForgetPassword {
	@NotBlank 
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
