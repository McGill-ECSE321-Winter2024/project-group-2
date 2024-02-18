package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

import jakarta.persistence.Entity;

@Entity
public abstract class Staff extends Role
{

  public Staff()
  {
    super();
  }
  public Staff(int aId)
  {
    super(aId);
  }

  public void delete()
  {
    super.delete();
  }

}