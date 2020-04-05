
package treeniapp.ui;

import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
import treeniapp.dao.TempSportDao;
import treeniapp.dao.sql.SQLService;
import treeniapp.domain.TreeniAppService;
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
        Boolean clearDatabases = true;
        
        // Start services
        SQLService sql = new SQLService();
        sql.initialiseDatabases(clearDatabases);
        userDao = new SQLUserDao(sql);
        sportDao = new TempSportDao(sql);
        workoutDao = new SQLWorkoutDao(sql, userDao, sportDao);
        treeniAppService = new TreeniAppService(userDao, workoutDao, sportDao);
        
    }
    
    public Node createWorkoutNode(Workout workout) {
        HBox workoutBox = new HBox(10);
        Label workoutDate = new Label(workout.getDayMonth());
        Label workoutSport = new Label(sportDao.findById(workout.getSport().getId()).getName());
        Label workoutDuration = new Label(workout.getDurationFormat());
        workoutDate.setMinHeight(20);
        workoutSport.setMinHeight(20);
        workoutDuration.setMinHeight(20);
        
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
    
    @Override
    public void start(Stage primaryStage) {
        
        /**
        * LOGIN SCREEN
        */   
        
        Label loginInstruction = new Label("Anna tunnuksesi ja paina 'Kirjaudu'");
        Button loginButton = new Button("Kirjaudu");
        Button loginNewUserButton = new Button("Luo uusi käyttäjä");
        TextField loginUsername = new TextField();
        Label loginNote = new Label("Testikäyttäjän tunnus: 'testaaja'"); //REMOVE
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
        Label newUserNameInstruction = new Label("Nimi:");
        TextField newUserUsername = new TextField();
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
        HBox topMainPane = new HBox(10);
        topMainPane.getChildren().addAll(welcomeLabel);
        HBox bottomMainPane = new HBox(10);
        Region menuSpacer = new Region();
        HBox.setHgrow(menuSpacer, Priority.ALWAYS);
        bottomMainPane.getChildren().addAll(addWorkoutButton, menuSpacer, logoutButton);
        mainPane.setTop(topMainPane);
        mainPane.setBottom(bottomMainPane);
        
        workoutNodes = new VBox(10);
        workoutNodes.setMaxWidth(280);
        workoutNodes.setMinWidth(280);
        redrawWorkouts();
        mainPaneScroller.setContent(workoutNodes);

        Scene mainScene = new Scene(mainPane, 300, 600);
        
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
            if (username.length() < 1) {
                newUserNote.setText("Tunnuksen on oltava ainakin yhden merkin pituinen!");
            } else if (name.length() < 1) {
                newUserNote.setText("Nimen on oltava ainakin yhden merkin pituinen!");
            } else if (username.length() > 15) {
                newUserNote.setText("Tunnus voi olla enintään 15 merkkiä!");
            } else if (name.length() > 20) {
                newUserNote.setText("Nimi voi olla enintään 20 merkkiä!");
            } else if (treeniAppService.newUser(username, name)) {
                newUserUsername.setText("");
                newUserName.setText("");
                loginNote.setText("Uusi käyttäjä '" + username + "' luotu!");
                loginNote.setTextFill(Color.BLACK);
                primaryStage.setScene(loginScene);
                newUserNote.setText("");
            } else {
                newUserNote.setText("Tunnus '" + username + "' on jo käytössä!");
            }
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
