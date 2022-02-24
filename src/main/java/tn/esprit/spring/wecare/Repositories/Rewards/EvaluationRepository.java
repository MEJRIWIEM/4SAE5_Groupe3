package tn.esprit.spring.wecare.Repositories.Rewards;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.Rewards.Evaluation;


@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long>{

}
