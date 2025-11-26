package htl.steyr.rockpaperscissors;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    public Button scissorsButton;
    public Button paperButton;
    public Button rockButton;

    //we only want one object of gameLogic and not constantly create a new one
    private GameLogic gameLogic = new GameLogic(null);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void onClick(ActionEvent actionEvent) {
        Button buttonSource = (Button) actionEvent.getSource();

        String chosenButton = buttonSource.getText();

        //setting the String to work in the GameLogic class
        gameLogic.setFinalButton(chosenButton);
        gameLogic.gameStart();

    }
}
