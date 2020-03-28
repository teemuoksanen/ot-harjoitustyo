
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
    
}
