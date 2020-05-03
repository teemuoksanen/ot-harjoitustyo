
package treeniapp.ui;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.UnaryOperator;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import treeniapp.domain.Sport;
import treeniapp.domain.TreeniAppService;
import treeniapp.domain.Workout;

public class AddWorkoutUi {
    
    private TreeniAppService treeniAppService;
    private UiService uiService;
    private WorkoutsUi workoutsUi;
    
    private ObservableList hours;
    private ObservableList mins;
    
    public AddWorkoutUi(TreeniAppService treeniAppService, UiService uiService, WorkoutsUi workoutsUi) {
        this.treeniAppService = treeniAppService;
        this.uiService = uiService;
        this.workoutsUi = workoutsUi;
        
        // Format basic variables
        hours = uiService.generateNumberList(0, 23);
        mins = uiService.generateNumberList(0, 59);
    }
    
    public Scene addWorkoutScene(Stage addWorkoutWindow) {
        
        Label addWorkoutLabel = new Label("Lisää uusi treeni");
        Button createWorkoutButton = new Button("Lisää");
        Button cancelWorkoutButton = new Button("Tyhjennä");
        Button closeWorkoutWindowButton = new Button("Sulje");
        
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]*")) { 
                return change;
            }
            return null;
        };
        
        Label workoutSportInstruction = new Label("Laji:");
        ComboBox<Sport> newWorkoutSport = new ComboBox<>(uiService.generateSportsDropdown());
        newWorkoutSport.getSelectionModel().select(0);
        newWorkoutSport.setTooltip(new Tooltip("Valitse urheilulaji"));
        ImageView newWorkoutSportIconView = new ImageView();
        newWorkoutSportIconView.setFitHeight(36);
        newWorkoutSportIconView.setFitWidth(36);
        Label workoutDayInstruction = new Label("Päivä:");
        DatePicker newWorkoutDay = new DatePicker(LocalDate.now());
        newWorkoutDay.setEditable(false);
        newWorkoutDay.setDayCellFactory(d -> new DateCell() {
               @Override public void updateItem(LocalDate item, boolean empty) {
                   super.updateItem(item, empty);
                   setDisable(item.isAfter(LocalDate.now()));
               }});
        Label workoutTimeInstruction = new Label("Kello:");
        ComboBox<String> newWorkoutTimeHour = new ComboBox<>(hours);
        newWorkoutTimeHour.getSelectionModel().select(LocalDateTime.now().getHour());
        Label timeSeparator = new Label(":");
        ComboBox<String> newWorkoutTimeMin = new ComboBox<>(mins);
        newWorkoutTimeMin.getSelectionModel().select(LocalDateTime.now().getMinute());
        Label workoutDurationInstruction = new Label("Kesto:");
        Spinner<Integer> newWorkoutDurationHour = new Spinner<>(0, 24, 0);
        Label workoutDurationHourLabel = new Label("t");
        Spinner<Integer> newWorkoutDurationMin = new Spinner<>(0, 59, 0);
        Label workoutDurationMinLabel = new Label("min");
        Label workoutDistanceInstruction = new Label("Matka:");
        workoutDistanceInstruction.setVisible(false);
        TextField newWorkoutDistanceKm = new TextField();
        newWorkoutDistanceKm.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        newWorkoutDistanceKm.setVisible(false);
        Label workoutDistanceKmLabel = new Label("km");
        workoutDistanceKmLabel.setVisible(false);
        TextField newWorkoutDistanceM = new TextField();
        newWorkoutDistanceM.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        newWorkoutDistanceM.setVisible(false);
        Label workoutDistanceMLabel = new Label("m");
        workoutDistanceMLabel.setVisible(false);
        Label workoutMhrInstruction = new Label("Keskisyke:");
        TextField newWorkoutMhr = new TextField();
        newWorkoutMhr.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        Label workoutMhrLabel = new Label("bpm");
        Label workoutNotesInstruction = new Label("Muistiinpano:");
        TextField newWorkoutNotes = new TextField();
        
        GridPane addWorkoutPane = new GridPane();
        ColumnConstraints addWorkoutCol0 = new ColumnConstraints();
        addWorkoutCol0.setHgrow(Priority.ALWAYS);
        addWorkoutCol0.setHalignment(HPos.CENTER);
        ColumnConstraints addWorkoutCol1 = new ColumnConstraints(80);
        addWorkoutCol1.setHalignment(HPos.RIGHT);
        ColumnConstraints addWorkoutCol6 = new ColumnConstraints();
        addWorkoutCol6.setHgrow(Priority.ALWAYS);
        addWorkoutPane.getColumnConstraints().addAll(addWorkoutCol0, addWorkoutCol1,
                new ColumnConstraints(80), new ColumnConstraints(20), new ColumnConstraints(80),
                new ColumnConstraints(36), addWorkoutCol6);
        addWorkoutPane.add(addWorkoutLabel, 0, 0, 7, 1);
        addWorkoutPane.add(workoutSportInstruction, 1, 2);
        addWorkoutPane.add(newWorkoutSport, 2, 2, 3, 1);
        addWorkoutPane.add(newWorkoutSportIconView, 5, 2);
        addWorkoutPane.add(workoutDayInstruction, 1, 3);
        addWorkoutPane.add(newWorkoutDay, 2, 3, 4, 1);
        addWorkoutPane.add(workoutTimeInstruction, 1, 4);
        addWorkoutPane.add(newWorkoutTimeHour, 2, 4);
        addWorkoutPane.add(timeSeparator, 3, 4);
        addWorkoutPane.add(newWorkoutTimeMin, 4, 4);
        addWorkoutPane.add(workoutDurationInstruction, 1, 5);
        addWorkoutPane.add(newWorkoutDurationHour, 2, 5);
        addWorkoutPane.add(workoutDurationHourLabel, 3, 5);
        addWorkoutPane.add(newWorkoutDurationMin, 4, 5);
        addWorkoutPane.add(workoutDurationMinLabel, 5, 5);
        addWorkoutPane.add(workoutDistanceInstruction, 1, 6);
        addWorkoutPane.add(newWorkoutDistanceKm, 2, 6);
        addWorkoutPane.add(workoutDistanceKmLabel, 3, 6);
        addWorkoutPane.add(newWorkoutDistanceM, 4, 6);
        addWorkoutPane.add(workoutDistanceMLabel, 5, 6);
        addWorkoutPane.add(workoutMhrInstruction, 1, 7);
        addWorkoutPane.add(newWorkoutMhr, 2, 7, 3, 1);
        addWorkoutPane.add(workoutMhrLabel, 5, 7);
        addWorkoutPane.add(workoutNotesInstruction, 1, 8);
        addWorkoutPane.add(newWorkoutNotes, 2, 8, 4, 1);
        addWorkoutPane.add(createWorkoutButton, 2, 10);
        addWorkoutPane.add(cancelWorkoutButton, 4, 10, 2, 1);
        addWorkoutPane.add(closeWorkoutWindowButton, 0, 12, 7, 1);
        addWorkoutPane.setAlignment(Pos.CENTER);
        addWorkoutPane.setVgap(10);
        addWorkoutPane.setHgap(10);
        addWorkoutPane.setPadding(new Insets(20, 20, 20, 20));
        
        Scene addWorkoutScene = new Scene(addWorkoutPane, 400, 450);
        
        
        /**
        * ACTIONS
        */
        
        // Choose Sport in Workout Window
        
        newWorkoutSport.setOnAction((event) -> {
            Sport selectedSport = newWorkoutSport.getSelectionModel().getSelectedItem();
            Image selectedSportIcon = uiService.getSportsIcon(selectedSport);
            newWorkoutSportIconView.setImage(selectedSportIcon);
            Boolean showDistance = selectedSport.isShowDistance();
            workoutDistanceInstruction.setVisible(showDistance);
            newWorkoutDistanceKm.setVisible(showDistance);
            workoutDistanceKmLabel.setVisible(showDistance);
            newWorkoutDistanceM.setVisible(showDistance);
            workoutDistanceMLabel.setVisible(showDistance);
        });
        
        // Create new workout
        
        createWorkoutButton.setOnAction((event) -> {
            int workoutSport = newWorkoutSport.getValue().getId();
            LocalDate workoutDay = newWorkoutDay.getValue();
            int workoutTimeHour = Integer.valueOf(newWorkoutTimeHour.getValue());
            int workoutTimeMin = Integer.valueOf(newWorkoutTimeMin.getValue());
            LocalDateTime workoutDateTime = workoutDay.atTime(workoutTimeHour, workoutTimeMin);
            int workoutDurationHour = newWorkoutDurationHour.getValue();
            int workoutDurationMin = newWorkoutDurationMin.getValue();
            int workoutDuration = workoutDurationHour * 60 + workoutDurationMin;
            int workoutDistanceKm;
            if (newWorkoutDistanceKm.getText().trim().isEmpty()) {
                workoutDistanceKm = 0;
            } else {
                workoutDistanceKm = Integer.valueOf(newWorkoutDistanceKm.getText());
            }
            int workoutDistanceM;
            if (newWorkoutDistanceM.getText().trim().isEmpty()) {
                workoutDistanceM = 0;
            } else {
                workoutDistanceM = Integer.valueOf(newWorkoutDistanceM.getText());
            }
            int workoutDistance = workoutDistanceKm * 1000 + workoutDistanceM;
            int workoutMhr;
            if (newWorkoutMhr.getText().trim().isEmpty()) {
                workoutMhr = 0;
            } else {
                workoutMhr = Integer.valueOf(newWorkoutMhr.getText());
            }
            String workoutNotes = newWorkoutNotes.getText();
            
            if (workoutSport == 0) {
                uiService.showError("Lajia ei ole valittu!", "Valitse treenin urheilulaji valikosta ja yritä uudelleen.");
            } else if (workoutDistanceM > 999) {
                newWorkoutDistanceKm.setText(String.valueOf(workoutDistance/1000));
                newWorkoutDistanceM.setText(String.valueOf(workoutDistance%1000));
                uiService.showInfo("Päivitin matkan puolestasi.", "Korjasin yli 999 metrin matkan kilometreiksi. Tarkista matka. Jos matka on oikein, paina uudelleen 'Lisää'.");
            } else if (workoutDay.isAfter(LocalDate.now())) {
                uiService.showError("Virheellinen päivä!", "Valittu päivä on myöhemmin kuin tänään. Tarkista päivä ja yritä uudelleen.");
            } else if (workoutDuration < 1) {
                uiService.showError("Kesto puuttuu!", "Anna treenin kesto ja yritä uudelleen.");
            } else if (workoutDistance > 1000000) {
                uiService.showError("Matka ei voi olla yli 1.000 km!", "Tarkista matkan pituus ja yritä uudelleen.");
            } else if (workoutMhr > 250) {
                uiService.showError("Keskisyke ei voi olla yli 250 bpm!", "Tarkista keskisyke ja yritä uudelleen.");
            } else {
                Workout workout = new Workout(0, Timestamp.valueOf(workoutDateTime), treeniAppService.getLoggedInUser(), treeniAppService.getSportById(workoutSport), workoutDuration, workoutDistance, workoutMhr, workoutNotes);
                
                try {
                    if (treeniAppService.newWorkout(workout)) {
                        addWorkoutWindow.close();
                        newWorkoutSport.getSelectionModel().select(0);
                        newWorkoutDay.setValue(LocalDate.now());
                        newWorkoutTimeHour.getSelectionModel().select(LocalDateTime.now().getHour());
                        newWorkoutTimeMin.getSelectionModel().select(LocalDateTime.now().getMinute());
                        newWorkoutDurationHour.getValueFactory().setValue(0);
                        newWorkoutDurationMin.getValueFactory().setValue(0);
                        newWorkoutDistanceKm.setText("");
                        newWorkoutDistanceM.setText("");
                        newWorkoutMhr.setText("");
                        newWorkoutNotes.setText("");
                    } else {
                        uiService.showError("Lisääminen ei onnistunut!", "Todennäköisesti ohjelmassa tapahtui virhe tallennettaessa treeniä tietokantaan.");
                    }
                } catch (SQLException e) {
                    uiService.showError("Tietokantavirhe!", "Käyttäjän treenien haku tietokannasta epäonnistui. Ohjelma suljetaan.");
                    Platform.exit();
                } catch (Exception e) {
                    uiService.showError("Tapahtui tuntematon virhe!", "Ohjelma voi käyttäytyä oudosti. Tarvittaessa voit käynnistää ohjelman uudelleen.");
                }
                
            }
        });
        
        // Clear Add Workout Form
        
        cancelWorkoutButton.setOnAction((event) -> {
            newWorkoutSport.getSelectionModel().select(0);
            newWorkoutDay.setValue(LocalDate.now());
            newWorkoutTimeHour.getSelectionModel().select(LocalDateTime.now().getHour());
            newWorkoutTimeMin.getSelectionModel().select(LocalDateTime.now().getMinute());
            newWorkoutDurationHour.getValueFactory().setValue(0);
            newWorkoutDurationMin.getValueFactory().setValue(0);
            newWorkoutDistanceKm.setText("");
            newWorkoutDistanceM.setText("");
            newWorkoutMhr.setText("");
            newWorkoutNotes.setText("");
        });
        
        // Close Workout Window
        
        closeWorkoutWindowButton.setOnAction((event) -> {
            addWorkoutWindow.close();
        });
        
        return addWorkoutScene;
        
    }
    
}
