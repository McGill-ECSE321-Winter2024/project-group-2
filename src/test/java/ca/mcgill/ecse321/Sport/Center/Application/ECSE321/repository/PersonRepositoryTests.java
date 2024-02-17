package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersonRepositoryTests {
    
    @Autowired
    private PersonRepository repo;

    public PersonRepositoryTests(PersonRepository repo) {
        this.repo = repo;
    }

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadPerson() {

    }

}
