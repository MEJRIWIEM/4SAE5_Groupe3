package tn.esprit.spring.wecare.Repositories.Collaborators;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.Collaborators.Collaborator;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator,Long> {

}
