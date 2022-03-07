package tn.esprit.spring.wecare.Repositories;

import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserRepository extends JpaRepository<User, Long>  {

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	public User findByEmail(String email);
}
