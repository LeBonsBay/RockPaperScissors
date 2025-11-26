package htl.steyr.rockpaperscissors;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GameLogic {

    public String finalButton;
    private boolean turn;
    private String botGesture;


    public String getBotGesture() {
        return botGesture;
    }

    public void setBotGesture(String botGesture) {
        this.botGesture = botGesture;
    }






    public GameLogic(String finalButton) {
        this.finalButton = finalButton;
    }

    public void gameStart() {

        if (turn) {
            playerLogic();
            turn = false;
        } else {
            botLogic();
        }

    }

    public void botLogic() {
        Random random = new Random();
        String[] hands = {"Rock", "Paper", "Scissors"};

        for (int i = 0; i < hands.length; ++i) {
            botGesture = hands[random.nextInt(0, hands.length)];
            setBotGesture(hands[random.nextInt(0, hands.length)]);
        }
    }

    public String playerLogic() {
        GameController gameController = new GameController();

        //String button = gameController.getButton();
        switch (finalButton) {

            case "Rock":

                break;

            case "Paper":

                break;

            case "Scissors":

                break;

        }
    }


}
