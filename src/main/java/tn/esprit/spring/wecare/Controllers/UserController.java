package tn.esprit.spring.wecare.Controllers;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


import javax.validation.Valid;

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
import tn.esprit.spring.wecare.Configuration.Files.FileDB;
import tn.esprit.spring.wecare.Configuration.Files.FileDBRepository;
import tn.esprit.spring.wecare.Entities.ERole;
import tn.esprit.spring.wecare.Entities.Role;
import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.EmployeeList.EmployeeList;

import tn.esprit.spring.wecare.Payloads.Responses.MessageResponse;
import tn.esprit.spring.wecare.Repositories.RoleRepository;
import tn.esprit.spring.wecare.Repositories.SaveEmployeeToDb;
import tn.esprit.spring.wecare.Repositories.UserRepository;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
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
	FileDBRepository fileDBRepository;
	

	@Autowired
	 Job job;

	
	    //Get User List
		@GetMapping("/admin/employees")
		@PreAuthorize("hasRole('ADMIN')")
		public List<User> listEmployees (){
			return userRepository.findAll();
		}
		//Get Employees List
		@GetMapping("/admin/employeesList")
		@PreAuthorize("hasRole('ADMIN')")
		public List<EmployeeList> EmployeesTab (){
			return employeeRepo.findAll();
		}
		
		//Get User by id
		@GetMapping("/admin/employeeById/{id}")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
		public User employeeById (@PathVariable("id") Long userId){
			return userRepository.findById(userId).orElse(null);
		}
		
		//Update user Account 
		@PutMapping("/userEdit")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
		@ResponseBody
		public ResponseEntity<?> editMyAccount(@Valid @RequestBody User u ) throws IOException{
			String x = encoder.encode(u.getPassword());
            User us = getConnectedUser();
            System.out.println(x);
            System.out.println(us.getPassword());
            boolean verif = encoder.matches(u.getPassword(), us.getPassword());

           if (verif==false){
            	return ResponseEntity
    					.badRequest()
    					.body(new MessageResponse("Wrong Password"));
            }
            else{
            
            u.setRoles(us.getRoles());
            u.setFileDB(us.getFileDB());
			u.setId(us.getId());
			u.setPassword(x);
			userRepository.save(u);
			return ResponseEntity.ok(new MessageResponse("User Profile changed successfully!"));
 
            }
			
		}
		
		//Update Role Account to Admin
		@PutMapping("/RoleToAdmin/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		@ResponseBody
		public void editUserRoleA(@PathVariable("id") Long userId) {
		User  u =userRepository.getById(userId);
		Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		Set<Role> roles = new HashSet<>();
		roles.add(adminRole);
		u.setRoles(roles);}
				
		//Update Role Account to User
		@PutMapping("/RoleToUser/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		@ResponseBody
		public void editUserRoleU(@PathVariable("id") Long userId) {
		User  u =userRepository.getById(userId);
		Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		Set<Role> roles = new HashSet<>();
		roles.add(userRole);
		u.setRoles(roles);}
				
		//Delete user by id 
		@DeleteMapping("/deleteUser/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		@ResponseBody
		public void removeUser(@PathVariable("id") Long userId) {
		userRepository.deleteById(userId);
		}

	    //get user Details of the authorised user
		public User getConnectedUser() {
			String username;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
				} else {
				 username = principal.toString();
				}
			User us = userRepository.findByUsername(username).orElse(null);

			return us;
			
			
		}
		
        //Spring Batch Employees List to DB
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
		
		//Add Employees list from csv
		@PostMapping("/admin/employeesList")
		@PreAuthorize("hasRole('ADMIN')")
	    public void saveUser(@RequestParam("CSVfile") MultipartFile multipartFile) throws Exception  {
	         
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
		

        //Send RegistrationMAIL
	    @GetMapping("/sendRegistrationMail")
	    @PreAuthorize("hasRole('ADMIN')")

 	    public ResponseEntity<?> sendtoAll() {
		
		List<String> address = new ArrayList<String>();
		
		List<User> users = userRepository.findAll();
		
		
		List<String> addressUsers = new ArrayList<String>();

		
		for (User u : users){
			
			addressUsers.add(u.getEmail());   
		}
		

		List<EmployeeList> employee = employeeRepo.findAll();
		for (EmployeeList i:employee){	
			
			if(!addressUsers.contains(i.getEmail())){
			address.add(i.getEmail());
			}
		}
		
		
    if (address.size()==0)
    {
    	return ResponseEntity.badRequest()
				.body(new MessageResponse("Tous les employee ont deja un compte"));
    }else{
		
	    emailSender.send(constructEmailRegestration("Register Wecare","Welcome to our Team !!!"+"\n\n"+"Please visit this link below to create an account :"+"\n\n"+"www.wecare.com/signup",address));
		return ResponseEntity.ok(new MessageResponse("email sent!"));
    }
	}

	    //user upload profile photo
	    @PostMapping("/user/photo")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	    public void profilePic(@RequestParam("file") MultipartFile file) throws IOException{
	    	User u = getConnectedUser();
	    	if(file!=null){
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
		        fileDBRepository.save(FileDB);
		        u.setFileDB(FileDB);
		        userRepository.save(u);
			}
	    	
	    }
	    
	   
	    
	    
	    
}
