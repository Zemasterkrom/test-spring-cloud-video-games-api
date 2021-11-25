package com.rk.videogameslibraryapplication;

import com.rk.videogameslibraryapplication.dao.VideoGameRepository;
import com.rk.videogameslibraryapplication.model.VideoGame;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;

@SpringBootApplication
public class VideoGameLibraryService {

	/**
	 * Variable permettant d'activer ou désactiver l'initialisation de la base de données. Passer à false pour désactiver.
	 */
	private static final boolean INIT_DATABASE = true;

	public static void main(String[] args) {
		SpringApplication.run(VideoGameLibraryService.class, args);
	}

	/**
	 * Initialiser la base de données avec des jeux vidéo préexistants
	 * @param repository Dépôt de jeux vidéo contenant les opérations à effectuer
	 * @return Interface fonctionnelle décrivant les opérations à effectuer sur la base de données H2
	 */
	@Bean
	public CommandLineRunner init(VideoGameRepository repository) {
		return (args) -> {
			if (INIT_DATABASE) {
				VideoGame vgOne = new VideoGame();
				vgOne.setName("Valorant");
				vgOne.setEditor("Riot Games");
				vgOne.setDescription("Valorant (stylisé VALORANT) est un jeu vidéo free-to-play de tir à la première personne en multijoueur. Chaque joueur joue le rôle d'un « agent » aux compétences uniques.");
				vgOne.setReleasedDate(new SimpleDateFormat("dd/MM/yyyy").parse("02/06/2020"));

				VideoGame vgTwo = new VideoGame();
				vgTwo.setName("Fortnite");
				vgTwo.setEditor("Epic Games");
				vgTwo.setDescription("Fortnite est un jeu en ligne développé par Epic Games sous la forme de différents modes de jeu qui partagent le même gameplay général et le même moteur de jeu. Les modes de jeu comprennent : Fortnite : Sauver le monde, un jeu coopératif de tir et de survie conçu pour quatre joueurs au maximum et dont le but est de combattre des zombies et de défendre des objets à l'aide de fortifications, et Fortnite Battle Royale, un jeu de battle royale en free-to-play où jusqu'à 100 joueurs se battent entre eux dans des espaces de plus en plus petits avec pour objectif d'être le dernier survivant. Ces deux modes de jeux sont déconseillés aux moins de douze ans en Europe (PEGI : 12) et aux moins de treize ans en Amérique du nord (ESRB : Teen).");
				vgTwo.setReleasedDate(new SimpleDateFormat("dd/MM/yyyy").parse("25/07/2017"));

				VideoGame vgThree = new VideoGame();
				vgThree.setName("Assassin's Creed Valhalla");
				vgThree.setEditor("Ubisoft");
				vgThree.setDescription("Assassin's Creed Valhalla est un jeu vidéo d'action-aventure et de rôle, développé par Ubisoft Montréal et édité par Ubisoft, sorti en novembre 2020 sur Microsoft Windows, PlayStation 4, Xbox One, Xbox Series, Google Stadia et PlayStation 5. Il appartient à la série Assassin's Creed et en est le douzième opus canonique. L'action principale du jeu se déroule pendant l'ère viking.");
				vgThree.setReleasedDate(new SimpleDateFormat("dd/MM/yyyy").parse("20/11/2020"));

				if (!repository.findByName(vgOne.getName()).isPresent()) {
					repository.save(vgOne);
				}

				if (!repository.findByName(vgTwo.getName()).isPresent()) {
					repository.save(vgTwo);
				}

				if (!repository.findByName(vgThree.getName()).isPresent()) {
					repository.save(vgThree);
				}
			}
		};
	}
}
