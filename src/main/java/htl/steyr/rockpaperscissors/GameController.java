package htl.steyr.rockpaperscissors;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;




public class GameController implements Initializable {

    public Button scissorsButton;
    public Button paperButton;
    public Button rockButton;

    //we only want one object of gameLogic and not constantly create a new one
    //Note: null is just a placeholder!
    private GameLogic gameLogic = new GameLogic(null);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

        String chosenButton = buttonSource.getText();

        //setting the String of the button to work in the GameLogic class
        gameLogic.setButton(chosenButton);
        gameLogic.gameStart();
    }

}
