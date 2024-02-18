package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.ClassTypeRepository;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ClassTypeRepositoryTests {
    
    @Autowired
    private ClassTypeRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadClassType() {
        String classType = "yoga";
        ClassType yoga = new ClassType(classType);

        yoga = repo.save(yoga);

        ClassType yogaFromRepo = repo.getClassTypeByClassType(yoga.getClassType());

        assertNotNull(yogaFromRepo);
        assertEquals(classType, yogaFromRepo.getClassType());
    }

}
