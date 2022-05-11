package tn.esprit.spring.wecare.Payloads.Requests;

import javax.validation.constraints.NotBlank;

public class LinkedinLoginRequest {
	
	@NotBlank
	  private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}
