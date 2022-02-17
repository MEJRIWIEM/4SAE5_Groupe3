package tn.esprit.spring.wecare.Services.Forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Post;
import tn.esprit.spring.wecare.Repositories.Forum.PostRepository;

@Service
public class PostServiceImp implements PostService{
	@Autowired
	PostRepository postRepository;

	@Override
	public void addPost(Post post, User user) {
		post.setUser(user);
		postRepository.save(post);
		
	}

}
