package com.app.musicmix.musicmix.Repository;

import com.app.musicmix.musicmix.Model.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, Long> {
    @Query("SELECT c FROM Cancion c JOIN c.albums ca WHERE c.titulo = :titulo AND ca.album.id = :albumId AND EXISTS (SELECT aa FROM AlbumArtista aa WHERE aa.album.id = :albumId AND aa.artista.id = :artistaId)")
    Cancion findByTituloAndArtistaAndAlbum(@Param("titulo") String titulo, @Param("artistaId") Long artistaId, @Param("albumId") Long albumId);
}
