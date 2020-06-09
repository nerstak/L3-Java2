package oo.Players;

import oo.Game.PhaseEnum;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Class player
 */
public class Player implements Runnable,Serializable, Comparable<Player> {
    private final int number;
    private String name;
    private int score;
    private PlayerStatus status;
    private long durationTimer;
    private long currentTimer;
    private transient Thread thread;

    private volatile boolean stop;

    private static int globalPlayerNumber = 100;

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

    public void setDurationTimer(long dt) {
        this.durationTimer = dt;
    }

    public void setScore(int s) {
        this.score = s;
    }

    /**
     * Change status of a player
     *
     * @param p Status to update to
     */
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

    /**
     * Update the score
     *
     * @param phase Current phase
     */
    public void updateScore(PhaseEnum phase) {
        switch (phase) {
            case Phase1 -> score += 2;
            case Phase2 -> score += 3;
            case Phase3 -> score += 5;
        }
    }

    public static Comparator<Player> PlayerRanking = new Comparator<>() {
        @Override
        public int compare(Player o1, Player o2) {
            return o1.compareTo(o2);
        }
    };

    public static Comparator<Player> PlayerStatusComparator = new Comparator<>() {
        @Override
        public int compare(Player o1, Player o2) {
            return o1.status.compareTo(o2.status);
        }
    };

    @Override
    public int compareTo(Player o) {
        if (this.score != o.score) {
            return Integer.compare(this.score, o.score);
        } else {
            return Long.compare(this.durationTimer, o.durationTimer);
        }
    }
}
