package tn.esprit.spring.wecare.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.spring.wecare.Entities.ERole;
import tn.esprit.spring.wecare.Entities.Role;

public interface RoleRepository  extends JpaRepository<Role, Long>{

	Optional<Role> findByName(ERole name);
}
