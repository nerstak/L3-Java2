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

public class Game implements Serializable {
	private final static Themes allThemes = initializeAllThemes();

	private transient Question<?> selectedQuestion;

	private Themes nextThemes;
	private SetPlayers listPlayers;
	private PhaseEnum currentPhase;
	boolean eliminationDone;

	private PhaseEnum phaseBeforeDeciding;
	private Integer turnLeftBeforeDeciding;
	private Integer scoreBeforeDeciding;
	private Long timerBeforeDeciding;
	private List<Player> worstPlayers;

	public SetPlayers getListPlayers() {
		return listPlayers;
	}

	public Player getCurrentPlayer() {
		synchronized (listPlayers) {
			return listPlayers.selectPlayer(PlayerStatus.selected);
		}
	}

	public PhaseEnum getCurrentPhase() {
		return currentPhase;
	}

	public Question<?> getSelectedQuestion() {
		return selectedQuestion;
	}

	public Themes getNextThemes () {
		return nextThemes;
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

	public Game () {
        listPlayers = new SetPlayers();
		nextThemes = new Themes();
		selectedQuestion = null;
		currentPhase = null;
		eliminationDone = false;

		phaseBeforeDeciding = null;
		turnLeftBeforeDeciding = null;
		Integer scoreBeforeDeciding = null;
		Long timerBeforeDeciding = null;
		worstPlayers = null;
	}

	/**
	 * Handle the result of a question and execute the logic between this question and the next one
	 *
	 * @param isCorrect was the answer correct
	 */
	public void handleResult (boolean isCorrect) {
		if (isCorrect) {
			getCurrentPlayer().updateScore(currentPhase);
		}

		nextQuestion();
	}

	/**
	 * Execute the logic between a question and the next one
	 */
	public void nextQuestion () {
		try {
			saveGame("save");
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (nextThemes.size() <= 0) {
			if (currentPhase == PhaseEnum.DecideWorstPlayer) {
				decideWorstPlayer();
			}
			else {
			 	nextPhase();
			}
			return;
		}

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
		synchronized (listPlayers) {
			if (getCurrentPlayer() != null) {
				getCurrentPlayer().setStatus(PlayerStatus.hasPlayed);
			}

			if (listPlayers.countPlayers(PlayerStatus.waiting) == 0) {
				while (listPlayers.countPlayers(PlayerStatus.hasPlayed) != 0) {
					listPlayers.selectPlayer(PlayerStatus.hasPlayed).setStatus(PlayerStatus.waiting);
				}
			}
			System.out.print(listPlayers.countPlayers(PlayerStatus.waiting));
			listPlayers.selectPlayer(PlayerStatus.waiting).setStatus(PlayerStatus.selected);
		}
	}

	/**
	 * Set 4 players among a list to waiting
	 */
	private void selectFourPlayersRandomly () {
        do {
            listPlayers.selectPlayer().setStatus(PlayerStatus.waiting);
        } while (listPlayers.countPlayers(PlayerStatus.waiting) < 4);
	}

	/**
	 * Change the current phase and set variables linked to the new phase
	 */
	private void nextPhase () {
		if (currentPhase == PhaseEnum.End) {
			return;
		} else if (currentPhase == null) {
			// Going to phase1
			currentPhase = PhaseEnum.Phase1;
			selectFourPlayersRandomly();

			for (int i = 0; i < 8; i++) {
				nextThemes.add(allThemes.getAtIndex(allThemes.selectTheme(PhaseEnum.Phase1)));
			}

			nextQuestion();
			return;
		} else if (!eliminationDone) {
			/**
			// Test to see what happens if two players are at equality
			Vector<Player> r1 = listPlayers.selectPlayers(PlayerStatus.hasPlayed);
			System.out.println(r1.get(0).getName());
			System.out.println(r1.get(1).getName());
			r1.get(0).setScore(0);
			r1.get(0).setDurationTimer(100000);
			r1.get(1).setScore(0);
			r1.get(1).setDurationTimer(100000);
			 **/
			eliminationDone = true;
			eliminateWorstPlayer();
			return;
		}

		eliminationDone = false;
		nextThemes = new Themes();

		switch (currentPhase) {
			case Phase1:
				currentPhase = PhaseEnum.Phase2;
				ArrayList<Integer> currentThemesIndex = allThemes.selectSixRandomThemes();
				for (int currentThemeIndex : currentThemesIndex) {
					nextThemes.add(allThemes.getAtIndex(currentThemeIndex));
				}
				break;

			case Phase2:
				currentPhase = PhaseEnum.Phase3;
				// designer (that means us, developers :p) manually selected themes
				for (int i = 0; i < 2; i++) {
					nextThemes.add("gaming");
					nextThemes.add("sciences");
					nextThemes.add("technology");
				}
				break;

			case Phase3:
				this.currentPhase = PhaseEnum.End;

				// Select winner
				if (this.getCurrentPlayer() != null) {
					this.getCurrentPlayer().setStatus(PlayerStatus.winner);
				} else if (listPlayers.selectPlayer(PlayerStatus.hasPlayed) != null) {
					listPlayers.selectPlayer(PlayerStatus.hasPlayed).setStatus(PlayerStatus.winner);
				}
				this.getCurrentPlayer().setStatus(PlayerStatus.waiting);
				Main.sceneManager.activate("FinalScreen");
				break;
		}
		nextQuestion();
	}

	private void saveGame (String name) throws IOException {
		Files.createDirectories(Paths.get("resources/saves/"));
		FileOutputStream file = new FileOutputStream("resources/saves/" + name);
		ObjectOutputStream out = new ObjectOutputStream(file);

		out.writeObject(this);

		out.close();
		file.close();
	}

	private void loadGame (String name) {
		try {
			FileInputStream file = new FileInputStream("resources/saves/" + name);
			ObjectInputStream in = new ObjectInputStream(file);

			Game loadedGame = (Game)in.readObject();
			this.nextThemes = loadedGame.nextThemes;
			this.listPlayers = loadedGame.listPlayers;
			this.currentPhase = loadedGame.currentPhase;
			this.phaseBeforeDeciding = loadedGame.phaseBeforeDeciding;
			this.scoreBeforeDeciding = loadedGame.scoreBeforeDeciding;
			this.timerBeforeDeciding = loadedGame.timerBeforeDeciding;
			this.worstPlayers = loadedGame.worstPlayers;

			in.close();
			file.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	public void eliminateWorstPlayer () {
		List<Player> possibleEliminatedPlayers = listPlayers.getWorstPlayers();
		if (possibleEliminatedPlayers.size() == 1) {
			possibleEliminatedPlayers.get(0).setStatus(PlayerStatus.eliminated);
			nextPhase();
		} else if (possibleEliminatedPlayers.size() > 1) {
			startWorstPlayerEqualityManagement(possibleEliminatedPlayers);
		}
	}

	/**
	 * Start the management of equality between worst players after a phase
	 *
	 * @param worstPlayers the list of worst player in which we need to decide who will be eliminated
	 */
	public void startWorstPlayerEqualityManagement (List<Player> worstPlayers) {
		listPlayers.selectPlayer(PlayerStatus.selected).setStatus(PlayerStatus.inactive);
		for (Player p : listPlayers.selectPlayers(PlayerStatus.hasPlayed))
			p.setStatus(PlayerStatus.inactive);

		for (Player p: worstPlayers)
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
	 *
	 */
	public void decideWorstPlayer () {
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
	private void endWorstPlayerEqualityManagement () {
		List<Player> newWorstPlayers = (new SetPlayers(worstPlayers)).getWorstPlayers();
		int indexEliminated = (new Random()).nextInt(newWorstPlayers.size());
		newWorstPlayers.get(indexEliminated).setStatus(PlayerStatus.eliminated);

		for (Player p : listPlayers.selectPlayers(PlayerStatus.inactive))
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

//	Phase II :
//	Le jeu se déroule entre les trois joueurs gagnants de la phase I. Cette phase propose deux questions
//	de niveau moyen pour chaque joueur (une question par thème choisi).
//	A ce niveau, les questions porteront sur uniquement 6 thèmes choisis du tableau aléatoirement.
//	A tour de rôle et de manière alternée, chaque joueur choisit un thème (ensuite un deuxième) qui est
//	supprimé des choix aussitôt sélectionnés.
//	Une question de niveau moyen (dans le thème choisi) est sélectionnée selon la politique Round-Robin
//	et présentée au joueur.
}

