package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/



// line 53 "model.ump"
// line 102 "model.ump"
public class Owner extends Staff
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
  private User user;
  private SportCenter sportCenter;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(int aEmployeeId, User aUser, SportCenter aSportCenter)
  {
    super(aEmployeeId);
    if (!setUser(aUser))
    {
      throw new RuntimeException("Unable to create Owner due to aUser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddSportCenter = setSportCenter(aSportCenter);
    if (!didAddSportCenter)
    {
      throw new RuntimeException("Unable to create owner due to sportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
  }
  /* Code from template association_GetOne */
  public SportCenter getSportCenter()
  {
    return sportCenter;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setUser(User aNewUser)
  {
    boolean wasSet = false;
    if (aNewUser != null)
    {
      user = aNewUser;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setSportCenter(SportCenter aNewSportCenter)
  {
    boolean wasSet = false;
    if (aNewSportCenter == null)
    {
      //Unable to setSportCenter to null, as owner must always be associated to a sportCenter
      return wasSet;
    }
    
    Owner existingOwner = aNewSportCenter.getOwner();
    if (existingOwner != null && !equals(existingOwner))
    {
      //Unable to setSportCenter, the current sportCenter already has a owner, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    SportCenter anOldSportCenter = sportCenter;
    sportCenter = aNewSportCenter;
    sportCenter.setOwner(this);

    if (anOldSportCenter != null)
    {
      anOldSportCenter.setOwner(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    user = null;
    SportCenter existingSportCenter = sportCenter;
    sportCenter = null;
    if (existingSportCenter != null)
    {
      existingSportCenter.setOwner(null);
    }
    super.delete();
  }

}