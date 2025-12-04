package htl.steyr.rockpaperscissors;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.control.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    public Button scissorsButton;
    public Button paperButton;
    public Button rockButton;
    public Button wellButton;
    public ProgressBar progressIndicator;
    public ListView<Integer> highScoreListView;
    public Slider volumeSlider;
    private MediaPlayer mediaPlayer;
    private Media clickSound;
    private Media bgMusic;


    //we only want one object of gameLogic and not constantly create a new one
    private GameLogic gameLogic = new GameLogic(null, null);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backGroundMusic();
    }

    //setting bg music with slider
    public void backGroundMusic() {

        String file = "bgMusic.mp3";
        String mp3File = "./src/main/resources/mp3/" + file;

        bgMusic = new Media(new File(mp3File).toURI().toString());

        mediaPlayer = new MediaPlayer(bgMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        volumeSlider.setMin(0.0);
        volumeSlider.setMax(1.0);

        // listener for volumeSlider
        mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty());

        mediaPlayer.play();
    }

    //onClick Sound for the buttons
    public void clickSound() {

        String file = "click.mp3";
        String soundFile = "./src/main/resources/mp3/" + file;

        clickSound = new Media(new File(soundFile).toURI().toString());


        mediaPlayer = new MediaPlayer(clickSound);

        mediaPlayer.setVolume(0.5);

        mediaPlayer.play();

    }


    public void onClick(ActionEvent actionEvent) {
        clickSound();

        Button buttonSource = (Button) actionEvent.getSource();

        String chosenButton = buttonSource.getId();

        //setting the String to work in the GameLogic class
        gameLogic.setButton(chosenButton);
        gameLogic.setView(highScoreListView);

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

                Platform.runLater(() -> {
                    gameLogic.gameStart();
                });
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