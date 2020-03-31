
package treeniapp.ui;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import treeniapp.dao.SQLUserDao;
import treeniapp.domain.TreeniAppService;

public class TreeniUi extends Application {
    
    private TreeniAppService treeniAppService;
    
    @Override
    public void init() throws Exception {
        
        // Load properties
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        String databaseDB = properties.getProperty("databaseDB");
        String usernameDB = properties.getProperty("usernameDB");
        String passwordDB = properties.getProperty("passwordDB");
        
        // Start services
        SQLUserDao userDao = new SQLUserDao();
        treeniAppService = new TreeniAppService(userDao);
        
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        // Login Screen
        
        Label loginInstruction = new Label("Anna tunnuksesi ja paina 'Kirjaudu'");
        Button loginButton = new Button("Kirjaudu");
        TextField loginUsername = new TextField();
        Label loginNote = new Label("");

        GridPane loginPane = new GridPane();
        loginPane.add(loginInstruction, 0, 0);
        loginPane.add(loginUsername, 0, 1);
        loginPane.add(loginButton, 0, 2);
        loginPane.add(loginNote, 0, 3);
        loginPane.setPrefSize(300, 300);
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setVgap(10);
        loginPane.setHgap(10);
        loginPane.setPadding(new Insets(20, 20, 20, 20));

        Scene loginScene = new Scene(loginPane);
        
        // Main Screen

        Label welcomeLabel = new Label("Tervetuloa!");
        Button logoutButton = new Button("Kirjaudu ulos");

        GridPane mainPane = new GridPane();
        mainPane.setPrefSize(300, 600);
        mainPane.add(welcomeLabel, 0, 0);
        mainPane.add(logoutButton, 0, 1);
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(10);
        mainPane.setHgap(10);
        mainPane.setPadding(new Insets(20, 20, 20, 20));

        Scene mainScene = new Scene(mainPane);
        
        // Scene changes
        
        loginButton.setOnAction((event) -> {
            String username = loginUsername.getText();
            if (treeniAppService.login(username)) {
                loginNote.setText("");
                // REFRESH WORKOUTS
                primaryStage.setScene(mainScene);
                loginUsername.setText("");
            } else {
                loginNote.setText("Käyttäjää '" + username + "' ei löytynyt!");
                loginNote.setTextFill(Color.RED);
            }
        });
        
        logoutButton.setOnAction((event) -> {
            loginNote.setTextFill(Color.BLACK);
            loginNote.setText("Uloskirjautuminen onnistui!");
            primaryStage.setScene(loginScene);
        });

        primaryStage.setScene(loginScene);
        primaryStage.setTitle("TreeniApp");
        primaryStage.show();
    }

    @Override
    public void stop() {
      // tee lopetustoimenpiteet täällä
      System.out.println("TreeniApp closed");
    }    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
