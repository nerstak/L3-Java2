package oo.Game;

/**
 * Interface for Phase
 * Not useful, mainly useless and confusing, but asked
 */
public interface Phase {
    /**
     * Select 4 random players
     * Could be moved to the Game class
     */
    void selectPlayers();

    /**
     * Get the current phase
     *
     * @return Current phase
     */
    PhaseEnum getCurrentPhase();
}
