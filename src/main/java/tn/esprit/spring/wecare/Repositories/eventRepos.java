package tn.esprit.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.entity.Event;
@Repository
public interface  eventRepos  extends JpaRepository<Event , Long> , JpaSpecificationExecutor<Event>
   {


}
