package oo;

public class Player {
    private int number;
    private String name;
    private int score;
    private PlayerStatus status;

    private static int globalPlayerNumber = 100;

    public Player() {
        this.name = "";
        this.score = 0;
        this.number = globalPlayerNumber;
        globalPlayerNumber += 10;
        this.status = PlayerStatus.waiting;
    }

    public void setName(String s) {
        this.name = s;
    }

    public void setStatus(PlayerStatus p) {
        this.status = p;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    // TODO: display() (srly?); updateScore(gameState);
}
