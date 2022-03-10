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
	
	@PostMapping("/addEvaluation/{id_user_evaluated}")
	public void addBadge(@RequestBody Evaluation evaluation ,@PathVariable("id_user_evaluated") Long id_user_evaluated) {
		User us = getTheCurrentUser();
		EvaluationService.addEvaluation(evaluation,us,id_user_evaluated);
		
	}
	@GetMapping("/unban/{id_user_evaluated}/{banned_user_id}")
	public void Unban(@RequestBody Evaluation evaluation ,@PathVariable("id_user_evaluated") Long id_user_evaluated,@PathVariable("banned_user_id") Long banned_user_id) {
		User us = getTheCurrentUser();
		EvaluationService.unban(id_user_evaluated, banned_user_id);
		
	}
	// see the list of evaluations i did 
		@GetMapping("/ListOfEvaluations")
		public List<Evaluation> RetrieveEvaluations() {
			User us = getTheCurrentUser();
			return EvaluationService.RetrieveEvaluations(us);
		}

		// see my evaluations done by other users to me
		@GetMapping("/ListOfMyEvaluations")
		public List<Evaluation> RetrieveMyEvaluations() {
			User us = getTheCurrentUser();
			return EvaluationService.RetrieveMyEvaluations(us);
		}

		// see a specific badge with his id
				@GetMapping("/RetriveEvaluation/{id}")
				public Evaluation RetrieveEvaluation(@PathVariable("id") Long id) {
					return EvaluationService.RetrieveEvaluation(id);
				}

		// delete his evaluation
		@DeleteMapping("/DeleteEvaluation/{id}")
		public ResponseEntity<Object> DeleteEvaluation(@PathVariable("id") Long id) {
			User us = getTheCurrentUser();
			return EvaluationService.DeleteEvaluation(id, us);
		}

		// edit his evaluation
		@PutMapping("/EditEvaluation/{id}")
		public ResponseEntity<Object> EditEvaluation(@PathVariable("id") Long id, @RequestBody Evaluation evaluation) {
			User us = getTheCurrentUser();
			return EvaluationService.EditEvaluation(id, us, evaluation);
		}
		
		//get the current user
				public User getTheCurrentUser() {
					String username;
					Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					if (principal instanceof UserDetails) {
						username = ((UserDetails) principal).getUsername();
					} else {
						username = principal.toString();
					}
					User us = userRepository.findByUsername(username).orElse(null);
					return us;
				}
	
}
