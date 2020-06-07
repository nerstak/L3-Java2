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

public class Game implements  Serializable {
	private final static Themes allThemes = initializeAllThemes();

	private ListQuestions nextQuestions;
	private Question selectedQuestion;
	private SetPlayers listPlayers;
	private Themes themes;
	private PhaseEnum currentPhase;

	public SetPlayers getListPlayers () {
		return listPlayers;
	}

	public Themes getThemes() {
		return themes;
	}

	public Player getCurrentPlayer () {
		return listPlayers.selectPlayer(PlayerStatus.selected);
	}

	public PhaseEnum getCurrentPhase () {
		return currentPhase;
	}

	public Question getSelectedQuestion () {
		return selectedQuestion;
	}

	/**
	 * Create a new Themes instance then call the method readThemes on it
	 *
	 * @return the new Themes instance
	 */
	public static Themes initializeAllThemes () {
		Themes allThemes = new Themes();
		allThemes.readThemes();
		return allThemes;
	}

	public Game () {
        listPlayers = new SetPlayers();
        themes = new Themes();
		nextQuestions = new ListQuestions();
		currentPhase = null;

		// Exemple of save
		/**
		themes.readThemes();
		System.out.println(themes.getAtIndex(0));
		saveGame("hellow");
		loadGame("hellow");
		System.out.println(themes.getAtIndex(0));
		 **/
	}

	/**
	 * Handle the result of a question and execute the logic between this question and the next one
	 *
	 * @param isCorrect was the answer correct
	 */
	public void handleResult (boolean isCorrect) {
		if (isCorrect)
			getCurrentPlayer().updateScore(currentPhase);

		nextQuestion();
	}

	/**
	 * Execute the logic between a question and the next one
	 */
	public void nextQuestion () {
		while (nextQuestions.size() <= 0)
			nextPhase();

		chooseNextPlayer();

		selectedQuestion = nextQuestions.get(0);
		nextQuestions.deleteQuestion(0);

		if (selectedQuestion.getStatement() instanceof MCQ) {
			Main.sceneManager.activate("MCQ");
		} else if (selectedQuestion.getStatement() instanceof TrueFalse) {
			nextQuestion(); // TEMP: remove once TrueFalseController is created
//			Main.sceneManager.activate("TrueFalse");
		} else if (selectedQuestion.getStatement() instanceof ShortAnswer) {
			nextQuestion(); // TEMP: remove once ShortAnswerController is created
//			Main.sceneManager.activate("ShortAnswer");
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
		ArrayList<Integer> currentThemesIndex = new ArrayList<>();
		nextQuestions = new ListQuestions();

		if (currentPhase == null) {
			selectFourPlayersRandomly();
			currentPhase = PhaseEnum.Phase1;
			currentThemesIndex.add(allThemes.selectTheme(PhaseEnum.Phase1));
			ListQuestions possibleQuestions = new ListQuestions(allThemes.getAtIndex(currentThemesIndex.get(0)));
			for (int i = 0; i < 4; i++) {
				nextQuestions.addQuestion(possibleQuestions.selectQuestion(PhaseEnum.Phase1));
			}
		} else if (currentPhase == PhaseEnum.Phase1) {
			// TODO: attendre la réponse du prof par rapport au fait qu'on a une méthode pour séléctionner 5 thèmes mais qu'il faut en séléctionner 6 pour la phase 2
			// TODO: sélectionner 6 thèmes
			currentPhase = PhaseEnum.Phase2;
		} else if (currentPhase == PhaseEnum.Phase2) {
			currentPhase = PhaseEnum.Phase3;
		} else if (currentPhase == PhaseEnum.Phase3) {
			System.exit(0);
		}
	}

	private void saveGame (String name) {
		try {
			Files.createDirectories(Paths.get("resources/saves/"));
			FileOutputStream file = new FileOutputStream("resources/saves/" + name);
			ObjectOutputStream out = new ObjectOutputStream(file);

			out.writeObject(this);

			out.close();
			file.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	private void loadGame (String name) {
		try {
			FileInputStream file = new FileInputStream("resources/saves/" + name);
			ObjectInputStream in = new ObjectInputStream(file);

			Game loadedGame = (Game)in.readObject();
			this.nextQuestions = loadedGame.nextQuestions;
			this.selectedQuestion = loadedGame.selectedQuestion;
			this.listPlayers = loadedGame.listPlayers;
			this.themes = loadedGame.themes;
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
//	Le score du joueur qui donne la bonne réponse est incrémenté de 3.
//	Phase III :
//	Dans cette phase, le jeu se déroule entre les deux joueurs gagnants de la phase II. Les questions
//	porteront sur trois thèmes choisis par le concepteur du jeu. Le score du joueur donnant la bonne
//	réponse est incrémenté de 5.
}

