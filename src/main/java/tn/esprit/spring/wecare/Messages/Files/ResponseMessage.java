package tn.esprit.spring.wecare.Messages.Files;

public class ResponseMessage {
	  private String message;
	  public ResponseMessage(String message) {
	    this.message = message;
	  }
	  public String getMessage() {
	    return message;
	  }
	  public void setMessage(String message) {
	    this.message = message;
	  }
	}