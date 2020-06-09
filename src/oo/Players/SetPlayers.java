package oo.Players;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class holding a set of players
 */
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
        listPlayers.addAll(players);
    }

    /**
     * Display every player in the set
     */
    public void display() {
        for (Player p : listPlayers) {
            p.display();
        }
    }

    /**
     * Select a random player among the set
     *
     * @return Player randomly selected
     */
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


    /**
     * Select playerS with a specific status
     *
     * @param ps Status
     * @return Player of status ps
     */
    public Vector<Player> selectPlayers(PlayerStatus ps) {
        return listPlayers.stream().filter(c -> c.getStatus() == ps).collect(Collectors.toCollection(Vector::new));
    }

    /**
     * Count player with a specific status
     *
     * @param ps Status
     * @return Number of players
     */
    public int countPlayers(PlayerStatus ps) {
        return (int) listPlayers.stream().filter(c -> c.getStatus() == ps).count();
    }

    /**
     * Select players that have been involved in the game at any time
     *
     * @return Players
     */
    public Vector<Player> getPlaying() {
        return listPlayers.stream().filter(player -> player.getStatus() != PlayerStatus.inactive).collect(Collectors.toCollection(Vector::new));
    }

    /**
     * List of worst players, based on score and timer
     *
     * @return Players
     */
    public List<Player> getWorstPlayers() {
        Supplier<Stream<Player>> inGamePlayers = () -> listPlayers
                .stream()
                .filter(player -> player.getStatus() == PlayerStatus.hasPlayed || player.getStatus() == PlayerStatus.waiting || player.getStatus() == PlayerStatus.selected);

        // Identification of minimal score
        int minScore = inGamePlayers.get()
                .min(Comparator.comparing(Player::getScore))
                .orElseThrow(NoSuchElementException::new)
                .getScore();

        // Identification of maximal timer
        long maxTimerWithMinScore = inGamePlayers.get()
                .filter(player -> player.getScore() == minScore)
                .max(Comparator.comparingLong(Player::getTimer))
                .orElseThrow(NoSuchElementException::new)
                .getTimer();

        // Returning players with min Score and max Timer
        return inGamePlayers.get()
                .filter(p -> p.getScore() == minScore && p.getTimer() == maxTimerWithMinScore)
                .collect(Collectors.toList());
    }
}
