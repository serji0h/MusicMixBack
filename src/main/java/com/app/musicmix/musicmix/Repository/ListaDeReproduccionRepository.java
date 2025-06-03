package com.app.musicmix.musicmix.Repository;

import com.app.musicmix.musicmix.Model.ListaDeReproduccion;
import com.app.musicmix.musicmix.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ListaDeReproduccionRepository extends JpaRepository<ListaDeReproduccion, Long> {
    List<ListaDeReproduccion> findByUsuario(Usuario usuario);
}