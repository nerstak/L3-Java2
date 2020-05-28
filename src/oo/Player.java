package oo;

/**
 * Class player
 */
public class Player implements Runnable {
    private final int number;
    private String name;
    private int score;
    private PlayerStatus status;
    private long timer;

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

    @Override
    public String toString() {
        return "[Name = " + name + "; Number = " + number + "; Score = " + score + "; Status = " + status.toString() + "]";
    }

    public void display() {
        System.out.println(this.toString());
    }

    @Override
    public void run() {
        timer = System.currentTimeMillis();
        while (status == PlayerStatus.selected) {
            long currentTimer = System.currentTimeMillis() - timer;
        }
    }


    // TODO: updateScore(gameState);
}
