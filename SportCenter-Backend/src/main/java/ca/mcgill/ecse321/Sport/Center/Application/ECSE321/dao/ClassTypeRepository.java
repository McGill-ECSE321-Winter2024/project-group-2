package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.ClassType;

/**
 * This interface defines the repository for the CRUD functionalities relating
 * to the ClassType.
 */

public interface ClassTypeRepository extends CrudRepository<ClassType, String> {

    /**
     * This method finds a class type based on its name (classType)
     *
     * @param classType class type
     * @return Type we are getting
     */
    public ClassType getClassTypeByClassType(String classType);
}