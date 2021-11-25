package com.rk.videogameslibraryapplication.dao;

/**
 * Constructeur d'identifiant pour le DAO JPA
 */
public class IdentifierBuilder {

    /**
     * Construire un identifiant entier à partir d'une chaîne
     * @param identifier Identifiant en chaîne
     * @return -1 si la chaîne ne correspond pas à un entier, l'entier correspondant sinon
     */
    public static Integer buildId(String identifier) {
        if (identifier == null || !identifier.matches("^[0-9]+$")) {
            return -1;
        }

        return Integer.parseInt(identifier);
    }

    /**
     * Construire un identifiant chaîne à partir d'une chaîne
     * @param identifier Identifiant en chaîne
     * @return Chaîne vide si identifiant null, chaîne sinon
     */
    public static String buildName(String identifier) {
        if (identifier == null) {
            return "";
        }

        return identifier;
    }
}
