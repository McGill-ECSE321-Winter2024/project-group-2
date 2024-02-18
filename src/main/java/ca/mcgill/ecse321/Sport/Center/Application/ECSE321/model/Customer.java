package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

import jakarta.persistence.Entity;

/**
 * The Customer class represents a role in the sports center for a customer.
 * This class extends the Role class and inherits its properties and methods.
 */
@Entity
public class Customer extends Role
{
  /**
   * Default constructor for Customer. Calls the default constructor of the
   * superclass (Role) to initialize the base properties.
   */
  public Customer()
  {
    super();
  }
  
  /**
   * Parameterized constructor for Customer.
   * 
   * @param aId The unique identifier for the customer role.
   */
  public Customer(int aId)
  {
    super(aId);
  }

  /**
   * Deletes the Customer. Calls the delete method of the superclass (Role)
   * to perform any cleanup or additional actions needed when deleting a customer.
   */
  public void delete()
  {
    super.delete();
  }

}