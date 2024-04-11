package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto;

import java.util.List;

public class InstructorDTO {
    private List<SessionDTO> sessions;
    private int instructorId;
    private int personId;

    public InstructorDTO() {
    }
  
    public InstructorDTO(int instructorId, List<SessionDTO> sessionDTOs, int associatedPersonId) {
        this.instructorId = instructorId;
        this.personId = associatedPersonId;
        this.sessions = sessionDTOs;
    }

    public int getPersonId() {
        return personId;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public List<SessionDTO> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionDTO> sessions) {
        this.sessions = sessions;
    }
}