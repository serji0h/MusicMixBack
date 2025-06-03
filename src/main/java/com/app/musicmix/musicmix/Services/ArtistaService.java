package com.app.musicmix.musicmix.Services;

import com.app.musicmix.musicmix.Model.Artista;
import com.app.musicmix.musicmix.Repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;

    public Artista createOrGetArtista(String nombre) {
        List<Artista> artista = artistaRepository.findByNombre(nombre);
        if (!artista.isEmpty()) {
            return artista.get(0);
        }
        Artista nuevoArtista = new Artista();
        nuevoArtista.setNombre(nombre);
        return artistaRepository.save(nuevoArtista);
    }
}