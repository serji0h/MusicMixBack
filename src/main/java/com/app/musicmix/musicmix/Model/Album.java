package com.app.musicmix.musicmix.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "album", uniqueConstraints = { @UniqueConstraint(columnNames = {"titulo"})})
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CancionAlbum> canciones = new ArrayList<>();

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AlbumArtista> artistas = new ArrayList<>();

    public Album() {
        this.canciones = new ArrayList<>();
        this.artistas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<CancionAlbum> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<CancionAlbum> canciones) {
        this.canciones = canciones;
    }

    public List<AlbumArtista> getArtistas() {
        return artistas;
    }

    public void setArtistas(List<AlbumArtista> artistas) {
        this.artistas = artistas;
    }
}