package tn.esprit.spring.wecare.Services.Forum;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Post;
import tn.esprit.spring.wecare.Repositories.Forum.PostRepository;

@Service
public class PostServiceImp implements PostService{
	@Autowired
	PostRepository postRepository;

	@Override
	public ResponseEntity addPost(Post post, User user) {
		post.setUser(user);
		postRepository.save(post);
		return new ResponseEntity("Post created successfully!",HttpStatus.CREATED);
	}

	@Override
	public List<Post> RetrievePosts() {
		return 	 postRepository.findAll();
	}


	@Override
	public List<Post> RetrieveMyPosts(User user) {
		 List<Post> posts = postRepository.findAll();
		 List<Post> myPosts = new ArrayList<Post>();
		 for(Post p: posts){
			 if(p.getUser().equals(user))
			 {
				 myPosts.add(p);
			 }
		 }
		return  myPosts;
	
	}

	@Override
	public Post RetrievePost(Long id) {
		return  postRepository.findById(id).orElse(null);
	}

	@Override
	public ResponseEntity  DeletePost(Long id, User user) {
		List<Post> posts = postRepository.findAll();

		 for(Post p: posts){
			 if(p.getUser().equals(user)&& p.getIdPost().equals(id))
			 {
				postRepository.delete(p);
				return new ResponseEntity("Post deleted successfully!",HttpStatus.OK);
			 }
		 }
		 return new ResponseEntity("Post was not deleted!",HttpStatus.CONFLICT);
		
	}

	@Override
	public ResponseEntity EditPost(Long id, User user, Post post) {
		List<Post> posts = postRepository.findAll();

		 for(Post p: posts){
			 if(p.getUser().equals(user)&& p.getIdPost().equals(id))
			 {
				p.setText(post.getText());
				p.setTimestamp(post.getTimestamp());
				p.setPicture(post.getPicture());
				p.setTitle(post.getTitle());
				postRepository.save(p);
				return new ResponseEntity("Post edited successfully!",HttpStatus.OK);
			 }
		 }
		 return new ResponseEntity("Post was not edited!",HttpStatus.CONFLICT);

		
	}

	

	

}
