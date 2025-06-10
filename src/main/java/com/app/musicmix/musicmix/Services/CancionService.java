package com.app.musicmix.musicmix.Services;

import com.app.musicmix.musicmix.Model.*;
import com.app.musicmix.musicmix.Repository.AlbumRepository;
import com.app.musicmix.musicmix.Repository.ArtistaRepository;
import com.app.musicmix.musicmix.Repository.CancionRepository;
import com.app.musicmix.musicmix.Repository.ListaDeReproduccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CancionService {

    @Autowired
    private ListaDeReproduccionRepository listadeReproduccionRepository;

    @Autowired
    private CancionRepository cancionRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    public Cancion createOrGetCancion(String titulo, Long artistaId, Long albumId) {
        // Verificar si la canción ya existe con el mismo título, artista y álbum
        Cancion cancion = cancionRepository.findByTituloAndArtistaAndAlbum(titulo, artistaId, albumId);
        if (cancion != null) {
            return cancion;
        }

        // Crear o buscar el artista
        Artista artista = artistaRepository.findById(artistaId)
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        // Crear o buscar el álbum
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Álbum no encontrado"));

        // Crear la canción
        Cancion nuevaCancion = new Cancion();
        nuevaCancion.setTitulo(titulo);
        nuevaCancion = cancionRepository.save(nuevaCancion);

        // Relacionar canción con álbum
        CancionAlbum cancionAlbum = new CancionAlbum();
        cancionAlbum.setCancion(nuevaCancion);
        cancionAlbum.setAlbum(album);
        nuevaCancion.getAlbums().add(cancionAlbum);

        //relaciona álbum con artista si no existe relacion
        boolean relacionExiste = album.getArtistas().stream()
                .anyMatch(albumArtista -> albumArtista.getArtista().getId().equals(artistaId));
        if (!relacionExiste) {
            AlbumArtista albumArtista = new AlbumArtista();
            albumArtista.setAlbum(album);
            albumArtista.setArtista(artista);
            album.getArtistas().add(albumArtista);
        }
        // Guardar los cambios
        cancionRepository.save(nuevaCancion);
        albumRepository.save(album);

        return nuevaCancion;
    }

    public List<Cancion> listSongs(Long listId) {
        // Buscar la lista de reproducción por ID
        ListaDeReproduccion lista = listadeReproduccionRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("Lista de reproducción no encontrada"));

        // Obtener las canciones asociadas a la lista a través de ListaCancion
        return lista.getCanciones().stream()
                .map(ListaCancion::getCancion)
                .collect(Collectors.toList());
    }

//    public Cancion fingById(Long id){
//        return cancionRepository.findById(id);
//    }
}