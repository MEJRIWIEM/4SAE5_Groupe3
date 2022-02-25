package tn.esprit.spring.wecare.Repositories.Files;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.wecare.Entities.Files.FileDB;
@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
}