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

public class Game implements Serializable {
	private final static Themes allThemes = initializeAllThemes();

	private Question<?> selectedQuestion;

	private Themes nextThemes;

	public Themes getNextThemes() {
		return nextThemes;
	}

	private SetPlayers listPlayers;
	private PhaseEnum currentPhase;

	public SetPlayers getListPlayers() {
		return listPlayers;
	}

	public Player getCurrentPlayer() {
		return listPlayers.selectPlayer(PlayerStatus.selected);
	}

	public PhaseEnum getCurrentPhase() {
		return currentPhase;
	}

	public Question<?> getSelectedQuestion() {
		return selectedQuestion;
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
	public void nextQuestion() {
		while (nextThemes.size() <= 0 && this.currentPhase != PhaseEnum.End) {
			nextPhase();
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
		nextThemes = new Themes();

		if (currentPhase == null) {
			// Going to phase1
			currentPhase = PhaseEnum.Phase1;
			selectFourPlayersRandomly();

			// TODO: increase number of question to 8 or something like that
			for (int i = 0; i < 4; i++) {
				nextThemes.add(
						allThemes.getAtIndex(
								allThemes.selectTheme(PhaseEnum.Phase1)));
			}
		} else if (currentPhase == PhaseEnum.Phase1) {
			// Going to phase2
			currentPhase = PhaseEnum.Phase2;
			listPlayers.eliminateWorstPlayer();

			// We select the list of themes that will be available
			ArrayList<Integer> currentThemesIndex = allThemes.selectSixRandomThemes();
			for (int currentThemeIndex : currentThemesIndex) {
				nextThemes.add(allThemes.getAtIndex(currentThemeIndex));
			}
		} else if (currentPhase == PhaseEnum.Phase2) {
			// Going to phase3
			currentPhase = PhaseEnum.Phase3;
			listPlayers.eliminateWorstPlayer();

			// designer (that means us, developers :p) manually selected themes
			for (int i = 0; i < 2; i++) {
				nextThemes.add("gaming");
				nextThemes.add("sciences");
				nextThemes.add("technology");
			}
		} else if (currentPhase == PhaseEnum.Phase3) {
			this.getCurrentPlayer().setStatus(PlayerStatus.waiting); // We stop the timer
			this.currentPhase = PhaseEnum.End;
			Main.sceneManager.activate("FinalScreen");
		}
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
			this.selectedQuestion = loadedGame.selectedQuestion;
			this.listPlayers = loadedGame.listPlayers;
			this.currentPhase = loadedGame.currentPhase;

			in.close();
			file.close();
		} catch(Exception e) {
			System.out.println(e);
		}
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

