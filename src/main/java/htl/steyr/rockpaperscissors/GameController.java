package htl.steyr.rockpaperscissors;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;




public class GameController implements Initializable {

    public AnchorPane root;
    public Button scissorsButton;
    public Button paperButton;
    public Button rockButton;
    public Button midButton;
    public Button botButton;
    private ImageView BotDisplayView;

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

        botButton.setDisable(true);
        //music at start of the program
        backGroundMusic();
    }

    public void backGroundMusic(){
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void onClick(ActionEvent actionEvent) {
        Button buttonSource = (Button) actionEvent.getSource();

        String chosenButton = buttonSource.getId();
        System.out.println("button: " + chosenButton);
        transition(chosenButton); // animation for moving the button to the middle
        for(int i = 0; i < root.getChildren().size(); i++){
            if (!root.getChildren().get(i).getId().equals(chosenButton)){
                root.getChildren().get(i).setOnMouseClicked(null);
                root.getChildren().get(i).setVisible(false);
            }
        }
        String botGesture = gameLogic.getBotGesture();

        //setting the String of the button to work in the GameLogic class
        gameLogic.setButton(chosenButton);
        gameLogic.gameStart();
    }


        public void transition(String button) {
            TranslateTransition tt = new TranslateTransition();
            Button btn = (Button) root.lookup('#' + button);
            tt.setNode(btn);
            int id = 0;
            for(int i = 0; i < root.getChildren().size(); i++){
                if(root.getChildren().get(i).equals(btn)){
                    id = i;
                    System.out.println(root.getChildren().size());
                    System.out.println(id);
                    break;
                }
            }
            double x = root.getChildren().get(id).getLayoutX();
            double y = root.getChildren().get(id).getLayoutY();
            double deltaX = root.getChildren().get(3).getLayoutX() - x;
            double deltaY = root.getChildren().get(3).getLayoutY() - y;
            System.out.println("x: " + x + " y: " + y);

            tt.setToX(deltaX);
            tt.setToY(deltaY);
            tt.setInterpolator(Interpolator.LINEAR);
            tt.play();
        }

}
