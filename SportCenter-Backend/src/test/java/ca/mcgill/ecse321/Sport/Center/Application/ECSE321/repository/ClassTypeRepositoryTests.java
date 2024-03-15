package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.ClassTypeRepository;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    public void testCreateAndReadClassTypeSuccessful() {
        String classType = "exampleClassType";
        ClassType exampleClassType = new ClassType(classType, false);
        exampleClassType = repo.save(exampleClassType);

        ClassType exampleClassTypeFromRepo = repo.findByClassType(classType);

        assertNotNull(exampleClassTypeFromRepo);
        assertEquals(classType, exampleClassTypeFromRepo.getClassType());
    }

    @Test
    public void testCreateAndReadClassTypeUnsuccessful() {
        String classType = "exampleClassType";
        ClassType exampleClassType = new ClassType(classType, false);
        exampleClassType = repo.save(exampleClassType);

        ClassType exampleClassTypeFromRepo = repo.findByClassType("nonExistentClassType");

        assertNull(exampleClassTypeFromRepo);
    }

    @Test
    public void testFindIsApprovedFalseWithFalseAndTrue(){
        String classType1 = "exampleClassType1";
        ClassType exampleClassType1 = new ClassType(classType1, false);
        exampleClassType1 = repo.save(exampleClassType1);

        String classType2 = "exampleClassType2";
        ClassType exampleClassType2 = new ClassType(classType2, false);
        exampleClassType2 = repo.save(exampleClassType2);

        String classType3 = "exampleClassType3";
        ClassType exampleClassType3 = new ClassType(classType3, true);
        exampleClassType3 = repo.save(exampleClassType3);

        String classType4 = "exampleClassType4";
        ClassType exampleClassType4 = new ClassType(classType4, true);
        exampleClassType4 = repo.save(exampleClassType4);

        List<ClassType> classTypes = repo.findByIsApproved(false);

        assertEquals(2, classTypes.size());
        assertNotNull(classTypes.get(0));
        assertEquals(classType1, classTypes.get(0).getClassType());
        assertNotNull(classTypes.get(1));
        assertEquals(classType2, classTypes.get(1).getClassType());
    }

    @Test
    public void testFindIsApprovedTrueWithFalseAndTrue(){
        String classType1 = "exampleClassType1";
        ClassType exampleClassType1 = new ClassType(classType1, false);
        exampleClassType1 = repo.save(exampleClassType1);

        String classType2 = "exampleClassType2";
        ClassType exampleClassType2 = new ClassType(classType2, false);
        exampleClassType2 = repo.save(exampleClassType2);

        String classType3 = "exampleClassType3";
        ClassType exampleClassType3 = new ClassType(classType3, true);
        exampleClassType3 = repo.save(exampleClassType3);

        String classType4 = "exampleClassType4";
        ClassType exampleClassType4 = new ClassType(classType4, true);
        exampleClassType4 = repo.save(exampleClassType4);

        List<ClassType> classTypes = repo.findByIsApproved(true);

        assertEquals(2, classTypes.size());
        assertNotNull(classTypes.get(0));
        assertEquals(classType3, classTypes.get(0).getClassType());
        assertNotNull(classTypes.get(1));
        assertEquals(classType4, classTypes.get(1).getClassType());
    }

    @Test
    public void testFindIsApprovedTrueWithOnlyFalse(){
        String classType1 = "exampleClassType1";
        ClassType exampleClassType1 = new ClassType(classType1, false);
        exampleClassType1 = repo.save(exampleClassType1);

        String classType2 = "exampleClassType2";
        ClassType exampleClassType2 = new ClassType(classType2, false);
        exampleClassType2 = repo.save(exampleClassType2);

        List<ClassType> classTypes = repo.findByIsApproved(true);

        assertEquals(0, classTypes.size());
    }

    @Test
    public void testFindIsApprovedFalseWithOnlyTrue(){
        String classType1 = "exampleClassType1";
        ClassType exampleClassType1 = new ClassType(classType1, true);
        exampleClassType1 = repo.save(exampleClassType1);

        String classType2 = "exampleClassType2";
        ClassType exampleClassType2 = new ClassType(classType2, true);
        exampleClassType2 = repo.save(exampleClassType2);

        List<ClassType> classTypes = repo.findByIsApproved(false);

        assertEquals(0, classTypes.size());
    }
}
