package htl.steyr.rockpaperscissors;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GameLogic {

    public String finalButton;
    private boolean turn;
    private String botGesture;

    public int getPlayerHP() {
        return playerHP;
    }

    public void setPlayerHP(int playerHP) {
        this.playerHP = playerHP;
    }

    private int playerHP = 3;
    private int botHP = 3;

    public int getBotHP() {
        return botHP;
    }

    public void setBotHP(int botHP) {
        this.botHP = botHP;
    }


    public String getFinalButton() {
        return finalButton;
    }

    public void setFinalButton(String finalButton) {
        this.finalButton = finalButton;
    }

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

        botLogic();
        gamesLogic();
    }

    //action of the bot
    public void botLogic() {
        Random random = new Random();
        String[] hands = {"Rock", "Paper", "Scissors"};
        botGesture = hands[random.nextInt(0, hands.length)];

        setBotGesture(hands[random.nextInt(0, hands.length)]);
        System.out.println(botGesture);
        //System.out.println(botGesture);
    }

    public void gamesLogic() {

        //@ToDo
        //setting hp in a textField
        String botGesture = getBotGesture();
        String playerGesture = getFinalButton();

        switch (playerGesture) {
            case "Rock":

                if (botGesture.equals("Scissors")) {

                    setBotHP(getBotHP() - 1);
                    System.out.println(getBotHP() + "Bot");
                    //set in a textfield hp...
                } else if (botGesture.equals("Paper")) {
                    setPlayerHP(getPlayerHP() - 1);
                    System.out.println(getPlayerHP() + "Player");
                } else {
                    System.out.println("Draw!");
                }


                break;
            case "Paper":

                if (botGesture.equals("Scissors")) {

                    setPlayerHP(getPlayerHP() - 1);
                    System.out.println(getPlayerHP() + "Player");
                } else if (botGesture.equals("Rock")) {
                    setBotHP(getBotHP() - 1);
                    System.out.println(getBotHP() + "Bot");
                } else {
                    System.out.println("Draw!");
                }

                break;

            case "Scissors":

                if (botGesture.equals("Paper")) {

                    setBotHP(getBotHP() - 1);
                    System.out.println(getBotHP() + "Bot");
                } else if (botGesture.equals("Rock")) {
                    setPlayerHP(getPlayerHP() - 1);
                    System.out.println(getPlayerHP() + "Player");
                } else {
                    System.out.println("Draw!");
                }
                break;
        }


    }


}
