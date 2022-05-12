package tn.esprit.spring.wecare.Controllers.Collaborators;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.wecare.Configuration.Files.FileDB;
import tn.esprit.spring.wecare.Configuration.Files.FileDBRepository;
import tn.esprit.spring.wecare.Configuration.Files.FileStorageService;
import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Collaborators.Advertising;
import tn.esprit.spring.wecare.Entities.Collaborators.Collaborator;
import tn.esprit.spring.wecare.Entities.Collaborators.Offer;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Repositories.Collaborators.AdsRepository;
import tn.esprit.spring.wecare.Repositories.Collaborators.CollaboratorRepository;
import tn.esprit.spring.wecare.Services.Collaborators.AdsService;
import tn.esprit.spring.wecare.Services.Collaborators.CollaboratorService;

@RestController
@RequestMapping("/api/advert")
@CrossOrigin(origins ="http://localhost:8081")

public class AdsController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	CollaboratorService collaboratorService;
	
	@Autowired
	
	AdsService adsService;
	@Autowired
	AdsRepository adsRepo;
	@Autowired
	FileDBRepository fileDBRepository;
	@Autowired
	  FileStorageService storageService;
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addAdvert/{id}")
	public ResponseEntity<?>  addAdvirtising(@RequestBody Advertising ads, @PathVariable("id") Long id){
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
		User us= userRepository.findByUsername(username).orElse(null);	
		return adsService.addAdvirtising(us, ads, id);
		
	}
	
	//user upload profile photo
    @PostMapping("/flayer")
    public void profilePic(@RequestParam("file") MultipartFile file) throws IOException{
		List<Advertising> ads = adsRepo.findAll();

		 for(Advertising collaborator: ads){
    	if(file!=null){
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
	        fileDBRepository.save(FileDB);
	        collaborator.setFileDB(FileDB);
	        adsRepo.save(collaborator);
		}}
    	
    }
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateAds/{id}")
	public ResponseEntity EditAdvertising(@PathVariable("id") Long id, @RequestBody Advertising ads) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
		User us= userRepository.findByUsername(username).orElse(null);	
		return adsService.EditAdvertising(us, id, ads);

	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/DeleteAds/{id}")
	public ResponseEntity DeleteAdvertising(@PathVariable("id") Long id) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
		User us= userRepository.findByUsername(username).orElse(null);	
		return adsService.DeleteAdvertising(us, id);
	}
	
	

	@GetMapping("/ListOfAds")
	public List<Advertising> RetrieveAds() {
		return 	adsService.RetrieveAds();
	}

	
	@GetMapping("/getAdsWithCollabortorId/{id}")
	public List<Advertising> getAdsWithCollabortorId(@PathVariable("id") Long id) {
		return adsService.getAdsWithCollabortorId(id);
	}
	
	@GetMapping("/RetriveAdsById/{id}")
	public Advertising RetrieveAdvertisingById(@PathVariable("id") Long id) {
		return adsService.RetrieveAdvertisingById(id);
	}
	
}
