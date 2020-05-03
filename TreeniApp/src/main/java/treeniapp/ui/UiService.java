
package treeniapp.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import treeniapp.domain.Sport;
import treeniapp.domain.TreeniAppService;

public class UiService {
    
    private TreeniAppService treeniAppService;
    
    public UiService(TreeniAppService treeniAppService) {
        this.treeniAppService = treeniAppService;
    }
    
    /**
    * Method to create a list of all <code>Sport</code> objects for the add workout view's drop down list.
    * 
    * @return <code>ObservableList</code> consisting of all <code>Sport</code> objects.
    */
    public ObservableList<Sport> generateSportsDropdown() {
        ObservableList<Sport> sportsDropdown = FXCollections.observableArrayList();
        sportsDropdown.add(new Sport(0, "Valitse laji", null, false));
        treeniAppService.getSports().forEach((s) -> {
            sportsDropdown.add(s);
        });
        return sportsDropdown;
    }
    
    /**
    * Method to create a list of consecutive numbers to be used in the add workout view's drop down lists.
    * 
    * @param    start   The first number of the list.
    * @param    last    The last number of the list.
    * 
    * @return <code>ObservableList</code> consisting of numbers from <code>start</code> to <code>last</code>.
    */
    public ObservableList generateNumberList(int start, int last) {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (int i = start; i <= last; i++) {
            String item;
            if (i < 10) {
                item = "0" + i;
            } else {
                item = String.valueOf(i);
            }
            list.add(item);
        }
        return list;
    }
    
    /**
    * Method to create a list of all <code>Sport</code> objects for the add workout view.
    * 
    * @param    sport   The <code>Sport</code> object for which an icon is requested.
    * 
    * @return The named <code>Sport</code>'s icon as an <code>Image</code>.
    */
    public Image getSportsIcon(Sport sport) {
        String iconFileName = "/sporticons/" + sport.getIcon() + ".png";
        Image icon = new Image(getClass().getResourceAsStream(iconFileName));
        return icon;
    }
    
    /**
    * Method to show error message.
    * 
    * @param    message     The error message to show.
    */
    public void showError(String message) {
        Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Virhe");
                error.setHeaderText(message);
                error.showAndWait();
    }
    
    /**
    * Method to show error message.
    * 
    * @param    header      The error message to show.
    * @param    message     The additional help text to show.
    */
    public void showError(String header, String message) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Virhe");
        error.setHeaderText(header);
        error.setContentText(message);
        error.showAndWait();
    }
    
    /**
    * Method to ask confirmation from the user.
    * 
    * @param    message     The confirmation message to show.
    * 
    * @return   <code>true</code> id "Yes" is clicked; <code>false</code> otherwise.
    */
    public boolean showConfirmation(String message) {
        ButtonType yesButton = new ButtonType("Kyllä");
        ButtonType noButton = new ButtonType("Ei");
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Valitse 'Kyllä' tai 'Ei'.", yesButton, noButton);
        confirmation.setTitle("Vahvista");
        confirmation.setHeaderText(message);
        confirmation.showAndWait();
        if (confirmation.getResult() == yesButton) {
            return true;
        }
        return false;
    }
    
    /**
    * Method to show information message.
    * 
    * @param    message     The information message to show.
    */
    public void showInfo(String message) {
        Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Huomautus");
                error.setHeaderText(message);
                error.showAndWait();
    }
    
    /**
    * Method to show information message.
    * 
    * @param    header      The information message to show.
    * @param    message     The additional help text to show.
    */
    public void showInfo(String header, String message) {
        Alert error = new Alert(Alert.AlertType.INFORMATION);
        error.setTitle("Huomautus");
        error.setHeaderText(header);
        error.setContentText(message);
        error.showAndWait();
    }
    
}
