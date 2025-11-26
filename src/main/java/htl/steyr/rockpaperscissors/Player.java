package htl.steyr.rockpaperscissors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Player {

    //@ToDo
    //change name of variables later...
    private String handSign;

    public String getHandSign() {
        return handSign;
    }

    public void setHandSign(String handSign) {
        this.handSign = handSign;
    }

    public Button scissorsButton;
    public Button paperButton;
    public Button rockButton;

    @FXML
    private void onClick(ActionEvent event){
        String button = event.getSource().toString();
        setHandSign(button);
    }
}
