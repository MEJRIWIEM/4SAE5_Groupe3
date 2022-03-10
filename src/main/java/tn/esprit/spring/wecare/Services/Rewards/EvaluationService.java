package tn.esprit.spring.wecare.Services.Rewards;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Rewards.Evaluation;

public interface EvaluationService {
	public ResponseEntity addEvaluation(Evaluation evalutation, User user ,Long id_user_evaluated);
	//see the list of Evaluation
		public List<Evaluation> RetrieveEvaluations(User user);
		//see the list of user's Evaluations
		public List<Evaluation> RetrieveMyEvaluations( User user);
		//see a specific Evaluation with his id
		public Evaluation RetrieveEvaluation( Long id);
		//delete his Evaluation
		public ResponseEntity DeleteEvaluation( Long id, User user);
		//edit his Evaluation
		public ResponseEntity EditEvaluation( Long id, User user, Evaluation evaluation);
		public void unban(Long id_user_evaluated,Long banned_user_id);
		
}
