package oo.Players;

import Project.Main;

import java.io.Serializable;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetPlayers implements Serializable {
    Vector<Player> listPlayers;

    private static final int sizeSet = 20;

    public SetPlayers() {
        listPlayers = new Vector<>();
        for (int i = 0; i < sizeSet; i++) {
        	String playerName = Character.toString((char) (65 + i));
            listPlayers.add(new Player(playerName));
        }
    }

    public SetPlayers (List<Player> players) {
        listPlayers = new Vector<>();
        for (Player p: players) {
            listPlayers.add(p);
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

    /**
     * Select randomly one player with a certain status in the list
     *
     * @param ps the status we want the list to be filter by
     * @return a pointer to the Player selected. Null if no player with this status exist in the list
     */
    public Player selectPlayer(PlayerStatus ps) {
        Vector<Player> list = selectPlayers(ps);
        if (list.size() == 0)
            return null;

        int index = (int) (Math.random() * list.size());
        return list.get(index);
    }


    public Vector<Player> selectPlayers(PlayerStatus ps) {
        return listPlayers.stream().filter(c -> c.getStatus() == ps).collect(Collectors.toCollection(Vector::new));
    }

    public int countPlayers(PlayerStatus ps) {
        return (int) listPlayers.stream().filter(c -> c.getStatus() == ps).count();
    }

    public List<Player> getWorstPlayers () {
        Supplier<Stream<Player>> inGamePlayers = () -> listPlayers
                .stream()
                .filter(player -> player.getStatus() == PlayerStatus.hasPlayed || player.getStatus() == PlayerStatus.waiting);

        int minScore = inGamePlayers.get()
                .min((p1, p2) -> p2.getScore() - p1.getScore())
                .orElseThrow(NoSuchElementException::new)
                .getScore();

        long maxTimerWithMinScore = inGamePlayers.get()
                .filter(player -> player.getScore() == minScore)
                .max((p1, p2) -> Long.compare(p2.getTimer(), p1.getTimer()))
                .orElseThrow(NoSuchElementException::new)
                .getTimer();

        List<Player> worstPlayers = inGamePlayers.get()
                .filter(p -> p.getScore() == minScore && p.getTimer() == maxTimerWithMinScore)
                .collect(Collectors.toList());

        return worstPlayers;
    }
}
