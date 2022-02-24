package tn.esprit.spring.wecare.Repositories.Rewards;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.Rewards.Badge;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {

}
