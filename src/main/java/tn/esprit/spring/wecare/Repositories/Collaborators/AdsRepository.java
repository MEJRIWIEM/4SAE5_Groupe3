package tn.esprit.spring.wecare.Repositories.Collaborators;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.Collaborators.Advertising;

@Repository
public interface AdsRepository extends JpaRepository<Advertising,Long>{
	
	  Page<Advertising> findByTypeAd(String typeAd, Pageable pageable);
	  Page<Advertising> findByNameContaining(String name, Pageable pageable);

}
