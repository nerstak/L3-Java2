package oo.Players;

/**
 * Enumeration of the different status
 */
public enum PlayerStatus {
    superWinner("Super Winner"),
    winner("Winner"),
    selected("Selected"),
    waiting("Waiting"),
    hasPlayed("Has played"),
    eliminated("Eliminated"),
    inactive("Inactive"); // Used for the non-playing players

    private final String meaning;

    PlayerStatus(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return meaning;
    }
}
