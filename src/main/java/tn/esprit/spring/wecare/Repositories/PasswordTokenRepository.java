package tn.esprit.spring.wecare.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.PasswordResetToken;
import tn.esprit.spring.wecare.Entities.User;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken,Long> {

	 PasswordResetToken findByToken(String token);
	 
	 PasswordResetToken findByUser(User user);

	
	 
}
