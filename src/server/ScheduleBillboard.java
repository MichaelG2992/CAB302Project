package server;

import java.io.Serializable;

public class ScheduleBillboard implements Serializable {

    private String name;
    private String startTime;
    private int duration;
    private String dayOfWeek;
    private String username;


    public ScheduleBillboard(){

    }


    public ScheduleBillboard(String name, String startTime, int duration, String dayOfWeek, String username) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.dayOfWeek = dayOfWeek;
        this.username = username;
    }




    //Getters
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    //Setters


    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Billboard Name: " + this.name +  "           Schedule Creator:" + this.username+ "    Day of Schedule:"+this.dayOfWeek+ "        Start Time:" +this.startTime
                + "        Duration:" + this.duration+" minutes";
    }
}
