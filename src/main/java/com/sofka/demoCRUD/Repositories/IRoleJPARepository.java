package com.sofka.demoCRUD.Repositories;

import com.sofka.demoCRUD.Models.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleJPARepository extends JpaRepository<Role, Long> {
    
}
