package tn.esprit.spring.wecare.Services.Rewards;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tn.esprit.spring.wecare.Configuration.Files.FileDB;
import tn.esprit.spring.wecare.Configuration.Files.FileDBRepository;
import tn.esprit.spring.wecare.Configuration.Files.FileStorageService;
import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.EmployeeList.EmployeeList;
import tn.esprit.spring.wecare.Entities.Rewards.Badge;
import tn.esprit.spring.wecare.Entities.Rewards.CategorieB;
import tn.esprit.spring.wecare.Entities.Rewards.StringSimilarity;
import tn.esprit.spring.wecare.Payloads.Responses.MessageResponse;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Repositories.Rewards.BadgeRepository;




@Service
public class BadgeServiceImp implements BadgeService {

	@Autowired
	BadgeRepository badgeRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	FileDBRepository fileDBRepository;
	@Autowired
	private FileStorageService storageService;
	@Autowired
    private JavaMailSender emailSender;
	StringSimilarity stringSimilarity;
	
	@Override 
	public ResponseEntity affecterBadgeUser(Long id, User user) {
		
		
		Badge badge= badgeRepository.findById(id).orElse(null);
		
			
		user.getBadges().add(badge);
					
					userRepository.save(user);
					badgeRepository.save(badge);
					
				
			
	
		return new ResponseEntity("Badge affecte successfully!", HttpStatus.CREATED);
		
	/*	Badge badge = badgeRepository.findById(id).orElse(null);

		       user.setBadges(badge); 
			 
				    badge.getUsers().add(user);
				    //user.getBadges().add(badge);
					badgeRepository.save(badge);
					userRepository.save(user);
					return new ResponseEntity("Badge affecte successfully!", HttpStatus.CREATED);
			 
		 
		
		/*if(user.getN_points()>=badge.getMinPoints() && user.getN_points()<=badge.getMaxPoints() )
		{
		}*/
		
		
		
		
	}
	
	@Override 
	public ResponseEntity addBadge(MultipartFile file, Badge badge ,User user) throws IOException{
		// TODO Auto-generated method stub
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
		List<Badge> badges = badgeRepository.findAll();
		
		
	
		/* for(Badge b: badges){
			 if(stringSimilarity.similarity(b.getIcon(), badge.getIcon())>0.500 ){
					return new ResponseEntity("A similar badge already exists! Try to enter a new name please.", HttpStatus.CREATED);
					
				}
		 }*/
		 
			if (file != null) {
				fileDBRepository.save(FileDB);
				badge.setFileDB(FileDB);

			}
		user.getBadges().add(badge);
		badge.setFileURL(ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(FileDB.getId())
				.toUriString());
		badgeRepository.save(badge);
		fileDBRepository.save(FileDB);
		emailSender.send(constructEmailRegestration("NEW BADGE UNLOCKED","CONGRATS , YOU UNLOCKED A NEW BADGE!","hayfa.ouni@esprit.tn"));
		return new ResponseEntity("Badge created successfully!", HttpStatus.CREATED);
		
	}
	@Override
	public List<Badge> RetrieveBadges() {
		// TODO Auto-generated method stub
		return 	 badgeRepository.findAll();
	}
	
	@Override
	public List<Badge> RetrieveBadgesWithFile() {
		List<Badge> badges = badgeRepository.findAll();
		List<Badge> affichages = new ArrayList<>();
		for (Badge b : badges) {
			affichages.add(b);
		}
		return affichages;
	}
	@Override
	public List<Badge> RetrieveMyBadges(User user) {
		// TODO Auto-generated method stub
		List<Badge> badges = badgeRepository.findAll();
		 List<Badge> myBadges = new ArrayList<Badge>();
		 for(Badge b: badges){
			 if(b.getUsers().equals(user))
			 {
				 myBadges.add(b);
			 }
		 }
		return  myBadges;
	}
	@Override
	public Badge RetrieveBadge(Long id) {
		// TODO Auto-generated method stub
		return  badgeRepository.findById(id).orElse(null);
		
	}
	@Override
	public ResponseEntity DeleteBadge(Long id, User user) {
		// TODO Auto-generated method stub
		List<Badge> badges = badgeRepository.findAll();

		 for(Badge b: badges){
			 if(b.getUsers().equals(user)&& b.getId().equals(id))
			 {
				badgeRepository.delete(b);
				return new ResponseEntity("badge deleted successfully!",HttpStatus.OK);
			 }
		 }
		 return new ResponseEntity("badge was not deleted!",HttpStatus.CONFLICT);
	}
	@Override
	public ResponseEntity EditBadge(MultipartFile file,Long id, User user, Badge badge) throws IOException {
		// TODO Auto-generated method stub
		List<Badge> badges = badgeRepository.findAll();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

		if (file != null) {
			fileDBRepository.save(FileDB);
			badge.setFileDB(FileDB);

		}

		for (Badge b : badges) {
			if (b.getUsers().equals(user) && b.getId().equals(id)) {
				fileDBRepository.delete(b.getFileDB());
				b.setMinPoints(badge.getMinPoints());
				b.setMaxPoints(badge.getMaxPoints());
				b.setN_votes(badge.getN_votes());
				b.setCategorie(badge.getCategorie());
				b.setFileURL(ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(FileDB.getId())
						.toUriString());
				b.setFileDB(badge.getFileDB());
				badgeRepository.save(b);
				return new ResponseEntity("Badge edited successfully!", HttpStatus.OK);
			}
		}
		return new ResponseEntity("Badge was not edited!", HttpStatus.CONFLICT);

	}
	private SimpleMailMessage constructEmailRegestration(String subject,String body,String  Address) {
	    SimpleMailMessage email = new SimpleMailMessage();
	    email.setSubject(subject);
	    email.setText(body);
	    email.setTo(Address);
	    email.setFrom("noreply.wecare.tn@gmail.com");
	    
	    return email;
	    
	}
	
	




	

}
