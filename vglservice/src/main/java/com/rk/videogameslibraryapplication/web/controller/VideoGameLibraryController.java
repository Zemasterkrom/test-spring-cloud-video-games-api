package com.rk.videogameslibraryapplication.web.controller;

import com.rk.videogameslibraryapplication.dao.VideoGameRepository;
import com.rk.videogameslibraryapplication.dao.IdentifierBuilder;
import com.rk.videogameslibraryapplication.model.VideoGame;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

/**
 * Controller permettant de modifier la base statique de jeux vidéo présente
 */
@Controller
@CrossOrigin
public class VideoGameLibraryController {

    /**
     * Lien autowired vers le DAO VideoGameDAO
     */
    @Autowired
    VideoGameRepository dao;

    /**
     * En cas d'auto-détection d'une syntaxe incorrecte, renvoyer une erreur 400
     * @return Réponse avec erreur 400 BAD REQUEST
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> returnBadRequestError() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Trouver tous les jeux vidéo triés par ID
     * @return Liste de tous les jeux vidéo existants
     */
    @ResponseBody
    @GetMapping(value = "/video-games/all")
    public Set<VideoGame> getAllVideoGames() {
        return dao.findAll();
    }

    /**
     * Trouver un jeu vidéo spécifique
     * @param identifier Identifiant (ID ou nom) du jeu à chercher
     * @return Jeu vidéo trouvé (si trouvé), sinon code 204
     */
    @ResponseBody
    @GetMapping(value = "/video-games/{identifier}")
    public ResponseEntity<?> getVideoGame(@DefaultValue("") @PathVariable String identifier) {
        Integer integerId = IdentifierBuilder.buildId(identifier);
        return dao.existsByIdOrName(integerId, identifier) ? new ResponseEntity<>(dao.findByIdOrName(integerId, identifier), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Ajouter un jeu vidéo
     * @param vg Jeu vidéo à ajouter
     * @return Réponse HTTP : 409 si déjà existant, 400 si requête mal formée, 201 si créé
     */
    @PostMapping(value = "/video-games/add")
    public ResponseEntity<?> addVideoGame(@RequestBody VideoGame vg) {
        try {
            if (!dao.existsByIdOrName(vg.getId(), vg.getName())) {
                VideoGame result = dao.save(vg);
                return result.getId() >= 0 ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Modifier un jeu vidéo existant
     * @param identifier Identifiant (ID ou nom) du jeu à modifier
     * @param vg Jeu vidéo à modifier
     * @return Réponse HTTP : 204 si non existant, 400 si requête mal formée, 200 si existant et retourné
     */
    @PutMapping(value = "/video-games/modify/{identifier}")
    public ResponseEntity<?> modifyVideoGame(@DefaultValue("") @PathVariable("identifier") String identifier, @RequestBody VideoGame vg) {
        try {
            Integer integerId = IdentifierBuilder.buildId(identifier);
            Optional<VideoGame> rvg = dao.findByIdOrName(integerId, identifier);

            if (rvg.isPresent()) {
                VideoGame realVideoGame = rvg.get();
                if ((realVideoGame.getId().equals(vg.getId()) || realVideoGame.getName().equals(vg.getName())) || !dao.existsByIdOrName(vg.getId(), vg.getName())) {
                    vg.setId(realVideoGame.getId());
                    VideoGame result = dao.save(vg);
                    return result.getId() >= 0 ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                } else {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Supprimer un jeu vidéo existant
     * @param identifier Identifiant (ID ou nom) du jeu à supprimer
     * @return Réponse HTTP : 204 si non existant, 400 si requête mal formée, 200 si supprimé
     */
    @Transactional
    @DeleteMapping(value = "/video-games/delete/{identifier}")
    public ResponseEntity<?> deleteVideoGame(@DefaultValue("") @PathVariable("identifier") String identifier) {
        try {
            Integer integerId = IdentifierBuilder.buildId(identifier);

            if (dao.existsByIdOrName(integerId, identifier)) {
                dao.deleteByIdOrName(integerId, identifier);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
