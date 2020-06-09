package oo.Game;

import Project.Main;
import oo.Players.Player;
import oo.Players.PlayerStatus;
import oo.Players.SetPlayers;
import oo.Questions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game class
 * It is basically the logic controller of the whole game (not program)
 */
public class Game implements Serializable, Phase {
    private final static Themes allThemes = initializeAllThemes();
    boolean eliminationDone;
    private transient Question<?> selectedQuestion;

    /**
     * nextThemes is the list of themes that will be used for the current phase
     * Is changed at the beginning of each phase
     */
    private Themes nextThemes;
    private SetPlayers listPlayers;
    private PhaseEnum currentPhase;
    private PhaseEnum phaseBeforeDeciding;
    private Integer turnLeftBeforeDeciding;
    private Integer scoreBeforeDeciding;
    private Long timerBeforeDeciding;

    private List<Player> worstPlayers;

    public Game() {
        listPlayers = new SetPlayers();
        nextThemes = new Themes();
        selectedQuestion = null;
        currentPhase = null;
        eliminationDone = false;

        phaseBeforeDeciding = null;
        turnLeftBeforeDeciding = null;
        scoreBeforeDeciding = null;
        timerBeforeDeciding = null;
        worstPlayers = null;
    }

    /**
     * Create a new Themes instance then call the method readThemes on it
     *
     * @return the new Themes instance
     */
    public static Themes initializeAllThemes() {
        Themes allThemes = new Themes();
        allThemes.readThemes();
        return allThemes;
    }

    /**
     * Get the list of players
     *
     * @return SetPlayers
     */
    public SetPlayers getListPlayers() {
        return listPlayers;
    }

    /**
     * Get the current player
     *
     * @return Current player or null
     */
    public Player getCurrentPlayer() {
        return listPlayers.selectPlayer(PlayerStatus.selected);
    }

    @Override
    public PhaseEnum getCurrentPhase() {
        return currentPhase;
    }

    public Question<?> getSelectedQuestion() {
        return selectedQuestion;
    }

    public Themes getNextThemes() {
        return nextThemes;
    }

    /**
     * Handle the result of a question and execute the logic between this question and the next one
     *
     * @param isCorrect was the answer correct
     */
    public void handleResult(boolean isCorrect) {
        if (isCorrect) {
            getCurrentPlayer().updateScore(currentPhase);
        }

        nextQuestion();
    }

    /**
     * Execute the logic between a question and the next one
     */
    public void nextQuestion() {
        try {
            saveGame("save");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If we don't have any theme left, we move on to the nextPhase OR choosing the worst player
        if (nextThemes.getSize() <= 0) {
            if (currentPhase == PhaseEnum.DecideWorstPlayer) {
                decideWorstPlayer();
            } else {
                nextPhase();
            }
            return;
        }

        // For every phase except the ending one (scoreboard), we have questions
        if (this.currentPhase != PhaseEnum.End) {
            chooseNextPlayer();

            if (currentPhase == PhaseEnum.Phase1 || currentPhase == PhaseEnum.Phase3) {
                loadQuestion(0);
            } else {
                Main.sceneManager.activate("ThemeSelection");
            }
        }
    }

    /**
     * Load the correct question and theme
     *
     * @param indexTheme Theme chosen
     */
    public void loadQuestion(int indexTheme) {
        ListQuestions possibleQuestions = new ListQuestions(nextThemes.remove(indexTheme));
        try {
            selectedQuestion = possibleQuestions.selectQuestion(currentPhase);
        } catch (ListQuestions.NoQuestionForDesiredPhaseException e) {
            e.printStackTrace();
        }

        if (selectedQuestion.getStatement() instanceof MCQ) {
            Main.sceneManager.activate("MCQ");
        } else if (selectedQuestion.getStatement() instanceof TrueFalse) {
            Main.sceneManager.activate("TrueFalse");
        } else if (selectedQuestion.getStatement() instanceof ShortAnswer) {
            Main.sceneManager.activate("ShortAnswer");
        }
    }

    /**
     * Set the old selected player to hasPlayed
     * Then, if no waiting player left, set all players that have played to waiting
     * Finally, choose a new selected player among waiting players
     */
    private void chooseNextPlayer() {

        if (getCurrentPlayer() != null) {
            getCurrentPlayer().setStatus(PlayerStatus.hasPlayed);
        }

        if (listPlayers.countPlayers(PlayerStatus.waiting) == 0) {
            while (listPlayers.countPlayers(PlayerStatus.hasPlayed) != 0) {
                listPlayers.selectPlayer(PlayerStatus.hasPlayed).setStatus(PlayerStatus.waiting);
            }
        }
        listPlayers.selectPlayer(PlayerStatus.waiting).setStatus(PlayerStatus.selected);
    }


    @Override
    public void selectPlayers() {
        do {
            listPlayers.selectPlayer().setStatus(PlayerStatus.waiting);
        } while (listPlayers.countPlayers(PlayerStatus.waiting) < 4);
    }

    /**
     * Change the current phase and set variables linked to the new phase
     */
    private void nextPhase() {
        if (currentPhase == PhaseEnum.End) {
            // If we are at the scoreboard, nothing to do
            return;
        } else if (currentPhase == null) {
            settingUpPhase1();
            return;
        } else if (!eliminationDone) {
            // Choosing which player should be eliminated at the end of a phase
            eliminationDone = true;
            if (getCurrentPlayer() != null) {
                getCurrentPlayer().setStatus(PlayerStatus.hasPlayed);
            }
            eliminateWorstPlayer();
            return;
        }

        eliminationDone = false;
        nextThemes = new Themes();


        switch (currentPhase) {
            case Phase1 -> settingUpPhase2();
            case Phase2 -> settingUpPhase3();
            case Phase3 -> settingUpPhaseEnd();
        }
        nextQuestion();
    }

    /**
     * Set up Phase1 and load it
     */
    private void settingUpPhase1() {
        currentPhase = PhaseEnum.Phase1;
        selectPlayers();

        // 2 questions per player
        for (int i = 0; i < this.listPlayers.getPlaying().size() * 2; i++) {
            nextThemes.add(allThemes.getAtIndex(allThemes.selectTheme(PhaseEnum.Phase1)));
        }

        nextQuestion();
    }

    /**
     * Set up Phase2 and load it
     */
    private void settingUpPhase2() {
        currentPhase = PhaseEnum.Phase2;
        ArrayList<Integer> currentThemesIndex = allThemes.selectSixRandomThemes();
        for (int currentThemeIndex : currentThemesIndex) {
            nextThemes.add(allThemes.getAtIndex(currentThemeIndex));
        }
    }

    /**
     * Set up Phase3 and load it
     */
    private void settingUpPhase3() {
        currentPhase = PhaseEnum.Phase3;
        // designer (that means us, developers :p) manually selected themes
        for (int i = 0; i < 2; i++) {
            nextThemes.add("gaming");
            nextThemes.add("sciences");
            nextThemes.add("technology");
        }
    }

    /**
     * Set up End phase and load it
     */
    private void settingUpPhaseEnd() {
        this.currentPhase = PhaseEnum.End;

        // Select winner
        if (this.getCurrentPlayer() != null) {
            this.getCurrentPlayer().setStatus(PlayerStatus.winner);
        } else if (listPlayers.selectPlayer(PlayerStatus.hasPlayed) != null) {
            listPlayers.selectPlayer(PlayerStatus.hasPlayed).setStatus(PlayerStatus.winner);
        }
        if (this.getCurrentPlayer() != null) {
            this.getCurrentPlayer().setStatus(PlayerStatus.waiting);
        }
        Main.sceneManager.activate("FinalScreen");
    }

    /**
     * Save a game
     *
     * @param name Name of the save file
     * @throws IOException Error
     */
    private void saveGame(String name) throws IOException {
        Files.createDirectories(Paths.get("resources/saves/"));
        FileOutputStream file = new FileOutputStream("resources/saves/" + name);
        ObjectOutputStream out = new ObjectOutputStream(file);

        out.writeObject(this);

        out.close();
        file.close();
    }

    /**
     * Load a save
     *
     * @param name Name of the save file
     */
    public void loadGame(String name) {
        try {
            FileInputStream file = new FileInputStream("resources/saves/" + name);
            ObjectInputStream in = new ObjectInputStream(file);

            Game loadedGame = (Game) in.readObject();
            this.nextThemes = loadedGame.nextThemes;
            this.listPlayers = loadedGame.listPlayers;
            this.currentPhase = loadedGame.currentPhase;
            this.phaseBeforeDeciding = loadedGame.phaseBeforeDeciding;
            this.scoreBeforeDeciding = loadedGame.scoreBeforeDeciding;
            this.timerBeforeDeciding = loadedGame.timerBeforeDeciding;
            this.worstPlayers = loadedGame.worstPlayers;

            in.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Eliminating the worst player
     */
    public void eliminateWorstPlayer() {
        List<Player> possibleEliminatedPlayers = listPlayers.getWorstPlayers();
        if (possibleEliminatedPlayers.size() == 1) {
            // Only one looser
            possibleEliminatedPlayers.get(0).setStatus(PlayerStatus.eliminated);
            nextPhase();
        } else if (possibleEliminatedPlayers.size() > 1) {
            // More than one possible looser
            startWorstPlayerEqualityManagement(possibleEliminatedPlayers);
        }
    }

    /**
     * Start the management of equality between worst players after a phase
     *
     * @param worstPlayers the list of worst player in which we need to decide who will be eliminated
     */
    public void startWorstPlayerEqualityManagement(List<Player> worstPlayers) {
        listPlayers.selectPlayer(PlayerStatus.selected).setStatus(PlayerStatus.inactive);
        // Every player not concerned is considered "winner" for the time being
        for (Player p : listPlayers.selectPlayers(PlayerStatus.hasPlayed))
            p.setStatus(PlayerStatus.winner);

        for (Player p : worstPlayers)
            p.setStatus(PlayerStatus.waiting);

        this.worstPlayers = worstPlayers;
        scoreBeforeDeciding = worstPlayers.get(0).getScore();
        timerBeforeDeciding = worstPlayers.get(0).getTimer();
        phaseBeforeDeciding = currentPhase;
        currentPhase = PhaseEnum.DecideWorstPlayer;

        turnLeftBeforeDeciding = 3;
        decideWorstPlayer();
    }

    /**
     * Choosing the worst player with question calls
     */
    public void decideWorstPlayer() {
        turnLeftBeforeDeciding--;

        // After three questions or if one of the worst players is worst than other
        if (turnLeftBeforeDeciding == 0 || (new SetPlayers(worstPlayers)).getWorstPlayers().size() == 1) {
            endWorstPlayerEqualityManagement();
            return;
        }

        for (int i = 0; i < worstPlayers.size(); i++) {
            nextThemes.add("gaming");
        }
        nextQuestion();
    }

    /**
     * Eliminate the new worst player or eliminate a random player in new worst players then reestablish the game as it was before
     */
    private void endWorstPlayerEqualityManagement() {
        List<Player> newWorstPlayers = (new SetPlayers(worstPlayers)).getWorstPlayers();

        int indexEliminated = (new Random()).nextInt(newWorstPlayers.size());
        newWorstPlayers.get(indexEliminated).setStatus(PlayerStatus.eliminated);

        // Not concerned players are now back in the game
        for (Player p : listPlayers.selectPlayers(PlayerStatus.winner))
            p.setStatus(PlayerStatus.waiting);

        for (Player p : listPlayers.selectPlayers(PlayerStatus.hasPlayed))
            p.setStatus(PlayerStatus.waiting);

        for (Player p : worstPlayers) {
            p.setDurationTimer(timerBeforeDeciding);
            p.setScore(scoreBeforeDeciding);
        }

        currentPhase = phaseBeforeDeciding;

        phaseBeforeDeciding = null;
        this.worstPlayers = null;
        scoreBeforeDeciding = null;
        timerBeforeDeciding = null;
        turnLeftBeforeDeciding = null;

        nextPhase();
    }
}

