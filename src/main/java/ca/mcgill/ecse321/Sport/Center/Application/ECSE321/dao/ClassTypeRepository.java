package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.ClassType;

public interface ClassTypeRepository extends CrudRepository<ClassType, String>{
    public ClassType getClassTypeByClassType(String classType);
}
