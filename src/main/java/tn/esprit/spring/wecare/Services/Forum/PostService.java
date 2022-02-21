package tn.esprit.spring.wecare.Services.Forum;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Post;

public interface PostService {
	public ResponseEntity addPost(Post post, User user);
	//see the list of posts
	public List<Post> RetrievePosts();
	//see the list of user's posts
	public List<Post> RetrieveMyPosts( User user);
	//see a specific post with his id
	public Post RetrievePost( Long id);
	//delete his post
	public ResponseEntity DeletePost( Long id, User user);
	//edit his post
	public ResponseEntity EditPost( Long id, User user, Post post);


}
