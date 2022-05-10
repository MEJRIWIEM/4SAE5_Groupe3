package tn.esprit.spring.wecare.Services.Forum;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Post;

public interface PostService {
	public ResponseEntity addPost(MultipartFile file,Post post, User user) throws IOException;
	//see the list of posts
	public List<Post> RetrievePosts();
	//see the list of user's posts
	public List<Post> RetrieveMyPosts( User user);
	//see a specific post with his id
	public Post RetrievePost( Long id);
	//delete his post
	public ResponseEntity DeletePost( Long id, User user);
	//edit his post
	public List<Post>  RetrievePostsWithFile();
	public ResponseEntity EditPost(MultipartFile file,Long id, User user, Post post) throws IOException;

}
