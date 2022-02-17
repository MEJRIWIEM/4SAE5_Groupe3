package tn.esprit.spring.wecare.Controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.wecare.Entities.ERole;
import tn.esprit.spring.wecare.Entities.Role;
import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Repositories.RoleRepository;
import tn.esprit.spring.wecare.Repositories.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
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


}
