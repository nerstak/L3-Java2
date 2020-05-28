package oo;

import java.util.Vector;

public class SetPlayers {
    Vector<Player> listPlayers;


    private static final int sizeSet = 20;

    public SetPlayers() {
        listPlayers = new Vector<>();
        for (int i = 0; i < sizeSet; i++) {
            listPlayers.add(new Player());
        }
    }

    /**
     * Display every player in the set
     */
    public void display() {
        for (Player p : listPlayers) {
            p.display();
        }
    }

    public Player selectPlayer() {
        int n = (int) (Math.random() * sizeSet);
        return listPlayers.get(n);
    }
}
