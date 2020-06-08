package oo.Players;

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

    public void eliminateWorstPlayer () {
        Supplier<Stream<Player>> inGamePlayers = () -> listPlayers
                .stream()
                .filter(player -> player.getStatus() == PlayerStatus.hasPlayed || player.getStatus() == PlayerStatus.waiting);


        int minScore = inGamePlayers.get()
            .min(new Comparator<Player>() {
                @Override
                public int compare(Player p1, Player p2) {
                    return p2.getScore() - p1.getScore();
                }
            })
            .orElseThrow(NoSuchElementException::new)
            .getScore();

        long maxTimerWithMinScore = inGamePlayers.get()
            .max(new    Comparator<Player>() {
                @Override
                public int compare(Player p1, Player p2) {
                    return Long.compare(p2.getTimer(), p1.getTimer());
                }
            })
            .orElseThrow(NoSuchElementException::new)
            .getTimer();
        System.out.println(minScore);
        System.out.println(maxTimerWithMinScore);

        ArrayList<Player> possibleEliminatedPlayers = (ArrayList<Player>) inGamePlayers.get()
            .filter(p -> p.getScore() == minScore && p.getTimer() == maxTimerWithMinScore)
            .collect(Collectors.toList());

        if (possibleEliminatedPlayers.size() == 1) {
            possibleEliminatedPlayers.get(0).setStatus(PlayerStatus.eliminated);
        } else {
            // TODO : Dans le cas d’égalité, à la fois, des scores et timers, proposer jusqu’à trois questions supplémentaires
            // pour les départager. Après cela, faire une sélection aléatoire pour passer à l’étape suivante.

            // TEMP
            possibleEliminatedPlayers.get(0).setStatus(PlayerStatus.eliminated);
        }
    }
}
