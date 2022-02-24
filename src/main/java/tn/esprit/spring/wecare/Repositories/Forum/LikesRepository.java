package tn.esprit.spring.wecare.Repositories.Forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.Forum.Likes;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long>{

}
