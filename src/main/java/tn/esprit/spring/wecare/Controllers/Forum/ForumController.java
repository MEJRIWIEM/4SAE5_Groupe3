package tn.esprit.spring.wecare.Controllers.Forum;

import static org.assertj.core.api.Assertions.not;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import tn.esprit.spring.wecare.Configuration.Files.FileStorageService;
import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Comment;
import tn.esprit.spring.wecare.Entities.Forum.Likes;
import tn.esprit.spring.wecare.Entities.Forum.Post;
import tn.esprit.spring.wecare.Repositories.UserRepository;
import tn.esprit.spring.wecare.Repositories.Forum.CommentRepository;
import tn.esprit.spring.wecare.Repositories.Forum.LikesRepository;
import tn.esprit.spring.wecare.Repositories.Forum.PostRepository;
import tn.esprit.spring.wecare.Services.Forum.CommentService;
import tn.esprit.spring.wecare.Services.Forum.LikesService;
import tn.esprit.spring.wecare.Services.Forum.NotificationService;
import tn.esprit.spring.wecare.Services.Forum.PostServiceImp;
import java.awt.*;

import java.net.URISyntaxException;
import java.net.URL;
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
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
	@Autowired
    CommentRepository commentRepo;
	@Autowired
	CommentService commentService;
	@Autowired
	PostRepository postRepo;
	@Autowired
	LikesRepository likesRepo;
	@Autowired
	LikesService likesService;
	// add a post with a file
	@PostMapping("/addPostupload")
	public ResponseEntity<Object> addPostUploadFile(@RequestPart(value = "file", required = false) MultipartFile file,
			@RequestPart("post") Post post) throws IOException {
		User us = getTheCurrentUser();
		return PostService.addPost(file, post, us);

	}

	@PostMapping("/addPostupload2")
	public ResponseEntity<Object> addPostUploadFile2(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "post")  String  post) throws IOException {
		Post p = new ObjectMapper().readValue(post, Post.class);
		User us = getTheCurrentUser();
		return PostService.addPost(file, p, us);

	}
	@PostMapping
	public ResponseEntity<Object> addPostUploadFile(
			@RequestBody Post post)  {
		User us = getTheCurrentUser();
		return PostService.addPost( post, us);

	}
	@GetMapping("/{idPost}")
	public  ResponseEntity<Boolean> statusLike(
			@PathVariable("idPost") Long idPost)  {
		User us = getTheCurrentUser();
		return likesService.status(us, idPost);

	}
	// edit his post
	@PutMapping("/EditPost/{id}")
	public ResponseEntity<Object> EditPost(@RequestPart(value = "file", required = false) MultipartFile file,
			@PathVariable("id") Long id, @RequestPart("post") Post post) throws IOException {
		User us = getTheCurrentUser();
		return PostService.EditPost(file, id, us, post);
	}
	@PutMapping("/update")
	public ResponseEntity<Object> EditPost(
			 @RequestBody Post post) throws IOException {
		User us = getTheCurrentUser();
		System.out.println("je suis executé*************");
		return PostService.Edit( us, post);
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
		System.out.println("*************NUMBE********************"+commentRepo.CommentsNumber(PostService.RetrievePost(id)));
		return PostService.RetrievePost(id);
	}
	
	@GetMapping("/NbrCommentsPost/{id}")
	public  long nbrCommentsByPost(@PathVariable("id") Long id){
		return commentRepo.CommentsNumber(PostService.RetrievePost(id));
	}
	@GetMapping("/NbrLikesPost/{id}")
	public  long nbrLikessByPost(@PathVariable("id") Long id){
		return likesRepo.likesNumber(PostService.RetrievePost(id));
	}
	@GetMapping("/CommentsByPost/{id}")
	public  Set<Comment> CommentsByPost(@PathVariable("id") Long id){
		return (Set<Comment>) commentService.commentsByPost(PostService.RetrievePost(id));
	}
	
	@GetMapping("/totalPosts")
	public long total(){
		return  postRepo.totalPosts();
	}

	// delete his post
	@DeleteMapping("/DeletePost/{id}")
	public ResponseEntity<Object> DeletePost(@PathVariable("id") Long id) {
		User us = getTheCurrentUser();
		System.out.println("************************executed**************");
		return PostService.DeletePost(id, us);
	}

	// get the current user
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
		List<String> mynotif = notificationService.showMyNotif(us);
		Collections.reverse(mynotif);
		System.out.println("title most liked post"+PostService.MostLiked().getTitle());
		return mynotif;

	}

	@GetMapping("/MostLiked")
	public Post show() {
		return PostService.MostLiked();
	}
	
	@GetMapping("/Linkedin")
	public URI ShareOnLinkedin() throws URISyntaxException {
		String myUrl = "https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=782v4yfo7xy5jw&redirect_uri=https://oauth.pstmn.io/v1/callback&state=foobar&scope=r_liteprofile%20r_emailaddress%20w_member_social";
		URI myURI = new URI(myUrl);
		return myURI;
		/*
		 * return ResponseEntity.status(HttpStatus.FOUND) .location(URI.create(
		 * "https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=782v4yfo7xy5jw&redirect_uri=https://oauth.pstmn.io/v1/callback&state=foobar&scope=r_liteprofile%20r_emailaddress%20w_member_social"
		 * )) .build();
		 */}

	@GetMapping("/getToken")
	public String getToken(@RequestBody String code) {
		System.out.println(code);

		try {
			String string = "https://www.linkedin.com/oauth/v2/accessToken?grant_type=authorization_code&code=" + code
					+ "&redirect_uri=https://oauth.pstmn.io/v1/callback&client_id=782v4yfo7xy5jw&client_secret=SQEafEMTbtmQ1juw";
			System.out.println(string);
			URL url = new URL(string);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
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

	@GetMapping("/shareOnLinkedin/{id}/")
	public void getIdUser(@PathParam("token") String token, @PathVariable("id") Long id1) {
		String y = Character.toString( (char) 128_512 );
		Post post = PostService.RetrievePost(id1);
		String content = "\uD83D\uDC8C"+post.getTitle().toUpperCase()+"\uD83D\uDC8C"
				+ System.lineSeparator()+post.getText()+System.lineSeparator()+System.lineSeparator()
				+"\u00A9"+ "Writer : " +post.getUser().getFirstname()+", www.wecare.tn";
		JSONObject jsonObj = null;
	    try {
	        jsonObj = new JSONObject(content);
	    } catch (JSONException e) {
	        e.printStackTrace();
	    }
		
		String userId = "";
		System.out.println(token);

		try {
			String string = "https://api.linkedin.com/v2/me?oauth2_access_token=" + token;
			System.out.println(string);
			URL url = new URL(string);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
		
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
			}
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				String id = output.substring(output.indexOf("\"id\"") + 6, output.indexOf("\"id\"") + 16);
				System.out.println(id);
				userId = id;

			}
			conn.disconnect();

		} catch (Exception e) {
			System.out.println("Exception in NetClientGet:- " + e);
		}
		String message;
		JSONObject json = new JSONObject();

	
		try {
			json.put("author", "urn:li:person:" + userId);
			json.put("lifecycleState", "PUBLISHED");
			JSONObject obj4 = new JSONObject();
			JSONObject item4 = new JSONObject();

			item4.put("com.linkedin.ugc.MemberNetworkVisibility", "PUBLIC");

			json.put("visibility", item4);

			JSONObject obj2 = new JSONObject();
			JSONObject obj5 = new JSONObject();

			obj2.put("shareMediaCategory", "NONE");

			JSONObject obj3 = new JSONObject();

			obj3.put("text",post.getTitle().toUpperCase()+System.lineSeparator()+System.lineSeparator()+post.getText()
			+System.lineSeparator()+System.lineSeparator()+"Author : "+post.getUser().getFirstname()+", www.wecare.tn");

			obj4.put("shareCommentary", obj3);

			obj4.put("shareMediaCategory", "NONE");

			obj5.put("com.linkedin.ugc.ShareContent", obj2);

			obj5.put("com.linkedin.ugc.ShareContent", obj4);

			json.put("specificContent", obj5);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		message = json.toString();
		System.out.println(message);
	
		String result = executePost("https://api.linkedin.com/v2/ugcPosts?oauth2_access_token=" + token,message);
		System.out.println(result);
		
		

	}
	public static String executePost(String targetURL, String requestJSON) {
        HttpURLConnection connection = null;
        InputStream is = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            //TODO may be prod or preprod api key
           
            connection.setRequestProperty("Content-Length", Integer.toString(requestJSON.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");  
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            System.out.println(requestJSON);
            DataOutputStream wr = new DataOutputStream (
            connection.getOutputStream());
            wr.writeBytes(requestJSON);
            wr.close();

            //Get Response  

            try {
                is = connection.getInputStream();
            } catch (IOException ioe) {
                if (connection instanceof HttpURLConnection) {
                    HttpURLConnection httpConn = (HttpURLConnection) connection;
                    int statusCode = httpConn.getResponseCode();
                    if (statusCode != 200) {
                        is = httpConn.getErrorStream();
                    }
                }
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(is));


            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
	
}
