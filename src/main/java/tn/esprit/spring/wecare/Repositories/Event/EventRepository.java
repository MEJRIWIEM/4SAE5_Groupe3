package tn.esprit.spring.wecare.Repositories.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.Event.Event;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

}
