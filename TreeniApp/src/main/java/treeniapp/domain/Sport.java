
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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isShowDistance() {
        return showDistance;
    }

    public void setShowDistance(boolean showDistance) {
        this.showDistance = showDistance;
    }

    @Override
    public String toString()  {
        return this.name;
    }
    
}
