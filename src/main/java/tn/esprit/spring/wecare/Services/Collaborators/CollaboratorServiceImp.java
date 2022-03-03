package tn.esprit.spring.wecare.Services.Collaborators;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Collaborator;
import tn.esprit.spring.wecare.Repositories.Collaborators.CollaboratorRepository;


@Service
public class CollaboratorServiceImp implements CollaboratorService{
	@Autowired 
	CollaboratorRepository collaboratorRepo;

	@Override
	public ResponseEntity addCollaborator(Collaborator collaborator, User user) {
		// TODO Auto-generated method stub
		collaborator.setUser(user);
		collaboratorRepo.save(collaborator);
		
		return new ResponseEntity("Collaborator created successfuly!",HttpStatus.CREATED);
	}

	
	@Override
	public List<Collaborator> RetrieveCollaborators() {
		return 	 collaboratorRepo.findAll();
	
	}
	
	@Override
	public ResponseEntity EditCollaborators(Long id, User user, Collaborator collaborator) {
		List<Collaborator> collaborators = collaboratorRepo.findAll();

		 for(Collaborator c: collaborators){
			 if(c.getUser().equals(user)&& c.getIdCollaborator().equals(id))
			 {
				c.setName(collaborator.getName());
				c.setAddress(collaborator.getAddress());
				c.setEmail(collaborator.getEmail());
				c.setTypeCollaborator(collaborator.getTypeCollaborator());
				c.setLogo(collaborator.getLogo());
				collaboratorRepo.save(c);
				return new ResponseEntity("Collaborator edited successfully!",HttpStatus.OK);
			 }
		 }
		 return new ResponseEntity("Collaborator was not edited!",HttpStatus.CONFLICT);

	}


	@Override
	public ResponseEntity DeleteCollaborators(Long id, User user) {
		List<Collaborator> collaborators = collaboratorRepo.findAll();

		 for(Collaborator c: collaborators){
			 if(c.getUser().equals(user)&& c.getIdCollaborator().equals(id))
			 {
				 collaboratorRepo.delete(c);
				return new ResponseEntity("Collaborator deleted successfully!",HttpStatus.OK);
			 }
		 }
		 return new ResponseEntity("Collaborator was not deleted!",HttpStatus.CONFLICT);
	}





	
}
