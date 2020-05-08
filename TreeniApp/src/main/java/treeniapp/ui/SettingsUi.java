
package treeniapp.ui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import treeniapp.domain.TreeniAppService;
import treeniapp.domain.User;

/**
 * Class handles the GUI for settings
 */
public class SettingsUi {
    
    private TreeniAppService treeniAppService;
    private UiService uiService;
    private WorkoutsUi workoutsUi;
    
    public SettingsUi(TreeniAppService treeniAppService, UiService uiService, WorkoutsUi workoutsUi) {
        this.treeniAppService = treeniAppService;
        this.uiService = uiService;
        this.workoutsUi = workoutsUi;
    }
    
    /**
    * Method for GUI for settings
     * @param settingsWindow  <code>Stage</code> to which the settings scene is injected.
     * @return  The <code>Scene</code> containing the settings form.
    */
    public Scene settingsScene(Stage settingsWindow) {
        
        User currentUser = treeniAppService.getLoggedInUser();
        
        Label settingsHeader = new Label("Asetukset");
        settingsHeader.setFont(new Font(20.0));
        Label iconNotice = new Label("Kuvakkeet: Material Design (https://material.io/)");
        iconNotice.setFont(new Font(10.0));
        Button closeSettingsButton = new Button("Sulje");
        
        Label changeNameHeader = new Label("Muuta nimesi");
        changeNameHeader.setFont(new Font(16.0));
        Label changeNameInstruction = new Label("Nimi:");
        TextField changeName = new TextField(currentUser.getName());
        Button changeNameButton = new Button("Muuta");
        
        Label modifySportsHeader = new Label("Lisää uusi laji");
        modifySportsHeader.setFont(new Font(16.0));
        Label sportNameInstruction = new Label("Laji:");
        TextField sportName = new TextField();
        CheckBox sportShowDistance = new CheckBox("Näytä matka-kenttä");
        Button addSportButton = new Button("Lisää");
        
        GridPane settingsPane = new GridPane();
        ColumnConstraints settingsCol0 = new ColumnConstraints();
        settingsCol0.setHgrow(Priority.ALWAYS);
        settingsCol0.setHalignment(HPos.CENTER);
        ColumnConstraints settingsCol1 = new ColumnConstraints(80);
        settingsCol1.setHalignment(HPos.RIGHT);
        ColumnConstraints settingsCol4 = new ColumnConstraints();
        settingsCol4.setHgrow(Priority.ALWAYS);
        settingsPane.getColumnConstraints().addAll(settingsCol0, settingsCol1,
                new ColumnConstraints(120), new ColumnConstraints(120),
                settingsCol4);
        settingsPane.add(settingsHeader, 0, 0, 5, 1);
        settingsPane.add(changeNameHeader, 0, 2, 5, 1);
        settingsPane.add(changeNameInstruction, 1, 3);
        settingsPane.add(changeName, 2, 3);
        settingsPane.add(changeNameButton, 3, 3);
        settingsPane.add(modifySportsHeader, 0, 5, 5, 1);
        settingsPane.add(sportNameInstruction, 1, 6);
        settingsPane.add(sportName, 2, 6);
        settingsPane.add(addSportButton, 3, 6);
        settingsPane.add(sportShowDistance, 2, 7, 2, 1);
        settingsPane.add(closeSettingsButton, 0, 9, 5, 1);
        settingsPane.add(iconNotice, 0, 11, 5, 1);
        settingsPane.setAlignment(Pos.CENTER);
        settingsPane.setVgap(10);
        settingsPane.setHgap(10);
        settingsPane.setPadding(new Insets(20, 20, 20, 20));
        
        Scene settingsScene = new Scene(settingsPane, 300, 300);
        
        // Change Name
        changeNameButton.setOnAction((event) -> {
            String name = changeName.getText();
            if (name.length() < 1 || name.length() > 20) {
                uiService.showError("Vihreellinen nimi!", "Uuden nimen on oltava 1-20 merkin pituinen. Tarkista nimi ja yritä uudelleen.");
            } else if (name.equals(currentUser.getName())) {
                uiService.showInfo("Nimeä ei muutettu!", "Uusi ja vanha nimi olivat samat, joten nimeä ei päivitetty.");
            } else {
                treeniAppService.updateUser(new User(currentUser.getUsername(), name));
                uiService.showInfo("Nimi päivitetty!", "Uusi nimi päivitetään treeninäkymään, kun suljet asetukset.");
                closeSettingsButton.setText("Sulje ja päivitä");
            }
            
        });
        
        // Add New Sport
        addSportButton.setOnAction((event) -> {
            String sName = sportName.getText().trim();
            String sIcon = "general";
            boolean sShowDistance = sportShowDistance.isSelected();
            if (sName.length() < 1 || sName.length() > 15) {
                uiService.showError("Vihreellinen lajin nimi!", "Lajin nimen on oltava 1-15 merkin pituinen. Tarkista nimi ja yritä uudelleen.");
            } else if (treeniAppService.newSport(sName, sIcon, sShowDistance)) {
                sportName.setText("");
                sportShowDistance.setSelected(false);
                uiService.showInfo("Uusi laji lisätty!", "Uusi laji '" + sName + "' on nyt käytössäsi."
                        + " Laji päivittyy listaan, kun avaat uuden treenin lisäämisen seuraavan kerran.");
            } else {
                uiService.showError("Lajin lisääminen ei onnistunut!", "Uuden lajin tallentaminen ei valitettavasti onnistunut."
                        + " Samanniminen laji on jo olemassa tai tietokantaan ei saatu yhteyttä.");
            }
        });
        
        // Close Settings Window
        closeSettingsButton.setOnAction((event) -> {
            settingsWindow.close();
        });
        
        return settingsScene;
        
    }
    
}
