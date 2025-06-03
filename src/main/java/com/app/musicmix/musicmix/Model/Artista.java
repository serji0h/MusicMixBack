package com.app.musicmix.musicmix.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artista", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombre"})
})
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AlbumArtista> albums = new ArrayList<>();

    public Artista() {
        this.albums = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<AlbumArtista> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumArtista> albums) {
        this.albums = albums;
    }
}