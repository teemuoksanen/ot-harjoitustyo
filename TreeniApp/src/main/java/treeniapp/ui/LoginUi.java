
package treeniapp.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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

/**
 * Class handles the GUI of the application
 */
public class LoginUi extends Application {
    
    // Stages
    private static Stage pStage;
    
    // Scenes
    private Scene loginScene;
    private Scene newUserScene;
    private WorkoutsUi workoutsUi;
    private AddWorkoutUi addWorkoutUi;
    
    // Services
    private TreeniAppService treeniAppService;
    private UiService uiService;
    private UserDao userDao;
    private WorkoutDao workoutDao;
    private SportDao sportDao;
    
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
        
        uiService = new UiService(treeniAppService);
        addWorkoutUi = new AddWorkoutUi(treeniAppService, uiService, workoutsUi);
        workoutsUi = new WorkoutsUi(treeniAppService, uiService, addWorkoutUi);
        
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
        Label loginNote = new Label("Testitunnus: 'testaaja'");
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
        * ACTIONS
        */
        
        // Login
        
        loginButton.setOnAction((event) -> {
            String username = loginUsername.getText();
            if (treeniAppService.login(username)) {
                workoutsUi.workoutsWindow(pStage);
                pStage.hide();
                loginNote.setText("");
                loginUsername.setText("");
            } else if (username.length() < 1) {
                uiService.showError("Anna käyttäjätunnus!", "Anna käyttäjätunnus kirjautuaksesi sisään ja yritä uudelleen.");
            } else {
                uiService.showError("Käyttäjää '" + username + "' ei löytynyt!", "Tarkista antamasi käyttäjätunnus ja yritä uudelleen.");
            }
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
                uiService.showError("Vihreellinen tunnus!", "Tunnuksen on oltava 1-15 merkin pituinen. Tarkista tunnus ja yritä uudelleen.");
            } else if (name.length() < 1 || name.length() > 20) {
                uiService.showError("Vihreellinen nimi!", "Nimen on oltava 1-20 merkin pituinen. Tarkista nimi ja yritä uudelleen.");
            } else try {
                if (treeniAppService.newUser(username, name)) {
                    newUserUsername.setText("");
                    newUserName.setText("");
                    loginNote.setText("Uusi käyttäjä '" + username + "' luotu!");
                    loginNote.setTextFill(Color.BLACK);
                    pStage.setScene(loginScene);
                    newUserNote.setText("");
                } else {
                    uiService.showError("Tunnus '" + username + "' on jo käytössä!", "Valitse uusi käyttäjätunnus ja yritä uudelleen.");
                }
            } catch (SQLException e) {
                uiService.showError("Tietokantavirhe!", "Käyttäjän treenien haku tietokannasta epäonnistui. Ohjelma suljetaan.");
                Platform.exit();
            } catch (Exception e) {
                uiService.showError("Tuntematon virhe!", "Tapahtui tuntematon virhe. Ohjelma suljetaan.");
                Platform.exit();
            }
        });

        pStage.setScene(loginScene);
        pStage.setTitle("TreeniApp");
        pStage.show();
    }
    
    public static Stage getPrimaryStage() {
        return pStage;
    }

    private void setPrimaryStage(Stage pStage) {
        LoginUi.pStage = pStage;
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
