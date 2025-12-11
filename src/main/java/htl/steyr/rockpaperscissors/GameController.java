package htl.steyr.rockpaperscissors;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.geometry.Point2D;
import javafx.scene.control.Slider;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.control.ListView;
import javafx.util.Duration;


import javax.swing.text.StyleConstants;
import javax.swing.text.html.StyleSheet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class GameController implements Initializable {

    public AnchorPane root;

    public Button scissorsButton;
    public Button paperButton;
    public Button rockButton;
    public Button wellButton;
    public Button midButton;

    public ImageView botDisplayView;
    private MediaPlayer mediaPlayer;
    private Media clickSound;
    private Media bgMusic;

    private Map<Node, Point2D> originalPositions = new HashMap<>();

    private final Image[] imgs = new Image[4];
    public Slider volumeSlider;

    private String handSign;

    public void setHandSign(String handSign) {
        this.handSign = handSign;
    }

    public String getHandSign() {
        return handSign;
    }

    public ProgressBar progressIndicator;
    public ListView<Integer> highScoreListView;


    //we only want one object of gameLogic and not constantly create a new one
    private GameLogic gameLogic = new GameLogic(null, null);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        midButton.setVisible(false);
        botDisplayView.setFocusTraversable(false);

        gameLogic.setView(highScoreListView);

        progressIndicator.setVisible(false);
        for (Node n : root.getChildren()) {
            originalPositions.put(n, new Point2D(n.getLayoutX(), n.getLayoutY()));
        }

        try {
            imgs[0] = new Image(getClass().getResourceAsStream("/images/rock.png"));
            imgs[1] = new Image(getClass().getResourceAsStream("/images/paper.png"));
            imgs[2] = new Image(getClass().getResourceAsStream("/images/scissors.png"));
            imgs[3] = new Image(getClass().getResourceAsStream("/images/well.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //music at start of the program
        backGroundMusic();
    }

    public void backGroundMusic() {

        String file = "bgMusic.mp3";
        String mp3File = "./src/main/resources/mp3/" + file;

        bgMusic = new Media(new File(mp3File).toURI().toString());

        mediaPlayer = new MediaPlayer(bgMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        volumeSlider.setMin(0.0);
        volumeSlider.setMax(1.0);

        // listener for volumeSlider
        //here the media value gets bound to the volumeSliders
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
        System.out.println("Player chose: " + chosenButton); // <-- NEU HINZUFÜGEN
        transition(chosenButton);
        cpuWaitTime(progressIndicator);
        for (int i = 0; i < (root.getChildren().size()); i++) {
            if (!root.getChildren().get(i).getId().equals(chosenButton) && !root.getChildren().get(i).getId().equals(highScoreListView.getId())) {
                root.getChildren().get(i).setVisible(false);
            }
        }
        buttonSource.setMouseTransparent(true);
        progressIndicator.setVisible(true);
        botDisplayView.setVisible(true);
        buttonSource.setOnMouseClicked(null);

        //chosenButton = buttonSource.getId();
        // {"Rock", "Paper", "Scissors"} are the values


        //setting the String to work in the GameLogic class
        gameLogic.setButton(chosenButton);
        gameLogic.setView(highScoreListView);

        rollingImages(gameLogic.getBotChoice());
        System.out.println(gameLogic.getBotChoice());

        //setting the String to work in the GameLogic class

        resetLater(3);
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
                    // sleep is used to simulate doings some work which takes some time....
                    Thread.sleep(10);
                }
                // After the waiting animation finishes, start the game logic

                Platform.runLater(() -> {
                    gameLogic.gameStart();
                    rollingImages(gameLogic.getBotChoice());
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
        double deltaX = root.getChildren().get(5).getLayoutX() - x;
        double deltaY = root.getChildren().get(5).getLayoutY() - y;

        tt.setRate(3);
        tt.setToX(deltaX);
        tt.setToY(deltaY);
        tt.setInterpolator(Interpolator.LINEAR);
        tt.play();
    }

    public void rollingImages(int botDecision) {

        final long ONE_SECOND = 1_000_000_000L; // 1 second in nanoseconds

        final long[] startTime = {-1};
        final long[] lastFrame = {0};

        // SPEED PARAMETERS
        long startDelay = 20_000_000L;   // 20ms → very fast
        long endDelay = 250_000_000L;  // 250ms → slow at the end

        final double slowdownSteps = 1.0 / imgs.length * 12.0;
        final double[] progress = {0};

        final long[] currentDelay = {startDelay};

        final int[] currentIndex = {0};

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // initialize timing
                if (startTime[0] < 0) {
                    startTime[0] = now;
                    lastFrame[0] = now;
                    return;
                }

                long elapsed = now - startTime[0];

                // If 1 second finished → SET FINAL IMAGE + STOP
                if (elapsed >= ONE_SECOND) {
                    botDisplayView.setImage(imgs[botDecision]);
                    this.stop();
                    return;
                }

                // Only change image if current delay passed
                if (now - lastFrame[0] >= currentDelay[0]) {

                    // cycle through images
                    currentIndex[0] = (currentIndex[0] + 1) % imgs.length;
                    botDisplayView.setImage(imgs[currentIndex[0]]);

                    lastFrame[0] = now;

                    // Compute progress from 0 → 1 (0% → 100% of animation time)
                    progress[0] = Math.min(1.0, (double) elapsed / ONE_SECOND);

                    // Ease-out slowdown curve (fast → slow)
                    double eased = 1.0 - Math.pow(1.0 - progress[0], 3); // smooth cubic

                    // Interpolate delay between fast and slow
                    currentDelay[0] = (long) (startDelay + (endDelay - startDelay) * eased);
                }
            }
        };

        timer.start();
        System.out.println("##" + botDecision);
    }

    public void reset() {
        for (Node node : originalPositions.keySet()) {
            Point2D point = originalPositions.get(node);

            node.setLayoutX(point.getX());
            node.setLayoutY(point.getY());
            node.setTranslateX(0);
            node.setTranslateY(0);
        }

        for (int i = 0; i < root.getChildren().size(); i++) {
            if (!root.getChildren().get(i).getId().equals(highScoreListView.getId())) {
                root.getChildren().get(i).setVisible(true);
                root.getChildren().get(i).setMouseTransparent(false);
            }
        }
        progressIndicator.setVisible(false);
        botDisplayView.setVisible(false);
    }

    public void resetLater(double seconds) {
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(event -> reset());
        pause.play();
    }
}