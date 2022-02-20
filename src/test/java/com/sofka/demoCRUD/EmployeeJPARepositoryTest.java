package com.sofka.demoCRUD;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sofka.demoCRUD.Models.Employee;
import com.sofka.demoCRUD.Models.Project;
import com.sofka.demoCRUD.Models.Role;
import com.sofka.demoCRUD.Repositories.IEmployeeJPARepository;
import com.sofka.demoCRUD.Repositories.IProjectJPARepository;
import com.sofka.demoCRUD.Repositories.IRoleJPARepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeJPARepositoryTest {
    
    @Autowired
    private IEmployeeJPARepository repoEmpl;

    @Autowired
    private IRoleJPARepository repoRole;

    @Autowired
    private IProjectJPARepository  repoProj;

    @Test
    public void saveEmployee(){
        Role admin = new Role("ROLE_ADMIN");
        Role dev = new Role("ROLE_DEV");

        admin = repoRole.save(admin);
        dev = repoRole.save(dev);

        Project project1 = new Project("proj1");
        Project project2 = new Project("proj2");
        Project project3 = new Project("proj3");

        project1 = repoProj.save(project1);
        project2 = repoProj.save(project2);
        project3 = repoProj.save(project3);

        Employee john = new Employee("John", "Smith", "empl123", dev);
        Employee claire = new Employee("Claire", "Redfield", "empl124", admin);

        john.getProjects().add(project1);
        john.getProjects().add(project2);

        claire.getProjects().add(project1);
        claire.getProjects().add(project2);
        claire.getProjects().add(project3);

        repoEmpl.save(john);
        repoEmpl.save(claire);

        repoEmpl.flush();

        Employee empl124 = repoEmpl.findByEmployeeid("empl124");
        assertEquals("Claire", empl124.getFirstName());
        assertEquals(2, repoEmpl.findAll().size());
        assertEquals(admin, empl124.getRole());
    }
}
