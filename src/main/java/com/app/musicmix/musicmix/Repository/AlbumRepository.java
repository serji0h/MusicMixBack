package com.app.musicmix.musicmix.Repository;

import com.app.musicmix.musicmix.Model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByTitulo(String titulo);
}