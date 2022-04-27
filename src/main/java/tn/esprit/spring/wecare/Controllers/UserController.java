package tn.esprit.spring.wecare.Controllers;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.wecare.Configuration.FileUploadUtil;
import tn.esprit.spring.wecare.Entities.ERole;
import tn.esprit.spring.wecare.Entities.Role;
import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.EmployeeList.EmployeeList;
import tn.esprit.spring.wecare.Payloads.Responses.MessageResponse;
import tn.esprit.spring.wecare.Repositories.RoleRepository;
import tn.esprit.spring.wecare.Repositories.SaveEmployeeToDb;
import tn.esprit.spring.wecare.Repositories.UserRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/userCrud")
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	RoleRepository roleRepository;
	@Autowired 
	UserDetailsService userService;
	
	@Autowired
	 JobLauncher jobLauncher;
	@Autowired
    SaveEmployeeToDb employeeRepo;
	@Autowired
    private JavaMailSender emailSender;

	@Autowired
	 Job job;

	
	    //Get Employees List
		@GetMapping("/admin/employees")
		@PreAuthorize("hasRole('ADMIN')")
		public List<User> listEmployees (){
			return userRepository.findAll();
		}
		//Get Employee by id
		@GetMapping("/admin/employeeById/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		public User employeeById (@PathVariable("id") Long userId){
			return userRepository.findById(userId).orElse(null);
		}
		
		//Update user Account 
		@PutMapping("/userEdit")
		@PreAuthorize("hasRole('USER')")
		@ResponseBody
		public User editMyAccount(@RequestBody User u){
			String x = encoder.encode(u.getPassword());
			Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			Set<Role> roles = new HashSet<>();
			roles.add(userRole);
			u.setRoles(roles);
			u.setPassword(x);
			return userRepository.save(u);
			
			
		}
		
		//Delete user by id 
		@DeleteMapping("/deleteUser/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		@ResponseBody
		public void removeUser(@PathVariable("id") Long userId) {
		userRepository.deleteById(userId);
		}

	   //get user Details of the authorised user
		
		@GetMapping("/admin")
		@PreAuthorize("hasRole('ADMIN')")
		public String adminAccess() {
			String username;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
				} else {
				 username = principal.toString();
				}
			User us= userRepository.findByUsername(username).orElse(null);
			
			return "Admin Board." + us.getId() ;
		}
		
	
		public void EmployeesList (String filePath)throws Exception{
			JobParameters jobParameters = new JobParametersBuilder()
	                .addString("date", UUID.randomUUID().toString())
	                .addLong("JobId",System.currentTimeMillis())
	                .addLong("time",System.currentTimeMillis())
	                .addString("fullPathFileName", filePath)
	                .toJobParameters();
			JobExecution execution = jobLauncher.run(job, jobParameters);
			System.out.println("STATUS :: "+execution.getStatus());
		}
		
		
		@PostMapping("/admin/employeesList")
		@PreAuthorize("hasRole('ADMIN')")
	    public void saveUser(@RequestParam("image") MultipartFile multipartFile) throws Exception  {
	         
	        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	 
	        String uploadDir = "CsvFiles/";
	 
	        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
	        
	        String filePath = uploadDir+fileName;
	        
	        EmployeesList (filePath);
			
				
				
						
	        
		}
		
		private SimpleMailMessage constructEmailRegestration(String subject,String body,List<String> Address) {
		    SimpleMailMessage email = new SimpleMailMessage();
		    email.setSubject(subject);
		    email.setText(body);
		    email.setTo(Address.toArray(new String [Address.size()]));
		    email.setFrom("noreply.wecare.tn@gmail.com");
		    
		    return email;
		    
		}



	@PostMapping("/sendRegestrationMail")
	@PreAuthorize("hasRole('ADMIN')")

	public ResponseEntity<?> sendtoAll() {
		
		List<String> address = new ArrayList<String>();
		


		List<EmployeeList> employee = employeeRepo.findAll();
		for (EmployeeList i:employee){	
			
			address.add(i.getEmail());
		}
		
		
    if (address.size()==0)
    {
    	return ResponseEntity.badRequest()
				.body(new MessageResponse("Tous les employee ont deja un compte"));
    }else{
		
	    emailSender.send(constructEmailRegestration("Register Wecare","lien",address));
		return ResponseEntity.ok(new MessageResponse("email sent!"));
    }
	}


}
