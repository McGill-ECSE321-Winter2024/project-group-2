package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Integer>{
    public Owner getOwnerById(int id);
}
