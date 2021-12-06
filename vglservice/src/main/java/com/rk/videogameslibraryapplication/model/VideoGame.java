package com.rk.videogameslibraryapplication.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Classe représentant le bean d'un jeu vidéo.
 * Des vérifications basiques sont effectuées concernant le type des données (champs non vides et non null)
 */
@Entity
@Table(name = "VideoGame")
public class VideoGame {

    /**
     * Identifiant du jeu vidéo
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom du jeu vidéo
     */
    @Column(unique = true)
    private String name;

    /**
     * Editeur du jeu vidéo
     */
    @NotBlank
    private String editor;

    /**
     * Description du jeu vidéo
     */
    @Column(columnDefinition = "LONGVARCHAR")
    private String description;

    /**
     * Date de sortie du jeu
     */
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date releasedDate;

    /**
     * Constructeur par défaut de VideoGame
     */
    public VideoGame() {
        this.id = -1;
        this.name = "";
        this.editor = "";
        this.description = "";
        try {
            this.releasedDate = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/0001");
        } catch (ParseException ignored) {
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        this.name = name;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) throws IllegalArgumentException {
        this.editor = editor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) throws IllegalArgumentException {
        this.releasedDate = releasedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoGame videoGame = (VideoGame) o;
        return id.equals(videoGame.id) && name.equals(videoGame.name) && editor.equals(videoGame.editor) && description.equals(videoGame.description) && releasedDate.equals(videoGame.releasedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, editor, description, releasedDate);
    }
}
