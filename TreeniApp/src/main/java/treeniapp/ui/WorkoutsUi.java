
package treeniapp.ui;

import java.sql.SQLException;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import treeniapp.domain.TreeniAppService;
import treeniapp.domain.Workout;

/**
 * Class handles the GUI for workout list and workout details
 */
public class WorkoutsUi {
    
    private TreeniAppService treeniAppService;
    private UiService uiService;
    private AddWorkoutUi addWorkoutUi;
    
    private Stage workoutsWindow;
    private Scene workoutsScene;
    private VBox workoutNodes;
    private Label workoutsTotal;
    private int yearCounter;
    
    public WorkoutsUi(TreeniAppService treeniAppService, UiService uiService, AddWorkoutUi addWorkoutUi) {
        this.treeniAppService = treeniAppService;
        this.uiService = uiService;
        this.addWorkoutUi = addWorkoutUi;
    }
    
    /**
    * Method to create a workout node for the workout list in the application's main view.
    * 
    * @param    workout   The <code>Workout</code> object for which the node is created.
    * 
    * @return <code>Node</code> containing the workout information.
    */
    public Node createWorkoutNode(Workout workout) {
        
        HBox workoutBox = new HBox(5);
        workoutBox.setAlignment(Pos.CENTER_LEFT);
        Label workoutDate = new Label(workout.getDayMonth());
        Image workoutIcon = uiService.getSportsIcon(workout.getSport());
        ImageView workoutIconView = new ImageView(workoutIcon);
        Label workoutSport = new Label(treeniAppService.getSportById(workout.getSport().getId()).getName());
        Label workoutDuration = new Label(workout.getDurationFormatted());
        Image moreIcon = new Image(getClass().getResourceAsStream("/more.png"));
        ImageView workoutMoreIcon = new ImageView(moreIcon);
        workoutMoreIcon.setFitHeight(15);
        workoutMoreIcon.setFitWidth(15);
        Button workoutMoreButton = new Button();
        workoutMoreButton.setGraphic(workoutMoreIcon);
        
        Scene viewWorkoutScene = viewWorkout(workout);
        
        workoutMoreButton.setOnMouseClicked((event) -> {
            workoutsWindow.setScene(viewWorkoutScene);
        });
        
        workoutDate.setMaxWidth(45);
        workoutDate.setMinWidth(45);
        workoutIconView.setFitHeight(20);
        workoutIconView.setFitWidth(20);
        workoutSport.setMaxWidth(120);
        workoutSport.setMinWidth(110);
        workoutDuration.setMaxWidth(40);
        workoutDuration.setMinWidth(40);
        workoutMoreButton.setMaxWidth(25);
        workoutMoreButton.setMinWidth(25);
        
        workoutBox.getChildren().addAll(workoutDate, workoutIconView, workoutSport, workoutDuration, workoutMoreButton);
        return workoutBox;
    }
    
    /**
    * Method to redraw <code>User</code>'s all workout nodes in the application's main view.
    */
    public void redrawWorkouts() {
        workoutNodes.getChildren().clear();
        yearCounter = 0;
        
        if (treeniAppService.getLoggedInUser() != null) {
            try {
                List<Workout> workouts = treeniAppService.getWorkouts(treeniAppService.getLoggedInUser());
                workouts.forEach(workout->{
        
                    int workoutYear = Integer.valueOf(workout.getYear());
                    
                    if (workoutYear != yearCounter) {
                        Label yearLabel = new Label("Vuosi " + workoutYear);
                        yearLabel.setFont(new Font(15.0));
                        workoutNodes.getChildren().addAll(yearLabel);
                        yearCounter = workoutYear;
                    }
                    
                    workoutNodes.getChildren().add(createWorkoutNode(workout));
                });
                workoutsTotal.setText(treeniAppService.getTotalTimeFormatted(treeniAppService.getLoggedInUser()));
            } catch (SQLException e) {
                uiService.showError("Käyttäjän treenien haku tietokannasta epäonnistui. Ohjelma suljetaan.");
                Platform.exit();
            } catch (Exception e) {
                uiService.showError("Tapahtui tuntematon virhe. Ohjelma suljetaan.");
                Platform.exit();
            }
        }
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
        ImageView viewWorkoutSportIconView = new ImageView(uiService.getSportsIcon(workout.getSport()));
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
            workoutsWindow.setScene(workoutsScene);
        });
        
        // Delete Workout
        deleteWorkoutButton.setOnAction((event) -> {
            boolean confirmRemove = uiService.showConfirmation("Haluatko varmasti poistaa treenin?");
            if (confirmRemove) {
                if (treeniAppService.deleteWorkout(workout)) {
                    redrawWorkouts();
                    workoutsWindow.setScene(workoutsScene);
                } else {
                    uiService.showError("Treenin poistaminen ei onnistunut!");
                }
            }
        });

        return new Scene(viewWorkoutPane, 330, 330);
    }
    
    /**
    * Method to view all <code>Workout</code>s of the user who is logged in.
    * 
    * @param    pStage   The login window (as <code>Stage</code> object) injected.
    */
    public void workoutsWindow(Stage pStage) {
        // Top
        HBox topMainPane = new HBox();
        Label welcomeLabel = new Label(treeniAppService.getLoggedInUser().getName());
        welcomeLabel.setFont(new Font(30.0));
        
        topMainPane.setPadding(new Insets(0, 0, 10, 0));
        topMainPane.setAlignment(Pos.CENTER);
        topMainPane.getChildren().addAll(welcomeLabel);
        
        // Workouts
        ScrollPane workoutsMainPane = new ScrollPane();
        workoutNodes = new VBox(15);
        workoutNodes.setPadding(new Insets(10, 10, 10, 10));
        workoutNodes.setMaxWidth(310);
        workoutNodes.setMinWidth(291);
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
        workoutsScene = new Scene(mainPane, 330, 600);
        redrawWorkouts();
        
        workoutsWindow = new Stage();
        workoutsWindow.setTitle("TreeniApp");
        workoutsWindow.setScene(workoutsScene);
        workoutsWindow.show();
        
        
        /**
        * ACTIONS
        */
        
        // Open Add Workout Window
        addWorkoutButton.setOnAction((event) -> {
            Stage addWorkoutWindow = new Stage();
            addWorkoutWindow.setTitle("Lisää treeni - TreeniApp");
            addWorkoutWindow.setScene(addWorkoutUi.addWorkoutScene(addWorkoutWindow));
            addWorkoutWindow.showAndWait();
            redrawWorkouts();
        });
        
        // Logout
        logoutButton.setOnAction((event) -> {
            pStage.show();
            workoutsWindow.close();
        });

    }
    
}
