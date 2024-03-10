package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto;

import java.util.List;

public class ClassTypeDTO {
    private String name;
    private List<SessionDTO> sessions;


    public ClassTypeDTO() {
    }
    public ClassTypeDTO(String name, List<SessionDTO> sessions) {
        this.name = name;
        this.sessions = sessions;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<SessionDTO> getSessions() {
        return sessions;
    }
    public void setSessions(List<SessionDTO> sessions) {
        this.sessions = sessions;
    }
}
