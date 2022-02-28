package tn.esprit.spring.wecare.Controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.wecare.Configuration.Files.FileDB;
import tn.esprit.spring.wecare.Configuration.Files.FileDBRepository;
import tn.esprit.spring.wecare.Entities.ERole;
import tn.esprit.spring.wecare.Entities.PasswordResetToken;
import tn.esprit.spring.wecare.Entities.RefreshToken;
import tn.esprit.spring.wecare.Entities.Role;
import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Payloads.Requests.LoginRequest;
import tn.esprit.spring.wecare.Payloads.Requests.PasswordReset;
import tn.esprit.spring.wecare.Payloads.Requests.SignupRequest;
import tn.esprit.spring.wecare.Payloads.Requests.TokenRefreshRequest;
import tn.esprit.spring.wecare.Payloads.Responses.JwtResponse;
import tn.esprit.spring.wecare.Payloads.Responses.MessageResponse;
import tn.esprit.spring.wecare.Payloads.Responses.TokenRefreshResponse;
import tn.esprit.spring.wecare.Repositories.PasswordTokenRepository;
import tn.esprit.spring.wecare.Repositories.RoleRepository;
import tn.esprit.spring.wecare.Repositories.SaveEmployeeToDb;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Security.TokenRefreshException;
import tn.esprit.spring.wecare.Security.jwt.JwtUtils;
import tn.esprit.spring.wecare.Security.services.RefreshTokenService;
import tn.esprit.spring.wecare.Security.services.UserDetailsImpl;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public final class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	FileDBRepository fileDBRepository;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
    private JavaMailSender emailSender;

    @Autowired
    PasswordTokenRepository passwordTokenRepository;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    SaveEmployeeToDb employeeRepo;

	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		

		String jwt = jwtUtils.generateJwtToken(userDetails);
	    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 refreshToken.getToken(),
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getFirstname(),
												 userDetails.getLastname(),
												 userDetails.getPhoto(),
												 userDetails.getNumTel(),
												 userDetails.getDepartement(),
												 userDetails.getEmail(),			 											
												 roles));
	}
	
	
	@PostMapping("/refreshtoken")
	  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
	    String requestRefreshToken = request.getRefreshToken();
	    return refreshTokenService.findByToken(requestRefreshToken)
	        .map(refreshTokenService::verifyExpiration)
	        .map(RefreshToken::getUser)
	        .map(user -> {
	          String token = jwtUtils.generateTokenFromUsername(user.getUsername());
	          return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
	        })
	        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
	            "Refresh token is not in database!"));
	  }
	
	

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestPart("user") SignupRequest signUpRequest, @RequestPart(value="file",required=false) MultipartFile file)throws IOException {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 signUpRequest.getFirstname(),
							 signUpRequest.getLastname(),
							 signUpRequest.getPhoto(),
							 signUpRequest.getFileDB(),
							 signUpRequest.getNumTel(),
							 signUpRequest.getDepartement(),
							 encoder.encode(signUpRequest.getPassword()));
							 
		

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		
		if(file!=null){
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
	        fileDBRepository.save(FileDB);
	        user.setFileDB(FileDB);
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
// Create password reset token
	
	public void createPasswordResetTokenForUser(User user, String token) {
		
	    PasswordResetToken myToken = new PasswordResetToken(token, user);
	    passwordTokenRepository.save(myToken);
	}
	
	
	//Send mail
	private SimpleMailMessage constructResetTokenEmail(
			  String contextPath, String token, User user) {
			    String url = contextPath + "/user/changePassword?token=" + token;
			    //String message = messages.getMessage("message.resetPassword", null, locale);
			    return constructEmail("Reset Password", url, user);
			}

			private SimpleMailMessage constructEmail(String subject, String body, 
			  User user) {
			    SimpleMailMessage email = new SimpleMailMessage();
			    email.setSubject(subject);
			    email.setText(body);
			    email.setTo(user.getEmail());
			    email.setFrom("noreply.wecare.tn@gmail.com");
			    return email;
			}
	
	
	
	//password reset
	
	@PostMapping("/forgetPassword")
	public ResponseEntity<?> resetPassword(@RequestParam("email") String userEmail) {
		User user = userRepository.findByEmail(userEmail);
		if (user == null) {
	        throw new UsernameNotFoundException("Email not found");
	    }
	    String token = UUID.randomUUID().toString();
	    createPasswordResetTokenForUser(user, token);
	    emailSender.send(constructResetTokenEmail("http://localhost:8089/SpringMVC/api/auth/resetPassword",token, user));
		return ResponseEntity.ok(new MessageResponse("token sent!"));

	}
	
	public String validatePasswordResetToken(String token) {
	    final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);

	    return !isTokenFound(passToken) ? "invalidToken"
	            : isTokenExpired(passToken) ? "expired"
	            : null;
	}

	private boolean isTokenFound(PasswordResetToken passToken) {
	    return passToken != null;
	}

	private boolean isTokenExpired(PasswordResetToken passToken) {
	    final Calendar cal = Calendar.getInstance();
	    return passToken.getExpiryDate().before(cal.getTime());
	}
	
	
	public void changeUserPassword(User user, String password) {
	    user.setPassword(encoder.encode(password));
	    userRepository.save(user);
	}
	
	

	
	@PostMapping("/resetPassword")
	public ResponseEntity<?> savePassword(@Valid @RequestBody PasswordReset passwordDto) {
		String result = validatePasswordResetToken(passwordDto.getToken());
		if(result != null) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error Token is : "+result));
	    }
		
		User user = passwordTokenRepository.findByToken(passwordDto.getToken()).getUser();
		if(user.getEmail() != null) {
	        changeUserPassword(user, passwordDto.getNewPassword());
			passwordTokenRepository.delete(passwordTokenRepository.findByToken(passwordDto.getToken()));
	        return ResponseEntity.ok(new MessageResponse("Password changed successfully!"));
	        
	        
		}
	        else
	        {
	        	return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error Token is : "+result));}
		
		
	

	}
	
	
	/*public List<String> emailList(){
		List<String> address = new ArrayList<String>();
		List<String> usersAddress = new ArrayList<String>();
		List<String> emailAddress = new ArrayList<String>();
		


		List<EmployeeList> employee = employeeRepo.findAll();
		List<User> users = userRepository.findAll();

         for (User x:users){	
        	 usersAddress.add(x.getEmail());
		   }
         
		for (EmployeeList i:employee){	
			address.add(i.getEmail());
		}
		
		for (String i : address)
		{
			while (usersAddress.contains(i)==false){
				emailAddress.add(i);
			}
			
		}
		
		return emailAddress;
		
	}
		*/	
	
	
	
}
