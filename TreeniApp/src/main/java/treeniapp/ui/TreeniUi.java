
package treeniapp.ui;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.UnaryOperator;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import treeniapp.dao.UserDao;
import treeniapp.dao.sql.SQLUserDao;
import treeniapp.dao.WorkoutDao;
import treeniapp.dao.sql.SQLWorkoutDao;
import treeniapp.dao.SportDao;
import treeniapp.dao.sql.SQLSportDao;
import treeniapp.dao.sql.SQLService;
import treeniapp.domain.TreeniAppService;
import treeniapp.domain.Sport;
import treeniapp.domain.Workout;

public class TreeniUi extends Application {
    
    private TreeniAppService treeniAppService;
    private UserDao userDao;
    private WorkoutDao workoutDao;
    private SportDao sportDao;
    
    private VBox workoutNodes;
    private ObservableList hours;
    private ObservableList mins;
    
    @Override
    public void init() throws Exception {
        
        // Set 'clearDatabases' to true if want to clear databases
        Boolean clearDatabases = false;
        
        // Start services
        SQLService sql = new SQLService();
        sql.initialiseDatabases(clearDatabases);
        userDao = new SQLUserDao(sql);
        sportDao = new SQLSportDao(sql);
        workoutDao = new SQLWorkoutDao(sql, userDao, sportDao);
        treeniAppService = new TreeniAppService(userDao, workoutDao, sportDao);
        
        // Format basic variables
        hours = generateNumberList(0, 23);
        mins = generateNumberList(0, 59);
    }
    
    public Node createWorkoutNode(Workout workout) {
        HBox workoutBox = new HBox(10);
        Label workoutDate = new Label(workout.getDayMonth());
        Image workoutIcon = getSportsIcon(workout.getSport());
        ImageView workoutIconView = new ImageView(workoutIcon);
        Label workoutSport = new Label(treeniAppService.getSportById(workout.getSport().getId()).getName());
        Label workoutDuration = new Label(workout.getDurationFormat());
        workoutDate.setMaxWidth(40);
        workoutDate.setMinWidth(40);
        workoutIconView.setFitHeight(20);
        workoutIconView.setFitWidth(20);
        workoutSport.setMaxWidth(150);
        workoutSport.setMinWidth(150);
        workoutDuration.setMaxWidth(40);
        workoutDuration.setMinWidth(40);
        
        workoutBox.getChildren().addAll(workoutDate, workoutIconView, workoutSport, workoutDuration);
        return workoutBox;
    }
    
    public void redrawWorkouts() {
        workoutNodes.getChildren().clear();     

        List<Workout> workouts = treeniAppService.getWorkouts();
        workouts.forEach(workout->{
            workoutNodes.getChildren().add(createWorkoutNode(workout));
        });     
    }
    
    public ObservableList<Sport> formatSportsDropdown() {
        ObservableList<Sport> sportsDropdown = FXCollections.observableArrayList();
        sportsDropdown.add(new Sport(0, "Valitse laji", null, false));
        for (Sport s : treeniAppService.getSports()) {
            sportsDropdown.add(s);
        }
        return sportsDropdown;
    }
    
    public Image getSportsIcon(Sport sport) {
        String iconFileName = "/sporticons/" + sport.getIcon() + ".png";
        Image icon = new Image(getClass().getResourceAsStream(iconFileName));
        return icon;
    }
    
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
    
    @Override
    public void start(Stage primaryStage) {
        
        
        /**
        * LOGIN SCREEN
        */   
        
        Label loginInstruction = new Label("Anna tunnuksesi ja paina 'Kirjaudu'");
        Button loginButton = new Button("Kirjaudu");
        Button loginNewUserButton = new Button("Luo uusi käyttäjä");
        TextField loginUsername = new TextField();
        Label loginNote = new Label("Testitunnus: 'testaaja'"); //REMOVE
        loginNote.setTextFill(Color.RED);

        GridPane loginPane = new GridPane();
        loginPane.add(loginInstruction, 0, 0);
        loginPane.add(loginUsername, 0, 1);
        loginPane.add(loginButton, 0, 2);
        loginPane.add(loginNote, 0, 3);
        loginPane.add(loginNewUserButton, 0, 4);
        loginPane.setPrefSize(300, 300);
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setVgap(10);
        loginPane.setHgap(10);
        loginPane.setPadding(new Insets(20, 20, 20, 20));

        Scene loginScene = new Scene(loginPane);
        
        
        /**
        * NEW USER SCREEN
        */
        
        Label newUserInstruction = new Label("Uusi käyttäjä");
        Label newUserUsernameInstruction = new Label("Tunnus:");
        TextField newUserUsername = new TextField();
        Label newUserNameInstruction = new Label("Nimi:");
        TextField newUserName = new TextField();
        Button newUserButton = new Button("Luo käyttäjä");
        Button newUserBackButton = new Button("Peruuta");
        Label newUserNote = new Label("");
        newUserNote.setTextFill(Color.RED);

        GridPane newUserPane = new GridPane();
        newUserPane.add(newUserInstruction, 0, 0);
        newUserPane.add(newUserUsernameInstruction, 0, 1);
        newUserPane.add(newUserUsername, 1, 1);
        newUserPane.add(newUserNameInstruction, 0, 2);
        newUserPane.add(newUserName, 1, 2);
        newUserPane.add(newUserButton, 1, 3);
        newUserPane.add(newUserNote, 1, 4);
        newUserPane.add(newUserBackButton, 1, 5);
        newUserPane.setPrefSize(300, 300);
        newUserPane.setAlignment(Pos.CENTER);
        newUserPane.setVgap(10);
        newUserPane.setHgap(10);
        newUserPane.setPadding(new Insets(20, 20, 20, 20));

        Scene newUserScene = new Scene(newUserPane);
        
        
        /**
        * MAIN SCREEN
        */

        Label welcomeLabel = new Label("");
        Button addWorkoutButton = new Button("Lisää treeni");
        Button logoutButton = new Button("Kirjaudu ulos");

        ScrollPane mainPaneScroller = new ScrollPane();
        BorderPane mainPane = new BorderPane(mainPaneScroller);
        mainPane.setPadding(new Insets(10, 10, 10, 10));
        HBox topMainPane = new HBox();
        topMainPane.setPadding(new Insets(0, 0, 10, 0));
        topMainPane.setAlignment(Pos.CENTER);
        topMainPane.getChildren().addAll(welcomeLabel);
        HBox bottomMainPane = new HBox();
        bottomMainPane.setPadding(new Insets(10, 0, 0, 0));
        Region menuSpacer = new Region();
        HBox.setHgrow(menuSpacer, Priority.ALWAYS);
        bottomMainPane.getChildren().addAll(addWorkoutButton, menuSpacer, logoutButton);
        mainPane.setTop(topMainPane);
        mainPane.setBottom(bottomMainPane);
        
        workoutNodes = new VBox(10);
        workoutNodes.setPadding(new Insets(10, 10, 10, 10));
        workoutNodes.setMaxWidth(310);
        workoutNodes.setMinWidth(300);
        redrawWorkouts();
        mainPaneScroller.setContent(workoutNodes);

        Scene mainScene = new Scene(mainPane, 330, 600);
        
        
        /**
        * ADD WORKOUT SCENE
        */
        
        Label addWorkoutLabel = new Label("Lisää uusi treeni");
        Button createWorkoutButton = new Button("Lisää");
        Button cancelWorkoutButton = new Button("Tyhjennä");
        Button closeWorkoutWindowButton = new Button("Sulje");
        
        UnaryOperator<Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]*")) { 
                return change;
            }
            return null;
        };
        
        Label newWorkoutSportInstruction = new Label("Laji:");
        ComboBox<Sport> newWorkoutSport = new ComboBox<>(formatSportsDropdown());
        newWorkoutSport.getSelectionModel().select(0);
        newWorkoutSport.setTooltip(new Tooltip("Valitse urheilulaji"));
        ImageView newWorkoutSportIconView = new ImageView();
        newWorkoutSportIconView.setFitHeight(36);
        newWorkoutSportIconView.setFitWidth(36);
        Label newWorkoutDayInstruction = new Label("Päivä:");
        DatePicker newWorkoutDay = new DatePicker(LocalDate.now());
        newWorkoutDay.setEditable(false);
        newWorkoutDay.setDayCellFactory(d -> new DateCell() {
               @Override public void updateItem(LocalDate item, boolean empty) {
                   super.updateItem(item, empty);
                   setDisable(item.isAfter(LocalDate.now()));
               }});
        Label newWorkoutTimeInstruction = new Label("Kello:");
        ComboBox<String> newWorkoutTimeHour = new ComboBox<>(hours);
        newWorkoutTimeHour.getSelectionModel().select(LocalDateTime.now().getHour());
        Label newWorkoutTimeSeparator = new Label(":");
        ComboBox<String> newWorkoutTimeMin = new ComboBox<>(mins);
        newWorkoutTimeMin.getSelectionModel().select(LocalDateTime.now().getMinute());
        Label newWorkoutDurationInstruction = new Label("Kesto:");
        Spinner<Integer> newWorkoutDurationHour = new Spinner<>(0, 24, 0);
        Label newWorkoutDurationHourLabel = new Label("t");
        Spinner<Integer> newWorkoutDurationMin = new Spinner<>(0, 59, 0);
        Label newWorkoutDurationMinLabel = new Label("min");
        Label newWorkoutDistanceInstruction = new Label("Matka:");
        newWorkoutDistanceInstruction.setVisible(false);
        TextField newWorkoutDistanceKm = new TextField();
        newWorkoutDistanceKm.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), null, integerFilter));
        newWorkoutDistanceKm.setVisible(false);
        Label newWorkoutDistanceKmLabel = new Label("km");
        newWorkoutDistanceKmLabel.setVisible(false);
        TextField newWorkoutDistanceM = new TextField();
        newWorkoutDistanceM.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), null, integerFilter));
        newWorkoutDistanceM.setVisible(false);
        Label newWorkoutDistanceMLabel = new Label("m");
        newWorkoutDistanceMLabel.setVisible(false);
        Label newWorkoutMhrInstruction = new Label("Keskisyke:");
        TextField newWorkoutMhr = new TextField();
        newWorkoutMhr.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), null, integerFilter));
        Label newWorkoutMhrLabel = new Label("bpm");
        Label newWorkoutNotesInstruction = new Label("Muistiinpano:");
        TextField newWorkoutNotes = new TextField();
        Label newWorkoutWarning = new Label("");
        newWorkoutWarning.setTextFill(Color.RED);
        
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
        addWorkoutPane.add(newWorkoutSportInstruction, 1, 2);
        addWorkoutPane.add(newWorkoutSport, 2, 2, 3, 1);
        addWorkoutPane.add(newWorkoutSportIconView, 5, 2);
        addWorkoutPane.add(newWorkoutDayInstruction, 1, 3);
        addWorkoutPane.add(newWorkoutDay, 2, 3, 4, 1);
        addWorkoutPane.add(newWorkoutTimeInstruction, 1, 4);
        addWorkoutPane.add(newWorkoutTimeHour, 2, 4);
        addWorkoutPane.add(newWorkoutTimeSeparator, 3, 4);
        addWorkoutPane.add(newWorkoutTimeMin, 4, 4);
        addWorkoutPane.add(newWorkoutDurationInstruction, 1, 5);
        addWorkoutPane.add(newWorkoutDurationHour, 2, 5);
        addWorkoutPane.add(newWorkoutDurationHourLabel, 3, 5);
        addWorkoutPane.add(newWorkoutDurationMin, 4, 5);
        addWorkoutPane.add(newWorkoutDurationMinLabel, 5, 5);
        addWorkoutPane.add(newWorkoutDistanceInstruction, 1, 6);
        addWorkoutPane.add(newWorkoutDistanceKm, 2, 6);
        addWorkoutPane.add(newWorkoutDistanceKmLabel, 3, 6);
        addWorkoutPane.add(newWorkoutDistanceM, 4, 6);
        addWorkoutPane.add(newWorkoutDistanceMLabel, 5, 6);
        addWorkoutPane.add(newWorkoutMhrInstruction, 1, 7);
        addWorkoutPane.add(newWorkoutMhr, 2, 7, 3, 1);
        addWorkoutPane.add(newWorkoutMhrLabel, 5, 7);
        addWorkoutPane.add(newWorkoutNotesInstruction, 1, 8);
        addWorkoutPane.add(newWorkoutNotes, 2, 8, 4, 1);
        addWorkoutPane.add(createWorkoutButton, 2, 10);
        addWorkoutPane.add(cancelWorkoutButton, 4, 10, 2, 1);
        addWorkoutPane.add(newWorkoutWarning, 0, 12, 7, 1);
        addWorkoutPane.add(closeWorkoutWindowButton, 0, 13, 7, 1);
        addWorkoutPane.setAlignment(Pos.CENTER);
        addWorkoutPane.setVgap(10);
        addWorkoutPane.setHgap(10);
        addWorkoutPane.setPadding(new Insets(20, 20, 20, 20));
        
        Scene workoutScene = new Scene(addWorkoutPane, 400, 450);
        
        Stage workoutWindow = new Stage();
        
        
        /**
        * SCENE ACTIONS
        */
        
        // Login
        
        loginButton.setOnAction((event) -> {
            String username = loginUsername.getText();
            if (treeniAppService.login(username)) {
                welcomeLabel.setText("Tervetuloa, " + treeniAppService.getLoggedInUser().getName() + "!");
                redrawWorkouts();
                primaryStage.setScene(mainScene);
                loginNote.setText("");
                loginUsername.setText("");
            } else if (username.length() < 1) {
                loginNote.setText("Anna käyttäjätunnus!");
                loginNote.setTextFill(Color.RED);
            } else {
                loginNote.setText("Käyttäjää '" + username + "' ei löytynyt!");
                loginNote.setTextFill(Color.RED);
            }
        });
        
        // Logout
        
        logoutButton.setOnAction((event) -> {
            loginNote.setTextFill(Color.BLACK);
            loginNote.setText("Uloskirjautuminen onnistui!");
            primaryStage.setScene(loginScene);
        });
        
        // To New User Screen
        
        loginNewUserButton.setOnAction((event) -> {
            primaryStage.setScene(newUserScene);
        });
        
        // Back To Login Screen
        
        newUserBackButton.setOnAction((event) -> {
            primaryStage.setScene(loginScene);
        });
        
        // Create new user
        
        newUserButton.setOnAction((event) -> {
            String username = newUserUsername.getText();
            String name = newUserName.getText();
            if (username.length() < 1 || username.length() > 15) {
                newUserNote.setText("Tunnuksen on oltava\n"
                        + "1-15 merkin pituinen.");
            } else if (name.length() < 1 || name.length() > 20) {
                newUserNote.setText("Nimen on oltava\n"
                        + "1-20 merkin pituinen.");
            } else if (treeniAppService.newUser(username, name)) {
                newUserUsername.setText("");
                newUserName.setText("");
                loginNote.setText("Uusi käyttäjä '" + username + "' luotu!");
                loginNote.setTextFill(Color.BLACK);
                primaryStage.setScene(loginScene);
                newUserNote.setText("");
            } else {
                newUserNote.setText("Tunnus '" + username + "'\n"
                        + "on jo käytössä!");
            }
        });
        
        // Open Add Workout Window
        
        addWorkoutButton.setOnAction((event) -> {
            workoutWindow.setTitle("Lisää treeni - TreeniApp");
            workoutWindow.setScene(workoutScene);
            workoutWindow.show();
        });
        
        // Choose Sport in Workout Window
        
        newWorkoutSport.setOnAction((event) -> {
            Sport selectedSport = newWorkoutSport.getSelectionModel().getSelectedItem();
            Image selectedSportIcon = getSportsIcon(selectedSport);
            newWorkoutSportIconView.setImage(selectedSportIcon);
            Boolean showDistance = selectedSport.isShowDistance();
            newWorkoutDistanceInstruction.setVisible(showDistance);
            newWorkoutDistanceKm.setVisible(showDistance);
            newWorkoutDistanceKmLabel.setVisible(showDistance);
            newWorkoutDistanceM.setVisible(showDistance);
            newWorkoutDistanceMLabel.setVisible(showDistance);
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
                newWorkoutWarning.setText("Virhe: Valitse laji.");
            } else if (workoutDistanceM > 999) {
                newWorkoutDistanceKm.setText(String.valueOf(workoutDistance/1000));
                newWorkoutDistanceM.setText(String.valueOf(workoutDistance%1000));
                newWorkoutWarning.setText("Korjasin yli 999 metrin matkan kilometreiksi. Tarkista matka.");
            } else if (workoutDay.isAfter(LocalDate.now())) {
                newWorkoutWarning.setText("Virhe: Valittu päivä on myöhemmin kuin tänään.");
            } else if (workoutDuration < 1) {
                newWorkoutWarning.setText("Virhe: Anna treenin kesto.");
            } else if (workoutDistance > 1000000) {
                newWorkoutWarning.setText("Virhe: Matka ei voi olla yli 1.000 km.");
            } else if (workoutMhr > 250) {
                newWorkoutWarning.setText("Virhe: Keskisyke ei voi olla yli 250 bpm.");
            } else {
                Workout workout = new Workout(0, Timestamp.valueOf(workoutDateTime), treeniAppService.getLoggedInUser(), treeniAppService.getSportById(workoutSport), workoutDuration, workoutDistance, workoutMhr, workoutNotes);
                
                if (treeniAppService.newWorkout(workout)) {
                    redrawWorkouts();
                    workoutWindow.close();
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
                    newWorkoutWarning.setText("");
                    newWorkoutWarning.setText("");
                } else {
                    newWorkoutWarning.setText("Virhe: Lisääminen ei onnistunut!");
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
            newWorkoutWarning.setText("");
        });
        
        // Close Workout Window
        
        closeWorkoutWindowButton.setOnAction((event) -> {
            workoutWindow.close();
        });
        

        primaryStage.setScene(loginScene);
        primaryStage.setTitle("TreeniApp");
        primaryStage.show();
    }

    @Override
    public void stop() {
      System.out.println("TreeniApp closed.");
    }    
    
    public static void main(String[] args) {
        System.out.println("TreeniApp launched.");
        launch(args);
    }
    
}
