package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

import jakarta.persistence.Entity;

/**
 * The Owner class represents a staff role in the sports center for an owner.
 * This class extends the Staff class and inherits its properties and methods.
 */
@Entity
public class Owner extends Staff
{
  /**
   * Default constructor for Owner. Calls the default constructor of the
   * superclass (Staff) to initialize the base properties.
   */
  public Owner(){
    super();
  }

  /**
   * Parameterized constructor for Owner.
   * 
   * @param aId The unique identifier for the owner role.
   */
  public Owner(int aId)
  {
    super(aId);
  }

  /**
   * Deletes the Owner. Calls the delete method of the superclass (Staff)
   * to perform any cleanup or additional actions needed when deleting an owner.
   */
  public void delete()
  {
    super.delete();
  }

}