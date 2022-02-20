package com.sofka.demoCRUD.Repositories;

import com.sofka.demoCRUD.Models.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectJPARepository extends JpaRepository<Project, Long>{
    
}
