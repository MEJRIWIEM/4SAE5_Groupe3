package tn.esprit.spring.wecare.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.wecare.Entities.Event;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    //issss
    List<Event> findByNameEquals(String name);

    List<Event> findByDateIsLessThanEqual(Date date);

    List<Event> findByDateEquals(Date date);

    List<Event> findByDateIsGreaterThanEqual(Date date);
}
