package com.app.musicmix.musicmix.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cancion")
public class Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @OneToMany(mappedBy = "cancion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference//para que no se repita
    private List<ListaCancion> listas = new ArrayList<>();

    @OneToMany(mappedBy = "cancion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CancionAlbum> albums = new ArrayList<>();

    public Cancion() {
        this.listas = new ArrayList<>();
        this.albums = new ArrayList<>();
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

    public List<ListaCancion> getListas() {
        return listas;
    }

    public void setListas(List<ListaCancion> listas) {
        this.listas = listas;
    }

    public List<CancionAlbum> getAlbums() {
        return albums;
    }

    public void setAlbums(List<CancionAlbum> albums) {
        this.albums = albums;
    }
}