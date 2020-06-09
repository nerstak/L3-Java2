package oo.Game;

/**
 * Interface for Phase
 * Not used, as we prefer centralizing everything in class Game, supported by the enum PhaseEnum
 */
public interface Phase {
    void SelectPlayers();

    void gamePhase();
}
