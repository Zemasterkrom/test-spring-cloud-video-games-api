package com.zemasterkrom.videogameslibraryapplication.web.controller;

import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Initialiseur de la base de données avec des commandes SQL
 *
 * @author Raphaël KIMM
 */
@Component
public class DatabaseInitializer implements CommandLineRunner {

    /**
     * Basculer la valeur à false dans application.properties pour empêcher l'initialisation
     */
    @Value("${spring.h2.initialize-database}")
    private boolean INIT_DATABASE;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        if (INIT_DATABASE) {
            System.out.println("Création et initialisation de la base de données");

            // Requêtes pour la création du schéma
            String[] sqlSchemaCreation = {
                    "DROP TABLE VideoGame IF EXISTS",
                    "CREATE TABLE VideoGame(" +
                            "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                            "name VARCHAR(255) NOT NULL CHECK (LENGTH(TRIM(name)) > 0)," +
                            "editor VARCHAR(255) NOT NULL CHECK (LENGTH(TRIM(editor)) > 0)," +
                            "description VARCHAR NOT NULL," +
                            "releasedDate TIMESTAMP NOT NULL" +
                            ");",
            };

            // Requête d'insertion
            String insertRequest = "INSERT INTO VideoGame(name, editor, description, releasedDate) VALUES(?, ?, ?, ?)";

            // Données
            List<Object[]> parameters = new LinkedList<Object[]>() {{
                add(new Object[]{
                        "Valorant",
                        "Riot Games",
                        "Valorant (stylisé VALORANT) est un jeu vidéo free-to-play de tir à la première personne en multijoueur développé et édité par Riot Games et sorti le 2 juin 2020 . Le jeu, dont le développement commence en 2014, est annoncé pour la première fois sous le nom de code Project A en octobre 2019.",
                        new SimpleDateFormat("dd/MM/yyyy").parse("02/06/2021")
                });
                add(new Object[]{
                        "Fortnite",
                        "Epic Games",
                        "Fortnite est un jeu en ligne développé par Epic Games sous la forme de différents modes de jeu qui partagent le même gameplay général et le même moteur de jeu. Les modes de jeu comprennent : Fortnite : Sauver le monde, un jeu coopératif de tir et de survie conçu pour quatre joueurs au maximum et dont le but est de combattre des zombies et de défendre des objets à l'aide de fortifications, et Fortnite Battle Royale, un jeu de battle royale en free-to-play où jusqu'à 100 joueurs se battent entre eux dans des espaces de plus en plus petits avec pour objectif d'être le dernier survivant. Ces deux modes de jeux sont déconseillés aux moins de douze ans en Europe (PEGI : 12) et aux moins de treize ans en Amérique du nord (ESRB : Teen).",
                        new SimpleDateFormat("dd/MM/yyyy").parse("26/09/2017")
                });
                add(new Object[]{
                        "Assassin's Creed Valhalla",
                        "Ubisoft",
                        "Assassin's Creed Valhalla est un RPG en monde ouvert se déroulant pendant l'âge des vikings. Vous incarnez Eivor, un viking du sexe de votre choix qui a quitté la Norvège pour trouver la fortune et la gloire en Angleterre.",
                        new SimpleDateFormat("dd/MM/yyyy").parse("10/11/2020")
                });
            }};

            // Création du schéma
            Arrays.stream(sqlSchemaCreation).forEach(sql -> {
                System.out.println(sql);
                jdbcTemplate.execute(sql);
            });

            System.out.println();

            // Insertion des données
            for (Object[] parametersValues : parameters) {
                jdbcTemplate.update(insertRequest, parametersValues);
            }

            System.out.println("Affichage de tous les jeux vidéo : ");
            jdbcTemplate.query("SELECT * FROM VideoGame",
                    (vg, i) -> {
                        System.out.println("id : " + vg.getInt("id"));
                        System.out.println("name : " + vg.getString("name"));
                        System.out.println("editor : " + vg.getString("editor"));
                        System.out.println("description : " + vg.getString("description"));
                        System.out.println("releasedDate : " + vg.getDate("releasedDate") + "\n");

                        return null;
                    }
            );
        }
    }
}
