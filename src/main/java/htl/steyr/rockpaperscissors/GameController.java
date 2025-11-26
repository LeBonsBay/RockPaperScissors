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

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public String button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void onClick(ActionEvent actionEvent) {
        Button buttonSource = (Button) actionEvent.getSource();

        String chosenButton = buttonSource.getText();
        setButton(chosenButton);

        GameLogic gameLogic = new GameLogic(getButton());

        gameLogic.gameStart();


    }
}
