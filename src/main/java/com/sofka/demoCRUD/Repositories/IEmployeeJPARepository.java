package com.sofka.demoCRUD.Repositories;

import com.sofka.demoCRUD.Models.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeJPARepository extends JpaRepository<Employee, Long>{
    //Select * from Employee where employeeid={param}
    Employee findByEmployeeid(String employeeid);
}
