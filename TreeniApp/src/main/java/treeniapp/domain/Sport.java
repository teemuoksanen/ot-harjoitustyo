
package treeniapp.domain;

/**
 * Class handles the Sport objects available for Workout objects
 */
public class Sport {
    
    private int id;
    private String name;
    private String icon;
    private boolean showDistance;
    
    public Sport(int id, String name, String icon, boolean showDistance) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.showDistance = showDistance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isShowDistance() {
        return showDistance;
    }
    
}
