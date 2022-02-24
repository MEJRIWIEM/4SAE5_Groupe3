package tn.esprit.spring.wecare.Controllers.Rewards;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Rewards.Badge;
import tn.esprit.spring.wecare.Entities.Rewards.Evaluation;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Services.Rewards.EvaluationServiceImp;

@RestController
public class EvaluationController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	EvaluationServiceImp EvaluationService;
	
	@PostMapping("/addEvaluation")
	public void addBadge(@RequestBody Evaluation evaluation) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
		User us= userRepository.findByUsername(username).orElse(null);
		EvaluationService.addEvaluation(evaluation,us);
		
	}
	// see the list of evaluations
		@GetMapping("/ListOfEvaluations")
		public List<Evaluation> RetrieveEvaluations() {
			return EvaluationService.RetrieveEvaluations();
		}

		// see my evaluations
		@GetMapping("/ListOfMyEvaluations")
		public List<Evaluation> RetrieveMyEvaluations() {
			String username;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}
			User us = userRepository.findByUsername(username).orElse(null);
			return EvaluationService.RetrieveMyEvaluations(us);
		}

	/*	// see a specific evaluation with his id
		@GetMapping("/RetriveEvaluation/{id}")
		public Badge RetrieveEvaluation(@PathVariable("id") Long id) {
			return EvaluationService.RetrieveEvaluation(id);
		}*/

		// delete his evaluation
		@DeleteMapping("/DeleteEvaluation/{id}")
		public ResponseEntity<Object> DeleteEvaluation(@PathVariable("id") Long id) {
			String username;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}
			User us = userRepository.findByUsername(username).orElse(null);
			return EvaluationService.DeleteEvaluation(id, us);
		}

		// edit his evaluation
	/*	@PutMapping("/EditEvaluation/{id}")
		public ResponseEntity<Object> EditEvaluation(@PathVariable("id") Long id, @RequestBody Badge post) {
			String username;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}
			User us = userRepository.findByUsername(username).orElse(null);
			return EvaluationService.EditEvaluation(id, us, post);
		}*/
	
}
