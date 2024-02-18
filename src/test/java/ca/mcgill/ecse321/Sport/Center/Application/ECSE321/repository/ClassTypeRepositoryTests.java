package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.ClassTypeRepository;
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
        String classType = "exampleClassType";
        ClassType exampleClassType = new ClassType(classType);

        exampleClassType = repo.save(exampleClassType);

        ClassType exampleClassTypeFromRepo = repo.getClassTypeByClassType(exampleClassType.getClassType());

        assertNotNull(exampleClassTypeFromRepo);
        assertEquals(classType, exampleClassTypeFromRepo.getClassType());
    }

}
