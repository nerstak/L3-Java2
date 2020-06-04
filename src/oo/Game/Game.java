package oo.Game;

import Project.SceneManager;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oo.Players.Player;
import oo.Players.PlayerStatus;
import oo.Players.SetPlayers;
import oo.Questions.Themes;

public class Game extends Application {
	private SetPlayers listPlayers;
	private Themes themes;
	private Player currentPlayer;
	private Phase currentPhase;
	private SceneManager sceneManager;
	
	public Game () {
        listPlayers = new SetPlayers();
        themes = new Themes();
	}
	
	public void _start() {
		launch();
	}

	@Override
	public void start (Stage primaryStage) throws Exception {
		setWindowParameters(primaryStage);
		
        selectFourPlayersRandomly();

        sceneManager = new SceneManager(primaryStage);
        sceneManager.activate("Starting");
	}
	
	private void setWindowParameters (Stage primaryStage) {
        primaryStage.setTitle("Weakest Link - The new entertainment game for the whole family");
        primaryStage.getIcons().add(new Image("file:resources/img/weakest-link-icon.png"));
        primaryStage.setResizable(false);
        primaryStage.show();
	}
	
	private void selectFourPlayersRandomly () {
        do {
            listPlayers.selectPlayer().setStatus(PlayerStatus.waiting);
        } while (listPlayers.countPlayers(PlayerStatus.waiting) < 4);
	}
	
//	Phases du jeu
//	Le jeu de Question / Réponse à réaliser comprendra trois phases où 4 joueurs s’affrontent autour de
//	questions sur 10 thèmes :
//	Phase I :
//	Dans cette phase, le jeu se déroule entre 4 joueurs choisis aléatoirement. Un thème parmi 10 est
//	sélectionné automatiquement du tableau dans un ordre séquentiel (un indicateur du dernier thème
//	sélectionné est mis à jour, après le choix du 10ème thème, on revient au premier).
//	Une question de niveau facile est sélectionnée pour chacun des joueurs selon une politique RoundRobin (i.e de manière circulaire). Les 4 joueurs répondent à leurs questions séparément, le score est
//	incrémenté de 2 si la réponse donnée est correcte.
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
