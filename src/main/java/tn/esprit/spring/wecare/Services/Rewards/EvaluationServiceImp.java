package tn.esprit.spring.wecare.Services.Rewards;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Rewards.Badge;
import tn.esprit.spring.wecare.Entities.Rewards.Evaluation;
import tn.esprit.spring.wecare.Repositories.Rewards.BadgeRepository;
import tn.esprit.spring.wecare.Repositories.Rewards.EvaluationRepository;

@Service
public class EvaluationServiceImp implements EvaluationService {

	@Autowired
	EvaluationRepository evaluationRepository;
	@Override
	public void addEvaluation(Evaluation evaluation, User user) {
		// TODO Auto-generated method stub
	//	evaluation.setUsers(user);
		evaluationRepository.save(evaluation);
		
	}

	@Override
	public List<Evaluation> RetrieveEvaluations() {
		// TODO Auto-generated method stub
		return 	 evaluationRepository.findAll();
		
	}

	@Override
	public List<Evaluation> RetrieveMyEvaluations(User user) {
		// TODO Auto-generated method stub
		List<Evaluation> evaluations = evaluationRepository.findAll();
		 List<Evaluation> myEvaluations = new ArrayList<Evaluation>();
		 for(Evaluation  e:evaluations){
			 if(e.getUsers().equals(user))
			 {
				 myEvaluations.add(e);
			 }
		 }
		return  myEvaluations;
	}

	@Override
	public Evaluation RetrieveEvaluation(Long id) {
		// TODO Auto-generated method stub
		return  evaluationRepository.findById(id).orElse(null);
	}

	@Override
	public ResponseEntity DeleteEvaluation(Long id, User user) {
		// TODO Auto-generated method stub
		List<Evaluation> evaluations = evaluationRepository.findAll();

		 for(Evaluation e: evaluations){
			 if(e.getUsers().equals(user)&& e.getEvaluation_id().equals(id))
			 {
				 evaluationRepository.delete(e);
				return new ResponseEntity("Post deleted successfully!",HttpStatus.OK);
			 }
		 }
		 return new ResponseEntity("Post was not deleted!",HttpStatus.CONFLICT);
	}

	@Override
	public ResponseEntity EditEvaluation(Long id, User user, Evaluation evaluation) {
		// TODO Auto-generated method stub
		List<Evaluation> evaluations = evaluationRepository.findAll();

		 for(Evaluation e: evaluations){
			 if(e.getUsers().equals(user)&& e.getEvaluation_id().equals(id))
			 {
				e.setText(evaluation.getText());
				e.setObjet(evaluation.getObjet());
				return new ResponseEntity("Post edited successfully!",HttpStatus.OK);
			 }
		 }
		 return new ResponseEntity("Post was not edited!",HttpStatus.CONFLICT);
	}

}
