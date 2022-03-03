package tn.esprit.spring.wecare.Services.Rewards;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Rewards.BadWordFilter;
import tn.esprit.spring.wecare.Entities.Rewards.Badge;
import tn.esprit.spring.wecare.Entities.Rewards.Evaluation;
import tn.esprit.spring.wecare.Repositories.Rewards.BadgeRepository;
import tn.esprit.spring.wecare.Repositories.Rewards.EvaluationRepository;

@Service
public class EvaluationServiceImp implements EvaluationService {

	@Autowired
	EvaluationRepository evaluationRepository;
	@Override
	public void addEvaluation(Evaluation evaluation, User user, Long id_user_evaluated) {
		// TODO Auto-generated method stub
		String output = BadWordFilter.getCensoredText(evaluation.getText());
		evaluation.setText(output);
	    evaluation.setUser(user);
	    evaluation.setId_user_evaluated(id_user_evaluated);
	 	evaluationRepository.save(evaluation);
		
	}
	@Override
	public List<Evaluation> RetrieveEvaluations(User user) {
		// TODO Auto-generated method stub
		List<Evaluation> evaluations = evaluationRepository.findAll();
		 List<Evaluation> myEvaluations = new ArrayList<Evaluation>();
		 for(Evaluation  e:evaluations){
			 if(e.getUser().equals(user))
			 {
				 myEvaluations.add(e);
			 }
		 }
		return  myEvaluations;
		
	}

	@Override
	public List<Evaluation> RetrieveMyEvaluations(User user) {
		// TODO Auto-generated method stub
		List<Evaluation> evaluations = evaluationRepository.findAll();
		 List<Evaluation> myEvaluations = new ArrayList<Evaluation>();
		 for(Evaluation  e:evaluations){
			 if(e.getId_user_evaluated().equals(user.getId()))
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
			 if(e.getUser().equals(user)&& e.getEvaluation_id().equals(id))
			 {
				 evaluationRepository.delete(e);
				return new ResponseEntity("Evaluation deleted successfully!",HttpStatus.OK);
			 }
		 }
		 return new ResponseEntity("Evaluation was not deleted!",HttpStatus.CONFLICT);
	}

	@Override
	public ResponseEntity EditEvaluation(Long id, User user, Evaluation evaluation) {
		// TODO Auto-generated method stub
		List<Evaluation> evaluations = evaluationRepository.findAll();

		 for(Evaluation e: evaluations){
			 if(e.getUser().equals(user)&& e.getEvaluation_id().equals(id))
			 {
				e.setText(evaluation.getText());
				e.setObjet(evaluation.getObjet());
				//e.setId_user_evaluated(evaluation.getId_user_evaluated());
				evaluationRepository.save(e);
			
				return new ResponseEntity("Evaluation edited successfully!",HttpStatus.OK);
			 }
		 }
		 return new ResponseEntity("Evaluation was not edited!",HttpStatus.CONFLICT);
	}

}
