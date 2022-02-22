package com.sofka.demoCRUD.Controller;

import java.util.List;
import java.util.Optional;

import com.sofka.demoCRUD.Models.Employee;
import com.sofka.demoCRUD.Repositories.IEmployeeJPARepository;
import com.sofka.demoCRUD.Repositories.IRoleJPARepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    
    @Autowired
    IEmployeeJPARepository empRepo;

    @Autowired
    IRoleJPARepository roleRepo;

    @GetMapping("/getall")
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) String title){
        try{
            List<Employee> employees = empRepo.findAll();

            if(employees.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{employeeid}")
    public ResponseEntity<Employee> getEmployeeByEmployeeid(@PathVariable("employeeid") String employeeid){
        Optional<Employee> employeeData = empRepo.findByEmployeeid(employeeid);

        if(employeeData.isPresent()){
            return new ResponseEntity<>(employeeData.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        try {

            if (roleRepo.findById(employee.getRole().getId()).isPresent()) {
                Employee _employee = empRepo.save(new Employee(employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmployeeid(),
                        roleRepo.getById(employee.getRole().getId())));
                return new ResponseEntity<>(_employee, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/delete/{employeeid}")
    public ResponseEntity<String> deleteEmployeeByEmployeeid(@PathVariable("employeeid") String id){
        try{
            empRepo.deleteEmployeeByEmployeeid(id);

            return new ResponseEntity<>("EMPLOYEE DELETED!!!", HttpStatus.NO_CONTENT);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAllEmployees(){
        try{
            empRepo.deleteAll();

            return new ResponseEntity<>("ALL EMPLOYEES DELETED!!!", HttpStatus.NO_CONTENT);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/update/{employeeid}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable("employeeid") String id, @RequestBody Employee employee){
        try{
            Optional<Employee> employeeData = empRepo.findByEmployeeid(id);

            if(employeeData.isPresent()){
                Employee _employee = employeeData.get();
                _employee.setFirstName(employee.getFirstName());
                _employee.setLastName(employee.getLastName());
                _employee.setEmployeeid(employee.getEmployeeid());
                _employee.setRole(roleRepo.getById(employee.getRole().getId()));

                return new ResponseEntity<>(empRepo.save(_employee), HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
