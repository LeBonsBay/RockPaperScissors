package htl.steyr.rockpaperscissors;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.control.ListView;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.StyleSheet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class GameController implements Initializable {

    public AnchorPane root;
    public Button scissorsButton;
    public Button paperButton;
    public Button rockButton;
    public Button midButton;
    public ImageView botDisplayView;

    private final Image[] imgs = new Image[3];

    private String handSign;

    public void setHandSign(String handSign) {
        this.handSign = handSign;
    }

    public String getHandSign() {
        return handSign;
    }
    public Button wellButton;
    public ProgressBar progressIndicator;
    public ListView<Integer> highScoreListView;


    //we only want one object of gameLogic and not constantly create a new one
    private GameLogic gameLogic = new GameLogic(null,null);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        midButton.setVisible(false);
        botDisplayView.setFocusTraversable(false);

        try {
            imgs[0] = new Image(getClass().getResourceAsStream("/images/rock.png"));
            imgs[1] = new Image(getClass().getResourceAsStream("/images/paper.png"));
            imgs[2] = new Image(getClass().getResourceAsStream("/images/scissors.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //music at start of the program
        backGroundMusic();
    }

    public void backGroundMusic(){
        //@ToDo
        //implement button for stopping music and implement infinite-loop (as long as the program runs)
        //sometimes the music stops while playing, idk yet why
        //Note: TinySound library seems to be good



        String mp3File = "./src/main/resources/mp3/bgMusic.mp3";
        Media bgMusic = new Media(new File(mp3File).toURI().toString());

        MediaPlayer mediaPlayer = new MediaPlayer(bgMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        mediaPlayer.setVolume(0.1);

        mediaPlayer.play();


    }


    public void onClick(ActionEvent actionEvent) {
        Button buttonSource = (Button) actionEvent.getSource();
        String chosenButton = buttonSource.getId();
        transition(chosenButton); // animation for moving the button to the middle
        for (int i = 0; i < (root.getChildren().size()); i++) {
            if (!root.getChildren().get(i).getId().equals(chosenButton) && !root.getChildren().get(i).getId().equals(botDisplayView.getId())) {
                root.getChildren().get(i).setOnMouseClicked(null);
                root.getChildren().get(i).setVisible(false);
            }
        }
        buttonSource.setOnMouseClicked(null);
        String botGesture = gameLogic.getBotGesture();
        int botChoice = gameLogic.getBotChoice();
        rollingImages(botChoice);
        System.out.println(botGesture);
        System.out.println(gameLogic.getBotGesture());


        // {"Rock", "Paper", "Scissors"} are the values


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

                Platform.runLater(() ->{
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
    public void transition(String button) {
        TranslateTransition tt = new TranslateTransition();
        Button btn = (Button) root.lookup('#' + button);
        tt.setNode(btn);
        int id = 0;
        for (int i = 0; i < root.getChildren().size(); i++) {
            if (root.getChildren().get(i).equals(btn)) {
                id = i;
                break;
            }
        }
        double x = root.getChildren().get(id).getLayoutX();
        double y = root.getChildren().get(id).getLayoutY();
        double deltaX = root.getChildren().get(4).getLayoutX() - x;
        double deltaY = root.getChildren().get(4).getLayoutY() - y;

        tt.setToX(deltaX);
        tt.setToY(deltaY);
        tt.setInterpolator(Interpolator.LINEAR);
        tt.play();
    }

    public void rollingImages(int botDesition) {

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        final int[] delay = {0};
        final int maxDelay = 70;

        Runnable task = new Runnable() {
            @Override
            public void run() {

                // update UI on FX thread
                Platform.runLater(() -> {
                    int rand = (int) (Math.random() * GameController.this.imgs.length);
                    botDisplayView.setImage(GameController.this.imgs[rand]);
                });


                // slow down gradually
                delay[0] += 2; // increase delay → slower

                // if delay too large, stop
                if (delay[0] > maxDelay) {
                    exec.shutdown();

                    // final face
                    Platform.runLater(() -> {
                        botDisplayView.setImage(imgs[botDesition]);
                        System.out.println(botDesition);
                    });
                } else {
                    // reschedule with new slower delay
                    exec.schedule(this, delay[0], TimeUnit.MILLISECONDS);
                }
            }
        };

        // start immediately
        exec.schedule(task, 0, TimeUnit.MILLISECONDS);
    }

}

