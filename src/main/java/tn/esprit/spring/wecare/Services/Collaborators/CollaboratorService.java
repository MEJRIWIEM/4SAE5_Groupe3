package tn.esprit.spring.wecare.Services.Collaborators;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Collaborator;
import tn.esprit.spring.wecare.Entities.Forum.Post;

public interface CollaboratorService {
	

	
//	public String sendMail(EmailRequestDto request, Map<String, String> model);
	
	public void MailWithAttachment();

	public ResponseEntity addCollaborator( Collaborator collaborator ,User user);
	
	public List<Collaborator> RetrieveCollaborators();
	//see the list of user's Collaborators
	//public List<Collaborator> RetrieveMyPosts( User user);
	
	//edit his post
    public ResponseEntity EditCollaborators( Long id, User user, Collaborator collaborator);
	public ResponseEntity DeleteCollaborators( Long id, User user);
	
	public Collaborator RetrieveCollaborator( Long id);
	
}
