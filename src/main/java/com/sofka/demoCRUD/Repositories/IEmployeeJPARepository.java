package com.sofka.demoCRUD.Repositories;

import java.util.List;
import java.util.Optional;

import com.sofka.demoCRUD.Models.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeJPARepository extends JpaRepository<Employee, Long>{
    //Select * from Employee where employeeid={param}
    Optional<Employee> findByEmployeeid(String employeeid);
    List<Employee> findByLastName(String lastName);
    List<Employee> findByRole(Long id);
    void deleteEmployeeByEmployeeid(String id);
}
