package com.sofka.demoCRUD.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sofka.demoCRUD.Models.Project;
import com.sofka.demoCRUD.Repositories.IProjectJPARepository;

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
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    IProjectJPARepository projRepo;
    
    @GetMapping("/getall")
    public ResponseEntity<List<Project>> getAllProjects(@RequestParam(required = false) String title){
        try{
            List<Project> projects = new ArrayList<Project>();
            projects = projRepo.findAll();

            if(projects.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } 

            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") Long id){
        Optional<Project> projectData = projRepo.findById(id);

        if (projectData.isPresent()){
            return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody Project project){
        try{
            Project _project = projRepo.save(new Project(project.getName()));

            return new ResponseEntity<>(_project, HttpStatus.CREATED);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProjectById(@PathVariable("id") Long id){
        try{
            projRepo.deleteById(id);

            return new ResponseEntity<>("PROJECT DELETED!!!", HttpStatus.NO_CONTENT);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAllProjects(){
        try{
            projRepo.deleteAll();

            return new ResponseEntity<>("ALL PROJECTS DELETED!!!", HttpStatus.NO_CONTENT);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Project> updateProjectById(@PathVariable("id") Long id, @RequestBody Project project){
        try{
            Optional<Project> projectData = projRepo.findById(id);

            if(projectData.isPresent()){
                Project _project = projectData.get();
                _project.setName(project.getName());

                return new ResponseEntity<>(projRepo.save(_project), HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
