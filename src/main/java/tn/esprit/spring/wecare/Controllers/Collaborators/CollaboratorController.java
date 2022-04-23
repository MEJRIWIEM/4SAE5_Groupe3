package tn.esprit.spring.wecare.Controllers.Collaborators;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Collaborator;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Services.Collaborators.CollaboratorService;

@RestController
@RequestMapping("/api/collaborator")
public class CollaboratorController {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	CollaboratorService collaboratorService;
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addCollaborator")
	public  ResponseEntity<Object> addCollaborator(@RequestBody Collaborator collaborator) {

		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
		User us= userRepository.findByUsername(username).orElse(null);
		return collaboratorService.addCollaborator(collaborator, us);
		
	}
	
	//see the list of collaborators
		@GetMapping("/ListOfCollaborators")
		public List<Collaborator> RetrieveCollaborators() {
			return 	collaboratorService.RetrieveCollaborators();
		}
		
		//edit his collaborators
		@PutMapping("/EditCollaborator/{id}")
		public ResponseEntity<Object> EditCollaborators(@PathVariable("id")Long id, @RequestBody Collaborator collaborator) {
			String username;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
				} else {
				 username = principal.toString();
				}
			User us= userRepository.findByUsername(username).orElse(null);	
			return collaboratorService.EditCollaborators(id, us, collaborator);
		}
		
		//delete his collaborator
		@DeleteMapping("/DeleteCollaborator/{id}")
		public  ResponseEntity<Object>  DeleteCollaborators(@PathVariable("id")Long id) {
			String username;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
				} else {
				 username = principal.toString();
				}
			User us= userRepository.findByUsername(username).orElse(null);	
			 return collaboratorService.DeleteCollaborators(id, us);
		}

}
