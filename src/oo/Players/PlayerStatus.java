package oo.Players;

public enum PlayerStatus {
    selected("Selected"),
    winner("Winner"),
    superWinner("Super Winner"),
    eliminated("Eliminated"),
    waiting("Waiting"),
    hasPlayed("Has played"),
    doNotPlay("Do not play"),
    inactive("Inactive");

    private final String meaning;

    PlayerStatus(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return meaning;
    }
}
