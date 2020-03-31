
package treeniapp.domain;

public class Workout {
    
    private int id;
    private String date;
    private String time;
    private String type;
    private int duration;
    private User user;
    
    public Workout(int id, String date, String time, String type, int duration, User user) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.type = type;
        this.duration = duration;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
