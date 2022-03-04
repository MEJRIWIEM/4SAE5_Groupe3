package tn.esprit.spring.wecare.Controllers.Forum;

import static org.assertj.core.api.Assertions.not;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.mockito.internal.stubbing.answers.ReturnsElementsOf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.jsonwebtoken.lang.Arrays;
import tn.esprit.spring.wecare.Configuration.Files.FileDB;
import tn.esprit.spring.wecare.Configuration.Files.FileStorageService;
import tn.esprit.spring.wecare.Configuration.Files.ResponseFile;
import tn.esprit.spring.wecare.Configuration.Files.ResponseMessage;
import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Comment;
import tn.esprit.spring.wecare.Entities.Forum.Likes;
import tn.esprit.spring.wecare.Entities.Forum.Post;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Services.Forum.NotificationService;
import tn.esprit.spring.wecare.Services.Forum.PostServiceImp;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@RestController
@RequestMapping("/api/forumCrud")
public class ForumController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostServiceImp PostService;
	@Autowired
	private FileStorageService storageService;
	@Autowired
	NotificationService notificationService;



	// add a post with a file
	@PostMapping("/addPostupload")
	public ResponseEntity<Object> addPostUploadFile(@RequestPart(value = "file", required = false) MultipartFile file,
			@RequestPart("post") Post post) throws IOException {
		User us = getTheCurrentUser();
		return PostService.addPost(file, post, us);

	}
	// edit his post
	@PutMapping("/EditPost/{id}")
	public ResponseEntity<Object> EditPost(@RequestPart(value = "file", required = false) MultipartFile file,@PathVariable("id")Long id, @RequestPart("post") Post post) throws IOException {
		User us = getTheCurrentUser();
		return PostService.EditPost(file, id, us, post);
	}

	// see the list of posts
	@GetMapping("/ListOfPosts")
	public List<Post> RetrievePosts() {
		return (List<Post>) PostService.RetrievePostsWithFile();
	}

	// see my posts
	@GetMapping("/ListOfMyPosts")
	public List<Post> RetrieveMyPosts() {
		User us = getTheCurrentUser();
		return PostService.RetrieveMyPosts(us);
	}

	// see a specific post with his id
	@GetMapping("/RetrivePost/{id}")
	public Post RetrievePost(@PathVariable("id") Long id) {
		return PostService.RetrievePost(id);
	}

	// delete his post
	@DeleteMapping("/DeletePost/{id}")
	public ResponseEntity<Object> DeletePost(@PathVariable("id") Long id) {
		User us = getTheCurrentUser();
		return PostService.DeletePost(id, us);
	}


	
	//get the current user
	public User getTheCurrentUser() {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User us = userRepository.findByUsername(username).orElse(null);
		return us;
	}
	@GetMapping("/showMyNotifications")
	public List<String> showMyNotifications() {
		User us = getTheCurrentUser();

		return notificationService.showMyNotif(us);
		
	}
	@GetMapping("/shareOnLinkedin")
	public   URI ShareOnLinkedin() throws URISyntaxException{
		String myUrl = "https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=782v4yfo7xy5jw&redirect_uri=https://oauth.pstmn.io/v1/callback&state=foobar&scope=r_liteprofile%20r_emailaddress%20w_member_social";
		URI myURI = new URI(myUrl);
		return myURI;
		/*
		 return ResponseEntity.status(HttpStatus.FOUND)
			        .location(URI.create("https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=782v4yfo7xy5jw&redirect_uri=https://oauth.pstmn.io/v1/callback&state=foobar&scope=r_liteprofile%20r_emailaddress%20w_member_social"))
			        .build();
	*/}
	@GetMapping("/getToken")
	public String getToken(@RequestBody String code){
		 System.out.println(code);

		 try {
			 String string = "https://www.linkedin.com/oauth/v2/accessToken?grant_type=authorization_code&code="+code+"&redirect_uri=https://oauth.pstmn.io/v1/callback&client_id=782v4yfo7xy5jw&client_secret=SQEafEMTbtmQ1juw";
			 System.out.println(string);
			 URL url = new URL(string);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Accept", "application/json");
	            if (conn.getResponseCode() != 200) {
	                throw new RuntimeException("Failed : HTTP Error code : "
	                        + conn.getResponseCode());
	            }
	            InputStreamReader in = new InputStreamReader(conn.getInputStream());
	            BufferedReader br = new BufferedReader(in);
	            String output;
	            while ((output = br.readLine()) != null) {
	                System.out.println(output);
	                return output;
	                
	            }
	            conn.disconnect();

	        } catch (Exception e) {
	            System.out.println("Exception in NetClientGet:- " + e);
	        }
		return "Token has expired, get a new one.";
	}
	@GetMapping("/getIdUser")
	public void getIdUser(@RequestBody String token){
System.out.println(token);

		 try {
			 String string = "https://api.linkedin.com/v2/me?oauth2_access_token="+token;
			 System.out.println(string);
			 URL url = new URL(string);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Accept", "application/json");
	            if (conn.getResponseCode() != 200) {
	                throw new RuntimeException("Failed : HTTP Error code : "
	                        + conn.getResponseCode());
	            }
	            InputStreamReader in = new InputStreamReader(conn.getInputStream());
	            BufferedReader br = new BufferedReader(in);
	            String output;
	            while ((output = br.readLine()) != null) {
	                System.out.println(output);
	              
	                
	            }
	            conn.disconnect();

	        } catch (Exception e) {
	            System.out.println("Exception in NetClientGet:- " + e);
	        }
	}
}
