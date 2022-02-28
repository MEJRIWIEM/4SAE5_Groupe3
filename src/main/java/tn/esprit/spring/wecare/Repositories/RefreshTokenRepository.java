package tn.esprit.spring.wecare.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import tn.esprit.spring.wecare.Entities.RefreshToken;
import tn.esprit.spring.wecare.Entities.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);
    @Modifying
    int deleteByUser(User user);
	
}
