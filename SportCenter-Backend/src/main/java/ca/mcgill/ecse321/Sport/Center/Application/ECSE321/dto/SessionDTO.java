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
    public SessionDTO(int id, int length, Time startTime, Time endTime, Date date, boolean isRepeating, int maxParticipants, ClassType classType){
        this.id = id;
        this.length = length;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.isRepeating = isRepeating;
        this.maxParticipants = maxParticipants;
        this.classType = classType;
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
        
    }

    public int getId(){ //maybe remove this //no keep it plz
        return id;
    }
    public int getLength(){
        return length;
    }
    public void setLength(int length){
        this.length = length;
    }
    public Time getStartTime(){
        return startTime;
    }
    public void setStartTime(Time startTime){
        this.startTime = startTime;
    }
    public Time getEndTime(){
        return endTime;
    }
    public void setEndTime(Time endTime){
        this.endTime = endTime;
    }
    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public boolean getIsRepeating(){
        return isRepeating;
    }
    public void setIsRepeating(boolean isRepeating){
        this.isRepeating = isRepeating;
    }
    public int getMaxParticipants(){
        return maxParticipants;
    }
    public void setMaxParticipants(int maxParticipants){
        this.maxParticipants = maxParticipants;
    }

    public ClassType getClassType() { return classType; }
    public void setClassType(ClassType classType) { this.classType = classType; }
    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

}
