
package treeniapp.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

/**
 * Class handles the GUI of the application
 */
public class TreeniUi extends Application {
    
    // Stages
    private static Stage pStage;
    private Stage addWorkoutWindow;
    
    // Scenes
    private Scene mainScene;
    private Scene loginScene;
    private Scene newUserScene;
    private Scene addWorkoutScene;
    
    // Services
    private TreeniAppService treeniAppService;
    private UserDao userDao;
    private WorkoutDao workoutDao;
    private SportDao sportDao;
    
    // General variables
    private VBox workoutNodes;
    private Label workoutsTotal;
    private ObservableList hours;
    private ObservableList mins;
    
    @Override
    public void init() {
        
        // Set 'clearDatabases' to true if want to clear databases
        Boolean clearDatabases = false;
        
        // Start services
        SQLService sql;
        try {
            sql = new SQLService();
            sql.initialiseDatabases(clearDatabases);
            userDao = new SQLUserDao(sql);
            sportDao = new SQLSportDao(sql);
            workoutDao = new SQLWorkoutDao(sql, userDao, sportDao);
            treeniAppService = new TreeniAppService(userDao, workoutDao, sportDao);
        } catch (SQLException e) {
            System.out.println("VIRHE! Tietokannan alustaminen ei onnistunut. Ohjelma suljetaan.");
            Platform.exit();
        } catch (FileNotFoundException e) {
            System.out.println("VIRHE! Asetustiedostoa (config.properties) ei löytynyt. Ohjelma suljetaan.");
            Platform.exit();
        } catch (IOException e) {
            System.out.println("VIRHE! Asetustiedoston (config.properties) lataaminen ei onnistunut. Ohjelma suljetaan.");
            Platform.exit();
        } catch (Exception e) {
            System.out.println("VIRHE! Tapahtui tuntematon virhe. Ohjelma suljetaan.");
            Platform.exit();
        }
        
        // Format basic variables
        hours = generateNumberList(0, 23);
        mins = generateNumberList(0, 59);
    }
    
    /**
    * Method to create a workout node for the workout list in the application's main view.
    * 
    * @param    workout   The <code>Workout</code> object for which the node is created.
    * 
    * @return <code>Node</code> containing the workout information.
    */
    public Node createWorkoutNode(Workout workout) {
        HBox workoutBox = new HBox(10);
        Label workoutDate = new Label(workout.getDayMonth());
        Image workoutIcon = getSportsIcon(workout.getSport());
        ImageView workoutIconView = new ImageView(workoutIcon);
        Label workoutSport = new Label(treeniAppService.getSportById(workout.getSport().getId()).getName());
        Label workoutDuration = new Label(workout.getDurationFormatted());
        Image moreIcon = new Image(getClass().getResourceAsStream("/more.png"));
        ImageView workoutMore = new ImageView(moreIcon);
        
        Scene viewWorkoutScene = viewWorkout(workout);
        
        workoutMore.setOnMouseClicked((event) -> {
            pStage.setScene(viewWorkoutScene);
        });
        
        workoutDate.setMaxWidth(40);
        workoutDate.setMinWidth(40);
        workoutIconView.setFitHeight(20);
        workoutIconView.setFitWidth(20);
        workoutSport.setMaxWidth(125);
        workoutSport.setMinWidth(125);
        workoutDuration.setMaxWidth(40);
        workoutDuration.setMinWidth(40);
        workoutMore.setFitHeight(20);
        workoutMore.setFitWidth(20);
        
        workoutBox.getChildren().addAll(workoutDate, workoutIconView, workoutSport, workoutDuration, workoutMore);
        return workoutBox;
    }
    
    /**
    * Method to redraw <code>User</code>'s all workout nodes in the application's main view.
    */
    public void redrawWorkouts() {
        workoutNodes.getChildren().clear();
        
        if (treeniAppService.getLoggedInUser() != null) {
            try {
                List<Workout> workouts = treeniAppService.getWorkouts(treeniAppService.getLoggedInUser());
                workouts.forEach(workout->{
                    workoutNodes.getChildren().add(createWorkoutNode(workout));
                });
                workoutsTotal.setText(treeniAppService.getTotalTimeFormatted(treeniAppService.getLoggedInUser()));
            } catch (SQLException e) {
                showError("Käyttäjän treenien haku tietokannasta epäonnistui. Ohjelma suljetaan.");
                Platform.exit();
            } catch (Exception e) {
                showError("Tapahtui tuntematon virhe. Ohjelma suljetaan.");
                Platform.exit();
            }
        }
    }
    
    /**
    * Method to create a list of all <code>Sport</code> objects for the add workout view's drop down list.
    * 
    * @return <code>ObservableList</code> consisting of all <code>Sport</code> objects.
    */
    public ObservableList<Sport> formatSportsDropdown() {
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
    * Method to create a view of one workout.
    * 
    * @param    workout   The <code>Workout</code> object for which the view is created.
    * 
    * @return The <code>Scene</code> containing the requested <code>Workout</code>'s view.
    */
    public Scene viewWorkout(Workout workout) {
        
        // Header
        HBox headerViewWorkoutPane = new HBox();
        Label viewWorkoutHeader = new Label("Treenisi " + workout.getDayMonthYear() + " klo " + workout.getTime());
        viewWorkoutHeader.setFont(new Font(20.0));
        headerViewWorkoutPane.setPadding(new Insets(10));
        headerViewWorkoutPane.setAlignment(Pos.CENTER);
        headerViewWorkoutPane.getChildren().addAll(viewWorkoutHeader);
        
        // Icon
        HBox iconViewWorkoutPane = new HBox();
        ImageView viewWorkoutSportIconView = new ImageView(getSportsIcon(workout.getSport()));
        viewWorkoutSportIconView.setFitHeight(36);
        viewWorkoutSportIconView.setFitWidth(36);
        iconViewWorkoutPane.setPadding(new Insets(0, 0, 10, 0));
        iconViewWorkoutPane.setAlignment(Pos.CENTER);
        iconViewWorkoutPane.getChildren().addAll(viewWorkoutSportIconView);
        
        // Workout View
        Label viewWorkoutSportLabel = new Label("Laji:");
        Label viewWorkoutSport = new Label(workout.getSport().getName());
        Label viewWorkoutDurationLabel = new Label("Kesto:");
        Label viewWorkoutDuration = new Label(workout.getDurationFormatted());
        Label viewWorkoutDistanceLabel = new Label("Matka:");
        Label viewWorkoutDistance = new Label(workout.getDistanceFormatted());
        Label viewWorkoutMhrLabel = new Label("Keskisyke:");
        Label viewWorkoutMhr = new Label(workout.getMhr() + " bpm");
        Text viewWorkoutNotes = new Text(workout.getNotes());
        
        int gridRowCounter = 0;
        
        GridPane workoutPane = new GridPane();
        workoutPane.setAlignment(Pos.CENTER);
        workoutPane.setVgap(10);
        workoutPane.setHgap(10);
        workoutPane.setPadding(new Insets(20, 20, 20, 20));
        workoutPane.add(viewWorkoutSportLabel, 0, gridRowCounter);
        workoutPane.add(viewWorkoutSport, 1, gridRowCounter);
        gridRowCounter++;
        workoutPane.add(viewWorkoutDurationLabel, 0, gridRowCounter);
        workoutPane.add(viewWorkoutDuration, 1, gridRowCounter);
        gridRowCounter++;        
        if (workout.getSport().isShowDistance()) {
            workoutPane.add(viewWorkoutDistanceLabel, 0, gridRowCounter);
            workoutPane.add(viewWorkoutDistance, 1, gridRowCounter);
            gridRowCounter++;
        }
        if (workout.getMhr() != 0) {
            workoutPane.add(viewWorkoutMhrLabel, 0, gridRowCounter);
            workoutPane.add(viewWorkoutMhr, 1, gridRowCounter);
            gridRowCounter++;
        }
        if (!workout.getNotes().trim().equals("")) {
            gridRowCounter++;
            workoutPane.add(viewWorkoutNotes, 0, gridRowCounter, 2, 1);
        }
        
        // Buttons
        Button closeViewWorkoutButton = new Button("Takaisin");
        Button deleteWorkoutButton = new Button("Poista treeni");
        
        HBox buttonsViewWorkoutPane = new HBox();
        buttonsViewWorkoutPane.setPadding(new Insets(10));
        Region buttonsSpacer = new Region();
        HBox.setHgrow(buttonsSpacer, Priority.ALWAYS);
        buttonsViewWorkoutPane.getChildren().addAll(deleteWorkoutButton, buttonsSpacer, closeViewWorkoutButton);
        
        // Collect Workout View
        VBox viewWorkoutPane = new VBox();
        Region viewWorkoutSpacer = new Region();
        VBox.setVgrow(viewWorkoutSpacer, Priority.ALWAYS);
        viewWorkoutPane.getChildren().addAll(headerViewWorkoutPane, iconViewWorkoutPane, workoutPane, viewWorkoutSpacer, buttonsViewWorkoutPane);
        
        // Close Workout View
        closeViewWorkoutButton.setOnAction((event) -> {
            pStage.setScene(mainScene);
        });
        
        // Delete Workout
        deleteWorkoutButton.setOnAction((event) -> {
            Alert notInUseAlert = new Alert(AlertType.INFORMATION);
            notInUseAlert.setHeaderText("Toiminto ei ole käytössä!");
            notInUseAlert.setContentText("Treenien poistaminen lisätään tulevissa versioissa. Toistaiseksi poistaminen ei ole mahdollista.");
            notInUseAlert.show();
        });

        return new Scene(viewWorkoutPane, 330, 330);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        setPrimaryStage(primaryStage);
        pStage = primaryStage;
        
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
        
        Image treeniAppIcon = new Image(getClass().getResourceAsStream("/treeniapp.png"));
        ImageView treeniAppIconView = new ImageView(treeniAppIcon);
        treeniAppIconView.setFitHeight(54);
        treeniAppIconView.setFitWidth(240);
        
        loginPane.add(treeniAppIconView, 0, 0);
        loginPane.add(loginInstruction, 0, 2);
        loginPane.add(loginUsername, 0, 3);
        loginPane.add(loginButton, 0, 4);
        loginPane.add(loginNote, 0, 5);
        loginPane.add(loginNewUserButton, 0, 6);
        loginPane.setPrefSize(300, 300);
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setVgap(10);
        loginPane.setHgap(10);
        loginPane.setPadding(new Insets(20, 20, 20, 20));

        loginScene = new Scene(loginPane);
        
        
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

        newUserScene = new Scene(newUserPane);
        
        
        /**
        * MAIN SCREEN
        */
        
        // Top
        HBox topMainPane = new HBox();
        Label welcomeLabel = new Label("");
        welcomeLabel.setFont(new Font(30.0));
        
        topMainPane.setPadding(new Insets(0, 0, 10, 0));
        topMainPane.setAlignment(Pos.CENTER);
        topMainPane.getChildren().addAll(welcomeLabel);
        
        // Workouts
        ScrollPane workoutsMainPane = new ScrollPane();
        workoutNodes = new VBox(10);
        workoutNodes.setPadding(new Insets(10, 10, 10, 10));
        workoutNodes.setMaxWidth(310);
        workoutNodes.setMinWidth(300);
        workoutsMainPane.setContent(workoutNodes);

        // Bottom
        Button addWorkoutButton = new Button("Lisää treeni");
        Button logoutButton = new Button("Kirjaudu ulos");
        Label totalLabel = new Label("Treenit yhteensä:");
        totalLabel.setFont(new Font(15.0));
        workoutsTotal = new Label("");
        workoutsTotal.setFont(new Font(15.0));
        
        HBox totalMainPane = new HBox();
        totalMainPane.setPadding(new Insets(10, 0, 0, 0));
        Region totalSpacer = new Region();
        HBox.setHgrow(totalSpacer, Priority.ALWAYS);
        totalMainPane.getChildren().addAll(totalLabel, totalSpacer, workoutsTotal);
        
        HBox buttonsMainPane = new HBox();
        buttonsMainPane.setPadding(new Insets(10, 0, 0, 0));
        Region buttonsSpacer = new Region();
        HBox.setHgrow(buttonsSpacer, Priority.ALWAYS);
        buttonsMainPane.getChildren().addAll(addWorkoutButton, buttonsSpacer, logoutButton);
        
        VBox bottomMainPane = new VBox();
        bottomMainPane.getChildren().addAll(totalMainPane, buttonsMainPane);
        
        // Collect the main screen
        BorderPane mainPane = new BorderPane(workoutsMainPane);
        mainPane.setPadding(new Insets(10, 10, 10, 10));
        mainPane.setTop(topMainPane);
        mainPane.setBottom(bottomMainPane);
        mainScene = new Scene(mainPane, 330, 600);
        redrawWorkouts();
        
        
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
        
        Label workoutSportInstruction = new Label("Laji:");
        ComboBox<Sport> newWorkoutSport = new ComboBox<>(formatSportsDropdown());
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
        addWorkoutPane.add(newWorkoutWarning, 0, 12, 7, 1);
        addWorkoutPane.add(closeWorkoutWindowButton, 0, 13, 7, 1);
        addWorkoutPane.setAlignment(Pos.CENTER);
        addWorkoutPane.setVgap(10);
        addWorkoutPane.setHgap(10);
        addWorkoutPane.setPadding(new Insets(20, 20, 20, 20));
        
        addWorkoutScene = new Scene(addWorkoutPane, 400, 450);
        
        addWorkoutWindow = new Stage();
        
        
        /**
        * SCENE ACTIONS
        */
        
        // Login
        
        loginButton.setOnAction((event) -> {
            String username = loginUsername.getText();
            if (treeniAppService.login(username)) {
                welcomeLabel.setText(treeniAppService.getLoggedInUser().getName());
                redrawWorkouts();
                pStage.setScene(mainScene);
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
            pStage.setScene(loginScene);
        });
        
        // To New User Screen
        
        loginNewUserButton.setOnAction((event) -> {
            pStage.setScene(newUserScene);
        });
        
        // Back To Login Screen
        
        newUserBackButton.setOnAction((event) -> {
            pStage.setScene(loginScene);
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
            } else try {
                if (treeniAppService.newUser(username, name)) {
                    newUserUsername.setText("");
                    newUserName.setText("");
                    loginNote.setText("Uusi käyttäjä '" + username + "' luotu!");
                    loginNote.setTextFill(Color.BLACK);
                    pStage.setScene(loginScene);
                    newUserNote.setText("");
                } else {
                    newUserNote.setText("Tunnus '" + username + "'\n"
                            + "on jo käytössä!");
                }
            } catch (SQLException e) {
                showError("Käyttäjän treenien haku tietokannasta epäonnistui. Ohjelma suljetaan.");
                Platform.exit();
            } catch (Exception e) {
                showError("Tapahtui tuntematon virhe. Ohjelma suljetaan.");
                Platform.exit();
            }
        });
        
        // Open Add Workout Window
        
        addWorkoutButton.setOnAction((event) -> {
            addWorkoutWindow.setTitle("Lisää treeni - TreeniApp");
            addWorkoutWindow.setScene(addWorkoutScene);
            addWorkoutWindow.show();
        });
        
        // Choose Sport in Workout Window
        
        newWorkoutSport.setOnAction((event) -> {
            Sport selectedSport = newWorkoutSport.getSelectionModel().getSelectedItem();
            Image selectedSportIcon = getSportsIcon(selectedSport);
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
                
                try {
                    if (treeniAppService.newWorkout(workout)) {
                        redrawWorkouts();
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
                        newWorkoutWarning.setText("");
                        newWorkoutWarning.setText("");
                    } else {
                        newWorkoutWarning.setText("Virhe: Lisääminen ei onnistunut!");
                    }
                } catch (SQLException e) {
                    showError("Käyttäjän treenien haku tietokannasta epäonnistui. Ohjelma suljetaan.");
                    Platform.exit();
                } catch (Exception e) {
                    showError("Tapahtui tuntematon virhe. Ohjelma suljetaan.");
                    Platform.exit();
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
            addWorkoutWindow.close();
        });

        pStage.setScene(loginScene);
        pStage.setTitle("TreeniApp");
        pStage.show();
    }
    
    /**
    * Method to show error message.
    * 
    * @param    message   The error message to show.
    */
    public void showError(String message) {
        Alert error = new Alert(AlertType.ERROR);
                error.setTitle("Virhe");
                error.setHeaderText(message);
                error.showAndWait();
    }
    
    public static Stage getPrimaryStage() {
        return pStage;
    }

    private void setPrimaryStage(Stage pStage) {
        TreeniUi.pStage = pStage;
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
