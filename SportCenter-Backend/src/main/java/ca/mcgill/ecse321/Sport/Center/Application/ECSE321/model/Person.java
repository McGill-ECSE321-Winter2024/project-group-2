package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


// line 14 "model.ump"
// line 116 "model.ump"
public class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  @Id
  @GeneratedValue
  private int id;
  private String password;
  private String email;
  private String name;

  //Person Associations
  @OneToMany
  private List<Role> roles;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(int aId, String aPassword, String aEmail, String aName, Role... allRoles)
  {
    id = aId;
    password = aPassword;
    email = aEmail;
    name = aName;
    roles = new ArrayList<Role>();
    boolean didAddRoles = setRoles(allRoles);
    if (!didAddRoles)
    {
      throw new RuntimeException("Unable to create Person, must have 1 to 3 roles. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public String getPassword()
  {
    return password;
  }

  public String getEmail()
  {
    return email;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetMany */
  public Role getRole(int index)
  {
    Role aRole = roles.get(index);
    return aRole;
  }

  public List<Role> getRoles()
  {
    List<Role> newRoles = Collections.unmodifiableList(roles);
    return newRoles;
  }

  public int numberOfRoles()
  {
    int number = roles.size();
    return number;
  }

  public boolean hasRoles()
  {
    boolean has = roles.size() > 0;
    return has;
  }

  public int indexOfRole(Role aRole)
  {
    int index = roles.indexOf(aRole);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRoles()
  {
    return 1;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfRoles()
  {
    return 3;
  }
  /* Code from template association_AddUnidirectionalMN */
  public boolean addRole(Role aRole)
  {
    boolean wasAdded = false;
    if (roles.contains(aRole)) { return false; }
    if (numberOfRoles() < maximumNumberOfRoles())
    {
      roles.add(aRole);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeRole(Role aRole)
  {
    boolean wasRemoved = false;
    if (!roles.contains(aRole))
    {
      return wasRemoved;
    }

    if (numberOfRoles() <= minimumNumberOfRoles())
    {
      return wasRemoved;
    }

    roles.remove(aRole);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalMN */
  public boolean setRoles(Role... newRoles)
  {
    boolean wasSet = false;
    ArrayList<Role> verifiedRoles = new ArrayList<Role>();
    for (Role aRole : newRoles)
    {
      if (verifiedRoles.contains(aRole))
      {
        continue;
      }
      verifiedRoles.add(aRole);
    }

    if (verifiedRoles.size() != newRoles.length || verifiedRoles.size() < minimumNumberOfRoles() || verifiedRoles.size() > maximumNumberOfRoles())
    {
      return wasSet;
    }

    roles.clear();
    roles.addAll(verifiedRoles);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRoleAt(Role aRole, int index)
  {  
    boolean wasAdded = false;
    if(addRole(aRole))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRoles()) { index = numberOfRoles() - 1; }
      roles.remove(aRole);
      roles.add(index, aRole);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRoleAt(Role aRole, int index)
  {
    boolean wasAdded = false;
    if(roles.contains(aRole))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRoles()) { index = numberOfRoles() - 1; }
      roles.remove(aRole);
      roles.add(index, aRole);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRoleAt(aRole, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    roles.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "password" + ":" + getPassword()+ "," +
            "email" + ":" + getEmail()+ "," +
            "name" + ":" + getName()+ "]";
  }
}