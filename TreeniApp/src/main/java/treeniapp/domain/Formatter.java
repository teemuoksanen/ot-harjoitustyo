
package treeniapp.domain;

/**
 * Class offers formatting services for other classes.
 */
public class Formatter {
    
    /**
    * Method to format duration (in minutes) into hours and minutes.
    *
    * @param   duration   number of minutes
    *
    * @return <code>String</code> in format "hh:mm".
    */
    public String minutesIntoHoursAndMinutes(int duration) {
        int hours = duration / 60;
        int minutes = duration % 60;
        String divider = ":";
        if (minutes < 10) {
            divider = ":0";
        }
        return hours + divider + minutes;
    }
    
    /**
    * Method to format distance (in metres) into kilometres and metres.
    *
    * @param   distance   number of meters
    *
    * @return <code>String</code> in format "# km ### m"; if under 1 km, returns "### m"; if metre value is 0, returns "# km".
    */
    public String metersIntoKmsAndMeters(int distance) {
        int kms = distance / 1000;
        int metres = distance % 1000;
        
        if (distance < 1000) {
            return metres + " m";
        }
        
        if (metres == 0) {
            return kms + " km";
        }
        
        return kms + " km  " + metres + " m";
    }
    
}
