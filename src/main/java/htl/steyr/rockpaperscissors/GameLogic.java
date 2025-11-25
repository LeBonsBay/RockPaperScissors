package htl.steyr.rockpaperscissors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameLogic extends Player {

    public Button scissorsButton;
    public Button paperButton;
    public Button rockButton;

    @FXML
    private void onClick(ActionEvent event){
        String button = event.getSource().toString();
        System.out.println(button);




    }


}
