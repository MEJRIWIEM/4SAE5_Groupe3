package tn.esprit.spring.wecare.Services.Forum;

import tn.esprit.spring.wecare.Entities.User;
import tn.esprit.spring.wecare.Entities.Forum.Post;

public interface PostService {
	public void addPost(Post post, User user);

}
