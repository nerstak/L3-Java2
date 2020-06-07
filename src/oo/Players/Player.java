package oo.Players;

import oo.Game.PhaseEnum;

/**
 * Class player
 */
public class Player implements Runnable {
    private final int number;
    private String name;
    private int score;
    private PlayerStatus status;
    private long durationTimer;
    private long currentTimer;
    private Thread thread;

    private volatile boolean stop;

    private static int globalPlayerNumber = 100;

    public Player() {
        this.name = "";
        this.score = 0;
        this.number = globalPlayerNumber;
        globalPlayerNumber += 10;
        this.status = PlayerStatus.inactive;
    }
    
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.number = globalPlayerNumber;
        globalPlayerNumber += 10;
        this.status = PlayerStatus.inactive;
    }

    public void setName(String s) {
        this.name = s;
    }

    public void setStatus(PlayerStatus p) {
        status = p;
        if (status == PlayerStatus.selected) {
            thread = new Thread(this);
            thread.setName(name);
            thread.setDaemon(true);
            thread.start();
        } else {
            stop();
        }
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
        stop = false;
        long timer = System.currentTimeMillis();
        while (!stop) {
            currentTimer = System.currentTimeMillis() - timer;
        }

        durationTimer = durationTimer + currentTimer;
        System.out.println(name + " " + durationTimer);
        currentTimer = 0;
        thread = null;
    }

    /**
     * Stop a thread
     */
    private void stop() {
        stop = true;
    }

    public long getTimer() {
        return durationTimer + currentTimer;
    }

    public void updateScore(PhaseEnum phase) {
        switch (phase) {
            case Phase1: {
                score += 2;
                break;
            }
            case Phase2: {
                score += 3;
                break;
            }
            case Phase3: {
                score += 5;
                break;
            }
        }
    }
}
