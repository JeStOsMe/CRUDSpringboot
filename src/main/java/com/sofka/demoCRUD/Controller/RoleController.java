package com.sofka.demoCRUD.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sofka.demoCRUD.Models.Role;
import com.sofka.demoCRUD.Repositories.IRoleJPARepository;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    IRoleJPARepository roleRepo;

    @GetMapping("/getall")
    public ResponseEntity<List<Role>> getAllRoles(@RequestParam(required = false) String title){
        try{
            List<Role> roles = new ArrayList<Role>();
            roles = roleRepo.findAll();

            if (roles.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") Long id){
        Optional<Role> roleData = roleRepo.findById(id);

        if(roleData.isPresent()){
            return new ResponseEntity<>(roleData.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        try{
            Role _role = roleRepo.save(new Role(role.getName()));
            
            return new ResponseEntity<>(_role, HttpStatus.CREATED);
        } catch (Exception ex){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
    
}
