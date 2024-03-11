package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Role;

import java.util.List;

public class PersonDTO {
    private int personId;
    private String password;
    private String email;
    private String name;
    private String roles;

    public PersonDTO() {
    }
    public PersonDTO(int personId, String password, String email, String name, List<Role> roles ) {
        this.personId = personId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.roles = roles.toString();
    }

    public PersonDTO(Person model) {
        this.personId = model.getId();
        this.password = model.getPassword();
        this.email = model.getEmail();
        this.name = model.getName();
        this.roles = model.getRoles().toString();
    }

    public int getPersonId() {
        return personId;
    }
    public void setPersonId(int personId) {
        this.personId = personId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getRoles() {
        return roles;
    }

    public void setRole(String roles) {
        this.roles = roles;
    }
    
}
