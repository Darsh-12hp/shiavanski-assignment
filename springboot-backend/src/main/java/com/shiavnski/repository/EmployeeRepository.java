package com.shiavnski.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shiavnski.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{


    
    Optional<Employee> findByEmailId(String emailId);

}
