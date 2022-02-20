package com.sofka.demoCRUD;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sofka.demoCRUD.Models.Employee;
import com.sofka.demoCRUD.Repositories.IEmployeeJPARepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeJPARepositoryTest {
    
    @Autowired
    private IEmployeeJPARepository repo;

    @Test
    public void saveEmployee(){
        Employee john = new Employee("John", "Smith", "empl123");
        Employee claire = new Employee("Claire", "Redfield", "empl124");

        repo.save(john);
        repo.save(claire);

        repo.flush();

        assertEquals(2, repo.findAll().size());
    }
}
