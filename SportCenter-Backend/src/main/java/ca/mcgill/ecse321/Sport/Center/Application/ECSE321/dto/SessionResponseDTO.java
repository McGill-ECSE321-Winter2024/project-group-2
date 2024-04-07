package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

public class SessionResponseDTO {
    private int id;
    private int length;
    private Time startTime;
    private Time endTime;
    private Date date;
    private boolean isRepeating;
    private int maxParticipants;
    private ClassType classType;
    private int instructorId;

    public SessionResponseDTO(Session session) {
        this.id = session.getId();
        this.length = session.getLength();
        this.startTime = session.getStartTime();
        this.endTime = session.getEndTime();
        this.date = session.getDate();
        this.isRepeating = session.getIsRepeating();
        this.maxParticipants = session.getMaxParticipants();
        this.classType = session.getClassType();
        this.instructorId = session.getInstructor().getId();
    }

    public static SessionResponseDTO create(Session session) {
        return new SessionResponseDTO(session);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getIsRepeating() {
        return isRepeating;
    }

    public void setIsRepeating(boolean isRepeating) {
        this.isRepeating = isRepeating;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public ClassType getClassType() { return classType; }
    public void setClassType(ClassType classType) { this.classType = classType; }

}
