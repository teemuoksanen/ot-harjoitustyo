
package treeniapp.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Workout {
    
    private int id;
    private Timestamp datetime;
    private User user;
    private Sport sport;
    private int duration;
    private int distance;
    private int mhr;
    private String notes;
    
    public Workout(int id, Timestamp datetime, User user, Sport sport, int duration, int distance, int mhr, String notes) {
        this.id = id;
        this.datetime = datetime;
        this.user = user;
        this.sport = sport;
        this.duration = duration;
        this.distance = distance;
        this.mhr = mhr;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }
    
    public String getDay() {
        String date = new SimpleDateFormat("d").format(this.datetime);
        return date;
    }
    
    public String getMonth() {
        String date = new SimpleDateFormat("M").format(this.datetime);
        return date;
    }
    
    public String getDayMonth() {
        String date = new SimpleDateFormat("d.M.").format(this.datetime);
        return date;
    }
    
    public String getDayMonthYear() {
        String date = new SimpleDateFormat("d.M.yyyy").format(this.datetime);
        return date;
    }
    
    public String getHour() {
        String date = new SimpleDateFormat("H").format(this.datetime);
        return date;
    }
    
    public String getMinute() {
        String date = new SimpleDateFormat("mm").format(this.datetime);
        return date;
    }
    
    public String getTime() {
        String date = new SimpleDateFormat("H.mm").format(this.datetime);
        return date;
    }
    
    public String getDateMonthYear() {
        String date = new SimpleDateFormat("d.M.yyyy").format(this.datetime);
        return date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDurationFormat() {
        int hours = duration / 60;
        int minutes = duration % 60;
        String divider = ":";
        if (minutes < 10) {
            divider = ":0";
        }
        return hours + divider + minutes;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getMhr() {
        return mhr;
    }

    public void setMhr(int mhr) {
        this.mhr = mhr;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    
    
}
