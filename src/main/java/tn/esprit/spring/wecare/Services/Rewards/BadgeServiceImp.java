package tn.esprit.spring.wecare.Services.Rewards;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import tn.esprit.spring.wecare.Configuration.Files.FileDB;
import tn.esprit.spring.wecare.Configuration.Files.FileDBRepository;
import tn.esprit.spring.wecare.Configuration.Files.FileStorageService;
import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Rewards.Badge;
import tn.esprit.spring.wecare.Entities.Rewards.CategorieB;
import tn.esprit.spring.wecare.Payloads.Responses.MessageResponse;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Repositories.Rewards.BadgeRepository;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;




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
    private JavaMailSender javaMailSender;

	

	@Override 
	@Transactional
	public ResponseEntity affecterBadgeUser(Long id, User user,Long points) throws TwitterException, WriterException, IOException, MessagingException {
		
		
		Badge badge= badgeRepository.findById(id).orElse(null);
		if(points>=badge.getMinPoints())
		{
			user.getBadges().add(badge);
			badgeRepository.save(badge);
			userRepository.save(user);
			TwitterFactory tf = new TwitterFactory();
	        Twitter twitter = tf.getInstance();
	        twitter.updateStatus("NEW BADGE UNLOCKED!"+id);
	        generateQRCodeImage("text",300,300,"./src/main/resources/QRCode"+id+user.getId()+".png");
	        
	        sendMailWithAttachment("hayfa.ouni@esprit.tn","new badge unlocked with a qr code ","here is your qr code ,to  enjoy it with our collaborators","C:/Users/PC-S9ZI/Desktop/github/4SAE5_Groupe3/src/main/resources/QRCode"+id+user.getId()+".png");
	       

		}
		else{
			TwitterFactory tf = new TwitterFactory();
	        Twitter twitter = tf.getInstance();
	        twitter.updateStatus(badge.getMinPoints()-points +"more points to unlock badge!"+id);
			 return new ResponseEntity("badge was not unlocked, check points!",HttpStatus.CONFLICT);
		}
		return null;
	
		
	}
	
	@Override 
	public ResponseEntity addBadge(MultipartFile file, Badge badge ,User user) throws IOException, TwitterException{
		// TODO Auto-generated method stub
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
		List<Badge> badges = badgeRepository.findAll();
		
		
	
	  /*  for(Badge b: badges){
			 if(stringSimilarity.similarity(b.getIcon(), badge.getIcon())>0.500 ){
					return new ResponseEntity("A similar badge already exists! Try to enter a new name please.", HttpStatus.CREATED);
					
				}
		 }*/
		 
			if (file != null) {
				fileDBRepository.save(FileDB);
				badge.setFileDB(FileDB);

			}

		badge.setFileURL(ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(FileDB.getId())
				.toUriString());
		badgeRepository.save(badge);
		fileDBRepository.save(FileDB);
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
				b.setName(badge.getName());
				b.setMinPoints(badge.getMinPoints());
				b.setMaxPoints(badge.getMaxPoints());
				b.setN_votes(badge.getN_votes());
				b.setFileURL(ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(FileDB.getId())
						.toUriString());
				b.setFileDB(badge.getFileDB());
				badgeRepository.save(b);
				return new ResponseEntity("Badge edited successfully!", HttpStatus.OK);
			}
		}
		return new ResponseEntity("Badge was not edited!", HttpStatus.CONFLICT);
		
		

	}
	
	
	public  void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
       
    }


	 public void sendMailWithAttachment(String toEmail,
             String body,
             String subject,
             String attachment) throws MessagingException {
MimeMessage mimeMessage=javaMailSender.createMimeMessage();
MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
mimeMessageHelper.setFrom("noreply.wecare.tn@gmail.com");
mimeMessageHelper.setTo(toEmail);
mimeMessageHelper.setText(body);
mimeMessageHelper.setSubject(subject);

FileSystemResource fileSystemResource=
new FileSystemResource(new File(attachment));
mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),
fileSystemResource);
javaMailSender.send(mimeMessage);
System.out.printf("Mail with attachment sent successfully..");

}

	

}
