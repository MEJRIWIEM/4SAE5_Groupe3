package tn.esprit.spring.wecare.Payloads.Requests;

import javax.validation.constraints.NotBlank;

public class PasswordReset {

	@NotBlank 
	private String token;
	@NotBlank
	private String newPassword;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
