package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer>{
    //finding by unique identifiers
    Role findById(int id);
    Role findByEmail(String email);

}
