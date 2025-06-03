package com.app.musicmix.musicmix.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "lista_cancion")
public class ListaCancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lista_id", nullable = false)
    @JsonBackReference
    private ListaDeReproduccion lista;

    @ManyToOne
    @JoinColumn(name = "cancion_id", nullable = false)
    @JsonBackReference
    private Cancion cancion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ListaDeReproduccion getLista() {
        return lista;
    }

    public void setLista(ListaDeReproduccion lista) {
        this.lista = lista;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }
}