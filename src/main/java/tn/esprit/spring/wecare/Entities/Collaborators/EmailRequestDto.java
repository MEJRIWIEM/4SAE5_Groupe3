package tn.esprit.spring.wecare.Entities.Collaborators;



import lombok.Getter;
import lombok.Setter;





@Getter
@Setter

public class EmailRequestDto {

	
	private String from;
	private String to;
	private String subject;
	private String name;
}
