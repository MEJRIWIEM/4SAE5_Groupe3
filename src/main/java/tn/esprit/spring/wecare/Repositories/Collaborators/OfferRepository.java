package tn.esprit.spring.wecare.Repositories.Collaborators;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.Collaborators.Offer;



@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {


	  Page<Offer> findByTypeOffer(String typeOffer, Pageable pageable);
	  Page<Offer> findByNameContaining(String name, Pageable pageable);}
