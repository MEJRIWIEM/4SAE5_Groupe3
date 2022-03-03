package tn.esprit.spring.wecare.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.spring.wecare.Entities.EmployeeList.EmployeeList;

public interface SaveEmployeeToDb extends JpaRepository<EmployeeList,Long> {

}

