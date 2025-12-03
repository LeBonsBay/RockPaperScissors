package htl.steyr.rockpaperscissors;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.text.StyleConstants;
import javax.swing.text.html.StyleSheet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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

    //we only want one object of gameLogic and not constantly create a new one
    //Note: null is just a placeholder!
    private GameLogic gameLogic = new GameLogic(null);

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

    public void backGroundMusic() {
        //@ToDo
        //implement button for stopping music and implement infinite-loop (as long as the program runs)
        //sometimes the music stops while playing, idk yet why
        //Note: TinySound library seems to be good

        try {
            String mp3File = "./src/main/resources/mp3/bgMusic.mp3";
            Media bgMusic = new Media(new File(mp3File).toURI().toString());

            MediaPlayer mediaPlayer = new MediaPlayer(bgMusic);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.1);

            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
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


        //setting the String of the button to work in the GameLogic class
        gameLogic.setButton(chosenButton);
        gameLogic.gameStart();
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
