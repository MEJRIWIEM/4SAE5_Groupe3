package tn.esprit.spring.wecare.Services.Rewards;

import java.util.List;

import org.springframework.http.ResponseEntity;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Rewards.Evaluation;

public interface EvaluationService {
	public void addEvaluation(Evaluation evalutation, User user);
	//see the list of Evaluation
		public List<Evaluation> RetrieveEvaluations();
		//see the list of user's Evaluations
		public List<Evaluation> RetrieveMyEvaluations( User user);
		//see a specific Evaluation with his id
		public Evaluation RetrieveEvaluation( Long id);
		//delete his Evaluation
		public ResponseEntity DeleteEvaluation( Long id, User user);
		//edit his Evaluation
		public ResponseEntity EditEvaluation( Long id, User user, Evaluation evaluation);
}
