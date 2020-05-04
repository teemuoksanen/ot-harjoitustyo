
package treeniapp.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Class handles the Workout objects representing the workouts the users have added in the application.
 */
public class Workout {
    
    private int id;
    private Timestamp datetime;
    private User user;
    private Sport sport;
    private int duration;
    private int distance;
    private int mhr;
    private String notes;
    private Formatter format = new Formatter();
    
    /**
    * Constructor for <code>Workout</code> object.
    * 
    * @param id         Individual ID number (as <code>Integer</code>).
    * @param datetime   <code>Timestamp</code> for the start date and time.
    * @param user       Which <code>User</code>'s workout it is.
    * @param sport      Which type of <code>Sport</code> the workout was.
    * @param duration   Duration of the workout in minutes (as <code>Integer</code>).
    * @param distance   Distance of the workout in meters (as <code>Integer</code>).
    * @param mhr        Median heart rate of the workout in bpm (beats per minute, as <code>Integer</code>).
    * @param notes      General notes (as <code>String</code>).
    */
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

    public User getUser() {
        return user;
    }

    public Sport getSport() {
        return sport;
    }

    public Timestamp getDatetime() {
        return datetime;
    }
    
    /**
    * Method to get the day of the month from the <code>Workout</code>'s Datetime field.
    *
    * @return Day of the month as <code>String</code>.
    */
    public String getDay() {
        String date = new SimpleDateFormat("d").format(this.datetime);
        return date;
    }
    
    /**
    * Method to get the number of the month from the <code>Workout</code>'s Datetime field.
    *
    * @return Number of the month as <code>String</code>.
    */
    public String getMonth() {
        String date = new SimpleDateFormat("M").format(this.datetime);
        return date;
    }
    
    /**
    * Method to get the day and the month from the <code>Workout</code>'s Datetime field.
    *
    * @return Day and month as <code>String</code> in the format "dd.mm.".
    */
    public String getDayMonth() {
        String date = new SimpleDateFormat("d.M.").format(this.datetime);
        return date;
    }
    
    /**
    * Method to get the day, the month and the year from the <code>Workout</code>'s Datetime field.
    *
    * @return Day, month and year as <code>String</code> in the format "dd.mm.yyyy".
    */
    public String getDayMonthYear() {
        String date = new SimpleDateFormat("d.M.yyyy").format(this.datetime);
        return date;
    }
    
    /**
    * Method to get the year from the <code>Workout</code>'s Datetime field.
    *
    * @return Year as <code>String</code> in the format "yyyy".
    */
    public String getYear() {
        String date = new SimpleDateFormat("yyyy").format(this.datetime);
        return date;
    }
    
    /**
    * Method to get the hour number from the <code>Workout</code>'s Datetime field.
    *
    * @return Hour number as <code>String</code>.
    */
    public String getHour() {
        String date = new SimpleDateFormat("H").format(this.datetime);
        return date;
    }
    
    /**
    * Method to get the minute number from the <code>Workout</code>'s Datetime field.
    *
    * @return Minute number as <code>String</code>.
    */
    public String getMinute() {
        String date = new SimpleDateFormat("mm").format(this.datetime);
        return date;
    }
    
    /**
    * Method to get the time from the <code>Workout</code>'s Datetime field.
    *
    * @return Time as <code>String</code> in the format "hh.mm.".
    */
    public String getTime() {
        String date = new SimpleDateFormat("H.mm").format(this.datetime);
        return date;
    }

    public int getDuration() {
        return duration;
    }

    /**
    * Method to get the formatted duration of <code>Workout</code>.
    *
    * @return Duration of the <code>Workout</code> as <code>String</code> in the format "hh:mm".
    */
    public String getDurationFormatted() {
        return format.minutesIntoHoursAndMinutes(this.duration);
    }

    public int getDistance() {
        return distance;
    }

    /**
    * Method to get the formatted distance of <code>Workout</code>.
    *
    * @return Distance of the <code>Workout</code> as <code>String</code> in the format  "# km ### m"; if under 1 km, returns "### m"; if metre value is 0, returns "# km".
    */
    public String getDistanceFormatted() {
        return format.metersIntoKmsAndMeters(this.distance);
    }

    public int getMhr() {
        return mhr;
    }

    public String getNotes() {
        return notes;
    }
    
}
