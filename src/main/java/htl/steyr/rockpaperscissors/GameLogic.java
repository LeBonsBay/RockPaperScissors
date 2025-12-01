package htl.steyr.rockpaperscissors;

import java.util.Random;

public class GameLogic {

    public String button;
    //private boolean turn;
    private String botGesture;

    public GameLogic(String button) {
        this.button = button;
    }

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
        botGesture = hands[random.nextInt(0, hands.length)];

        setBotGesture(hands[random.nextInt(0, hands.length)]);
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

                    setBotHP(getBotHP() - 1);
                    System.out.println(getBotHP() + "Bot");
                    //set in a textfield hp...
                } else if (botGesture.equals("Paper") || botGesture.equals("Well")) {
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
                } else if (botGesture.equals("Rock") || botGesture.equals("Well")) {
                    setBotHP(getBotHP() - 1);
                    System.out.println(getBotHP() + "Bot");
                } else if (botGesture.equals("Paper")) {
                    System.out.println("Draw!");
                }

                break;

            case "Scissors":

                if (botGesture.equals("Paper")) {

                    setBotHP(getBotHP() - 1);
                    System.out.println(getBotHP() + "Bot");
                } else if (botGesture.equals("Rock") || botGesture.equals("Well")) {
                    setPlayerHP(getPlayerHP() - 1);
                    System.out.println(getPlayerHP() + "Player");
                } else if (botGesture.equals("Scissors")) {
                    System.out.println("Draw!");
                }
                break;
            case "Well":

                if (botGesture.equals("Rock") || botGesture.equals("Scissors")) {

                    setBotHP(getBotHP() - 1);
                    System.out.println(getBotHP() + "Bot");
                } else if (botGesture.equals("Paper")) {
                    setPlayerHP(getPlayerHP() - 1);
                    System.out.println(getPlayerHP() + "Player");
                } else if (botGesture.equals("Well")) {
                    System.out.println("Draw!");
                }
                break;
        }
    }

}
