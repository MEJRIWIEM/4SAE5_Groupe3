package tn.esprit.spring.wecare.Configuration.Files;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
}