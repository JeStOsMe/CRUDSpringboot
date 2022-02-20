package com.sofka.demoCRUD.Repositories;

import java.util.List;

import com.sofka.demoCRUD.Models.Employee;
import com.sofka.demoCRUD.Models.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeJPARepository extends JpaRepository<Employee, Long>{
    //Select * from Employee where employeeid={param}
    Employee findByEmployeeid(String employeeid);
    List<Employee> findByLastName(String lastName);
    List<Employee> findByRole(Role role);
}
