package htl.steyr.rockpaperscissors;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    public Button scissorsButton;
    public Button paperButton;
    public Button rockButton;
    public Button wellButton;
    public ProgressBar progressIndicator;

    //we only want one object of gameLogic and not constantly create a new one
    private GameLogic gameLogic = new GameLogic(null);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}


    public void onClick(ActionEvent actionEvent) {
        Button buttonSource = (Button) actionEvent.getSource();

        String chosenButton = buttonSource.getText();

        //setting the String to work in the GameLogic class
        gameLogic.setButton(chosenButton);

        cpuWaitTime(progressIndicator);
    }

    public void cpuWaitTime(ProgressBar progressIndicator) {

        // Create a background task (JavaFX Task runs on a separate thread)
        // Explanation: A JavaFX task is used to run code in the background
        // without freezing the UI (sorta reactivity)
        final Task<Void> task = new Task<Void>() {

            // Number of steps the progress bar will animate through
            final int N_ITERATIONS = 100;

            @Override
            protected Void call() throws Exception {

                // Loop to simulate CPU thinking time
                for (int i = 0; i < N_ITERATIONS; i++) {

                    // Update the progress value (i goes from 0 → 99)
                    updateProgress(i + 1, N_ITERATIONS);
                    // sleep is used to simulate doing some work which takes some time....
                    Thread.sleep(10);
                }

                // After the waiting animation finishes, start the game logic
                gameLogic.gameStart();
                return null;
            }
        };


        // Bind the progress bar to the task's progress
        // (progress bar updates automatically)
        progressIndicator.progressProperty().bind(
                task.progressProperty()
        );

        // Run the task on its own background thread
        final Thread thread = new Thread(task, "task-thread");
        thread.setDaemon(true); // Will not block application shutdown
        thread.start();         // Start the waiting animation + game start
    }
}