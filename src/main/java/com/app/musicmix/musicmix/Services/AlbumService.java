package com.app.musicmix.musicmix.Services;

import com.app.musicmix.musicmix.Model.Album;
import com.app.musicmix.musicmix.Repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public Album createOrGetAlbum(String titulo) {
        List<Album> album = albumRepository.findByTitulo(titulo);
        if (!album.isEmpty()) {
            return album.get(0);
        }
        Album nuevoAlbum = new Album();
        nuevoAlbum.setTitulo(titulo);
        return albumRepository.save(nuevoAlbum);
    }
}