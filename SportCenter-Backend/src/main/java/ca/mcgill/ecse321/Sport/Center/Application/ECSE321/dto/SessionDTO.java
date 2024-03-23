package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.ClassType;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;

import java.sql.Date;
import java.sql.Time;

public class SessionDTO {
    private int id;
    private int length;
    private Time startTime;
    private Time endTime;
    private Date date;
    private boolean isRepeating;
    private int maxParticipants;
    private ClassType classType;

    private Instructor instructor;

    public SessionDTO(){
    }
    public SessionDTO(int id, int length, Time startTime, Time endTime, Date date, boolean isRepeating, int maxParticipants, ClassType classType, Instructor instructor){
        this.id = id;
        this.length = length;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.isRepeating = isRepeating;
        this.maxParticipants = maxParticipants;
        this.classType = classType;
        this.instructor = instructor;
    }

    public SessionDTO(Session session) {
        this.id = session.getId();
        this.length = session.getLength();
        this.startTime = session.getStartTime();
        this.endTime = session.getEndTime();
        this.date = session.getDate();
        this.isRepeating = session.getIsRepeating();
        this.maxParticipants = session.getMaxParticipants();
        this.classType = session.getClassType();
        this.instructor = session.getInstructor();
    }

    public int getId(){ //maybe remove this
        return id;
    }
    public int getLength(){
        return length;
    }
    public Time getStartTime(){
        return startTime;
    }
    public Time getEndTime(){
        return endTime;
    }
    public Date getDate(){
        return date;
    }
    public boolean getIsRepeating(){
        return isRepeating;
    }
    public int getMaxParticipants(){
        return maxParticipants;
    }

    public ClassType getClassType() { return classType; }

    public Instructor getInstructor() { return instructor; }

}
