
package treeniapp.domain;

/**
 * Class handles the Sport objects available for Workout objects.
 */
public class Sport {
    
    private int id;
    private String name;
    private String icon;
    private boolean showDistance;
    
    /**
    * Constructor for <code>Sport</code> object.
    * 
    * @param id             Individual ID number (as <code>Integer</code>).
    * @param name           Name of the sport (as <code>String</code>).
    * @param icon           Filename of the sport's icon (as <code>String</code>, icon stored as PNG image in "sporticons" folder).
    * @param showDistance   <code>true</code> if distance is applicable for the sport; <code>false</code> otherwise.
    */
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

    @Override
    public String toString() {
        return name;
    }
    
}
