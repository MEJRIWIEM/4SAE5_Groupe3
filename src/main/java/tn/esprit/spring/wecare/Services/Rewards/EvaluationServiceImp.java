package tn.esprit.spring.wecare.Services.Rewards;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	@Autowired
    private JavaMailSender emailSender;
	@Override
	public ResponseEntity addEvaluation(Evaluation evaluation, User user, Long id_user_evaluated) {
		// TODO Auto-generated method stub
	
		String output = BadWordFilter.getCensoredText(evaluation.getText());
		List<Evaluation> evaluations = evaluationRepository.findAll();
		 List<Evaluation> myEvaluations = new ArrayList<Evaluation>();
		 int times =0;
		 for(Evaluation  e:evaluations){
			 if(e.getId_user_evaluated().equals(id_user_evaluated) && e.getBan()==1)
			 {
				 times++;
				 
				
			 }
		 }
		
		
		
		if(output != evaluation.getText() )
		{
			if(times==0)
			{
				emailSender.send(constructEmailRegestration("Bad word detected first warning ","the evaluation was not sent because a bad word was used , next time you won't be able to evaluate the user!"+id_user_evaluated,"hayfa.ouni@esprit.tn"));
				evaluation.setText(output);
			    evaluation.setUser(user);
			    evaluation.setId_user_evaluated(id_user_evaluated);
			    evaluation.setBan(1);
			 	evaluationRepository.save(evaluation);
			 	
				return new ResponseEntity("evaluation not accepted ", HttpStatus.CREATED);
			}
			if  (times>0)
			{
				
				emailSender.send(constructEmailRegestration("Ban ","you are banned from evaluating the user"+id_user_evaluated+ "only if you are unbanned!","hayfa.ouni@esprit.tn"));
		
			}
			
		}
		else {
			//String BadWordsFound = BadwordFilter.BadWordsFound(evaluation.getText());
			evaluation.setText(evaluation.getText());
		    evaluation.setUser(user);
		    evaluation.setId_user_evaluated(id_user_evaluated);
		 	evaluationRepository.save(evaluation);
		 	return new ResponseEntity("evaluation  accepted ", HttpStatus.CREATED);
		 	
			
		}
		return null;
		
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
	private SimpleMailMessage constructEmailRegestration(String subject,String body,String  Address) {
	    SimpleMailMessage email = new SimpleMailMessage();
	    email.setSubject(subject);
	    email.setText(body);
	    email.setTo(Address);
	    email.setFrom("noreply.wecare.tn@gmail.com");
	    
	    return email;
	    
	}
	@Override
	public void unban(Long id_user_evaluated, Long banned_user_id) {
		// TODO Auto-generated method stub
		List<Evaluation> evaluations = evaluationRepository.findAll();
		 List<Evaluation> myEvaluations = new ArrayList<Evaluation>();
		 int times =0;
		 for(Evaluation  e:evaluations){
			 if(e.getId_user_evaluated().equals(id_user_evaluated) && e.getBan()==1 && e.getUser().getId()==banned_user_id)
			 {
				 e.setBan(0);
				 evaluationRepository.save(e);
				 emailSender.send(constructEmailRegestration("you got unbanned ","you can now evaluate user !"+id_user_evaluated+"again","hayfa.ouni@esprit.tn"));
			 }
		 }
		
	}

}
