package oo;

public enum PlayerStatus {
    selected("Selected"),
    winner("Winner"),
    superWinner("Super Winner"),
    eliminated("Eliminated"),
    waiting("Waiting");

    private String meaning;

    PlayerStatus(String meaning) {
        this.meaning = meaning;
    }


    @Override
    public String toString() {
        return meaning;
    }
}
