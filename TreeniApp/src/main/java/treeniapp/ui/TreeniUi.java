
package treeniapp.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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
        
    }
    
    public Node createWorkoutNode(Workout workout) {
        HBox workoutBox = new HBox(10);
        Label workoutDate = new Label(workout.getDayMonth());
        Label workoutSport = new Label(sportDao.findById(workout.getSport().getId()).getName());
        Label workoutDuration = new Label(workout.getDurationFormat());
        workoutDate.setMaxWidth(40);
        workoutDate.setMinWidth(40);
        workoutSport.setMaxWidth(150);
        workoutSport.setMinWidth(150);
        workoutDuration.setMaxWidth(40);
        workoutDuration.setMinWidth(40);
        
        workoutBox.getChildren().addAll(workoutDate, workoutSport, workoutDuration);
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
        String iconFileName = "resources/" + sport.getIcon() + ".png";
        try {
            Image icon = new Image(new FileInputStream(iconFileName));
            return icon;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TreeniUi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
        workoutNodes.setMaxWidth(280);
        workoutNodes.setMinWidth(270);
        redrawWorkouts();
        mainPaneScroller.setContent(workoutNodes);

        Scene mainScene = new Scene(mainPane, 300, 600);
        
        /**
        * ADD WORKOUT SCENE
        */
        
        Label addWorkoutLabel = new Label("Lisää uusi treeni");
        Button closeWorkoutWindowButton = new Button("Sulje");
        
        Label newWorkoutSportInstruction = new Label("Laji:");
        ComboBox<Sport> newWorkoutSport = new ComboBox<>(formatSportsDropdown());
        newWorkoutSport.getSelectionModel().select(0);
        newWorkoutSport.setTooltip(new Tooltip("Valitse urheilulaji"));
        ImageView newWorkoutSportIconView = new ImageView();
        newWorkoutSportIconView.setFitHeight(36);
        newWorkoutSportIconView.setFitWidth(36);
        Label newWorkoutDayInstruction = new Label("Päivä:");
        DatePicker newWorkoutDay = new DatePicker(LocalDate.now());
        Label newWorkoutTimeInstruction = new Label("Kello:");
        TextField newWorkoutTimeHour = new TextField();
        TextField newWorkoutTimeMin = new TextField();
        Label newWorkoutDurationInstruction = new Label("Kesto:");
        TextField newWorkoutDuration = new TextField();
        Label newWorkoutDistanceInstruction = new Label("Matka:");
        TextField newWorkoutDistance = new TextField();
        Label newWorkoutMhrInstruction = new Label("Keskisyke:");
        TextField newWorkoutMhr = new TextField();
        Label newWorkoutNotesInstruction = new Label("Muistiinpano:");
        TextField newWorkoutNotes = new TextField();
        
        GridPane addWorkoutPane = new GridPane();
        addWorkoutPane.add(newWorkoutSportInstruction, 0, 1);
        addWorkoutPane.add(newWorkoutSport, 1, 1);
        addWorkoutPane.add(newWorkoutSportIconView, 2, 1);
        addWorkoutPane.add(newWorkoutDayInstruction, 0, 2);
        addWorkoutPane.add(newWorkoutDay, 1, 2, 2, 1);
        addWorkoutPane.add(newWorkoutTimeInstruction, 0, 3);
        addWorkoutPane.add(newWorkoutTimeHour, 1, 3);
        addWorkoutPane.add(newWorkoutTimeMin, 2, 3);
        addWorkoutPane.add(newWorkoutDurationInstruction, 0, 4);
        addWorkoutPane.add(newWorkoutDuration, 1, 4, 2, 1);
        addWorkoutPane.add(newWorkoutDistanceInstruction, 0, 5);
        addWorkoutPane.add(newWorkoutDistance, 1, 5, 2, 1);
        addWorkoutPane.add(newWorkoutMhrInstruction, 0, 6);
        addWorkoutPane.add(newWorkoutMhr, 1, 6, 2, 1);
        addWorkoutPane.add(newWorkoutNotesInstruction, 0, 7);
        addWorkoutPane.add(newWorkoutNotes, 1, 7, 2, 1);
        addWorkoutPane.setAlignment(Pos.CENTER);
        addWorkoutPane.setVgap(10);
        addWorkoutPane.setHgap(10);
        addWorkoutPane.setPadding(new Insets(20, 20, 20, 20));
        
        BorderPane workoutPane = new BorderPane();
        HBox topWorkoutPane = new HBox();
        topWorkoutPane.setPadding(new Insets(10, 10, 10, 10));
        topWorkoutPane.setAlignment(Pos.CENTER);
        topWorkoutPane.getChildren().addAll(addWorkoutLabel);
        HBox bottomWorkoutPane = new HBox();
        bottomWorkoutPane.setPadding(new Insets(10, 10, 10, 10));
        Region workoutMenuSpacer = new Region();
        HBox.setHgrow(workoutMenuSpacer, Priority.ALWAYS);
        bottomWorkoutPane.getChildren().addAll(workoutMenuSpacer, closeWorkoutWindowButton);
        workoutPane.setTop(topWorkoutPane);
        workoutPane.setCenter(addWorkoutPane);
        workoutPane.setBottom(bottomWorkoutPane);
        
        Scene workoutScene = new Scene(workoutPane, 400, 400);
        
        Stage workoutWindow = new Stage();
        
        /**
        * SCENE ACTIONS
        */
        
        // Login
        
        loginButton.setOnAction((event) -> {
            String username = loginUsername.getText();
            if (treeniAppService.login(username)) {
                loginNote.setText("");
                welcomeLabel.setText("Tervetuloa, " + treeniAppService.getLoggedInUser().getName() + "!");
                primaryStage.setScene(mainScene);
                loginUsername.setText("");
                primaryStage.setScene(mainScene);
                redrawWorkouts();
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
            newWorkoutDistanceInstruction.setVisible(selectedSport.isShowDistance());
            newWorkoutDistance.setVisible(selectedSport.isShowDistance());
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
      // Quitting the app
      System.out.println("TreeniApp closed");
    }    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
