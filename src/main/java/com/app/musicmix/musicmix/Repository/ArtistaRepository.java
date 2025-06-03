package com.app.musicmix.musicmix.Repository;

import com.app.musicmix.musicmix.Model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    List<Artista> findByNombre(String nombre);
}