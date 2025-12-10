package htl.steyr.rockpaperscissors;

import javafx.scene.control.ListView;
import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

    private String button;
    //private boolean turn;
    private String botGesture;
    private int highScore = 0;
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();


    private int botChoice;


    public int getBotChoice() {
        return botChoice;
    }

    public ListView<Integer> getView() {
        return view;
    }

    public void setView(ListView<Integer> view) {
        this.view = view;
    }


    private ListView<Integer> view;

    public GameLogic(String button, ListView<Integer> view) {
        this.button = button;
        this.view = view;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String finalButton) {
        this.button = finalButton;
    }

    public String getBotGesture() {
        return botGesture;
    }

    public void setBotGesture(String botGesture) {
        this.botGesture = botGesture;
    }

    public void gameStart() {

        botLogic();
        gamesLogic();
    }

    //action of the bot
    public void botLogic() {
        Random random = new Random();
        String[] hands = {"Rock", "Paper", "Scissors", "Well"};
        int rand = random.nextInt(0, hands.length);
        botGesture = hands[rand];
        botChoice = rand;
        System.out.println(botGesture);
        //System.out.println(botGesture);
    }

    public void gamesLogic() {

        //@ToDo
        //setting hp in a textField
        String botGesture = getBotGesture();
        String playerGesture = getButton();

        switch (playerGesture) {
            case "Rock":

                if (botGesture.equals("Scissors")) {


                    setHighScore(getHighScore() + 100);

                    System.out.println("HighScore:" + getHighScore());
                } else if (botGesture.equals("Paper") || botGesture.equals("Well")) {
                    //implement log
                    arrayList.add(getHighScore());
                    view.getItems().add(getHighScore());
                    setHighScore(0);
                    System.out.println("highscore reset:" + getHighScore());
                } else {
                    System.out.println("Draw!");
                }


                break;
            case "Paper":

                if (botGesture.equals("Scissors")) {


                    //implement log
                    arrayList.add(getHighScore());
                    view.getItems().add(getHighScore());
                    setHighScore(0);
                    System.out.println("highscore reset:" + getHighScore());
                } else if (botGesture.equals("Rock") || botGesture.equals("Well")) {
                    setHighScore(getHighScore() + 100);
                    System.out.println("HighScore:" + getHighScore());
                } else if (botGesture.equals("Paper")) {
                    System.out.println("Draw!");
                }

                break;

            case "Scissors":

                if (botGesture.equals("Paper")) {
                    setHighScore(getHighScore() + 100);

                    System.out.println("HighScore:" + getHighScore());
                } else if (botGesture.equals("Rock") || botGesture.equals("Well")) {
                    arrayList.add(getHighScore());
                    view.getItems().add(getHighScore());

                    setHighScore(0);
                    System.out.println(getHighScore());
                } else if (botGesture.equals("Scissors")) {
                    System.out.println("Draw!");
                }
                break;
            case "Well":

                if (botGesture.equals("Rock") || botGesture.equals("Scissors")) {

                    setHighScore(getHighScore() + 100);
                    System.out.println("HighScore:" + getHighScore());
                } else if (botGesture.equals("Paper")) {
                    arrayList.add(getHighScore());
                    view.getItems().add(getHighScore());
                    setHighScore(0);
                    System.out.println(getHighScore());
                } else if (botGesture.equals("Well")) {
                    System.out.println("Draw!");
                }
                break;
        }
    }
}
