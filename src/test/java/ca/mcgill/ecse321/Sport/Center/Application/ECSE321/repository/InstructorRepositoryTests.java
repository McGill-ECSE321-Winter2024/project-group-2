package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;

@SpringBootTest
public class InstructorRepositoryTests {
        
    @Autowired
    private InstructorRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

}
