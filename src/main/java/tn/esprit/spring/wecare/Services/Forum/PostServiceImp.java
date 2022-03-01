package tn.esprit.spring.wecare.Services.Forum;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties.Storage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tn.esprit.spring.wecare.Configuration.Files.FileDB;
import tn.esprit.spring.wecare.Configuration.Files.FileDBRepository;
import tn.esprit.spring.wecare.Configuration.Files.FileStorageService;
import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Post;
import tn.esprit.spring.wecare.Repositories.Forum.PostRepository;

@Service
public class PostServiceImp implements PostService {
	@Autowired
	PostRepository postRepository;
	@Autowired
	FileDBRepository fileDBRepository;
	@Autowired
	private FileStorageService storageService;

	@Override
	public List<Post> RetrievePosts() {
		return postRepository.findAll();
	}

	@Override
	public List<Post> RetrievePostsWithFile() {
		List<Post> posts = postRepository.findAll();
		List<Post> affichages = new ArrayList<>();
		for (Post p : posts) {
			affichages.add(p);
		}
		return affichages;
	}

	@Override
	public List<Post> RetrieveMyPosts(User user) {
		List<Post> posts = postRepository.findAll();
		List<Post> myPosts = new ArrayList<Post>();
		for (Post p : posts) {
			if (p.getUser().equals(user)) {
				myPosts.add(p);
			}
		}
		return myPosts;

	}

	@Override
	public Post RetrievePost(Long id) {
		return postRepository.findById(id).orElse(null);
	}

	@Override
	public ResponseEntity DeletePost(Long id, User user) {
		List<Post> posts = postRepository.findAll();

		for (Post p : posts) {
			if (p.getUser().equals(user) && p.getIdPost().equals(id)) {
				postRepository.delete(p);
				return new ResponseEntity("Post deleted successfully!", HttpStatus.OK);
			}
		}
		return new ResponseEntity("Post was not deleted!", HttpStatus.CONFLICT);

	}

	@Override
	@Transactional
	public ResponseEntity EditPost(MultipartFile file, Long id, User user, Post post) throws IOException {
		List<Post> posts = postRepository.findAll();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

		if (file != null) {
			fileDBRepository.save(FileDB);
			post.setFileDB(FileDB);

		}

		for (Post p : posts) {
			if (p.getUser().equals(user) && p.getIdPost().equals(id)) {
				fileDBRepository.delete(p.getFileDB());
				p.setText(post.getText());
				p.setTitle(post.getTitle());
				p.setTimestamp(LocalDateTime.now());
				p.setFileURL(ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(FileDB.getId())
						.toUriString());
				p.setFileDB(post.getFileDB());
				postRepository.save(p);
				return new ResponseEntity("Post edited successfully!", HttpStatus.OK);
			}
		}
		return new ResponseEntity("Post was not edited!", HttpStatus.CONFLICT);

	}

	@Override
	public ResponseEntity addPost(MultipartFile file, Post post, User user) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
		if (file != null) {
			fileDBRepository.save(FileDB);
			post.setFileDB(FileDB);

		}
		post.setUser(user);
		post.setTimestamp(LocalDateTime.now());
		post.setFileURL(ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(FileDB.getId())
				.toUriString());
		postRepository.save(post);
		fileDBRepository.save(FileDB);
		return new ResponseEntity("Post created successfully!", HttpStatus.CREATED);
	}

}
