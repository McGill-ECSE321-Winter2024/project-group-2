package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto;

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

    public SessionDTO(){
    }
    public SessionDTO(int id, int length, Time startTime, Time endTime, Date date, boolean isRepeating, int maxParticipants){
        this.id = id;
        this.length = length;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.isRepeating = isRepeating;
        this.maxParticipants = maxParticipants;
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

}
