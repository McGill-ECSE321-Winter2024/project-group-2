package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

// line 36 "model.ump"
// line 106 "model.ump"
@Entity
public class SessionRegistration
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SessionRegistration Attributes
  @Id
  @GeneratedValue
  private int id;

  //SessionRegistration Associations
  @ManyToOne
  private Session session;
  @ManyToOne
  private Customer customer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SessionRegistration()
  {
  }

  public SessionRegistration(int aId, Session aSession, Customer aCustomer)
  {
    id = aId;
    if (!setSession(aSession))
    {
      throw new RuntimeException("Unable to create SessionRegistration due to aSession. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCustomer(aCustomer))
    {
      throw new RuntimeException("Unable to create SessionRegistration due to aCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public Session getSession()
  {
    return session;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSession(Session aNewSession)
  {
    boolean wasSet = false;
    if (aNewSession != null)
    {
      session = aNewSession;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCustomer(Customer aNewCustomer)
  {
    boolean wasSet = false;
    if (aNewCustomer != null)
    {
      customer = aNewCustomer;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    session = null;
    customer = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "session = "+(getSession()!=null?Integer.toHexString(System.identityHashCode(getSession())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}