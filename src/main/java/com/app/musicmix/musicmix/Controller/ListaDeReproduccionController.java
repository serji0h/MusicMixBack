package com.app.musicmix.musicmix.Controller;

import com.app.musicmix.musicmix.Model.*;
import com.app.musicmix.musicmix.Repository.CancionRepository;
import com.app.musicmix.musicmix.Repository.ListaDeReproduccionRepository;
import com.app.musicmix.musicmix.Repository.UsuarioRepository;
import com.app.musicmix.musicmix.Repository.ArtistaRepository;
import com.app.musicmix.musicmix.Repository.AlbumRepository;
import com.app.musicmix.musicmix.Services.ArtistaService;
import com.app.musicmix.musicmix.Services.CancionService;
import com.app.musicmix.musicmix.Services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@CrossOrigin(origins = "http://localhost:8100")
public class ListaDeReproduccionController {

    @Autowired
    private ListaDeReproduccionRepository playlistRepository;

    @Autowired
    private CancionRepository cancionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private CancionService cancionService;

    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private AlbumService albumService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ListaDeReproduccion>> getPlaylists(@PathVariable Long userId) {
        Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<ListaDeReproduccion> playlists = playlistRepository.findByUsuario(usuario);
        return ResponseEntity.ok(playlists);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ListaDeReproduccion> createPlaylist(@PathVariable Long userId, @RequestBody String nombre) {
        Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        ListaDeReproduccion playlist = new ListaDeReproduccion();
        playlist.setNombre(nombre);
        playlist.setUsuario(usuario);
        playlist = playlistRepository.save(playlist);
        return ResponseEntity.ok(playlist);
    }

    @PostMapping("/{playlistId}/add-song/{songId}")
    public ResponseEntity<ListaDeReproduccion> addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        ListaDeReproduccion playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist no encontrada"));
        Cancion cancion = cancionRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Canción no encontrada"));

        ListaCancion listaCancion = new ListaCancion();
        listaCancion.setLista(playlist);
        listaCancion.setCancion(cancion);

        playlist.getCanciones().add(listaCancion);
        playlist = playlistRepository.save(playlist);
        return ResponseEntity.ok(playlist);
    }

    @DeleteMapping("/{playlistId}/remove-song/{songId}")
    public ResponseEntity<ListaDeReproduccion> removeSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        ListaDeReproduccion playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist no encontrada"));
        playlist.getCanciones().removeIf(lc -> lc.getCancion().getId().equals(songId));
        playlist = playlistRepository.save(playlist);
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/songs")
    public ResponseEntity<List<Cancion>> getAllSongs() {
        List<Cancion> canciones = cancionRepository.findAll();
        return ResponseEntity.ok(canciones);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<Cancion> getSongById(@PathVariable Long id){
        Cancion cancion = cancionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canción no encontrada"));
        return ResponseEntity.ok(cancion);
    }

    @GetMapping("/{playlistId}/songs")
    public ResponseEntity<List<Cancion>>getListSongs(@PathVariable Long playlistId){
        List<Cancion> lista = cancionService.listSongs(playlistId);
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/songs")
    public ResponseEntity<Cancion> createSong(@RequestBody CancionRequest cancionRequest) {
        //crea u obtiene tanto el artista como el album
        Artista artista = artistaService.createOrGetArtista(cancionRequest.getArtista());
        Album album = albumService.createOrGetAlbum(cancionRequest.getAlbum());

        //crea u obtiene la cancion
        Cancion cancion = cancionService.createOrGetCancion(
                cancionRequest.getTitulo(),
                artista.getId(),
                album.getId()
        );
        return ResponseEntity.ok(cancion);
    }

    @PostMapping("/artists")
    public ResponseEntity<Artista> createArtist(@RequestBody ArtistaRequest artistaRequest) {
        Artista artista = artistaService.createOrGetArtista(artistaRequest.getNombre());
        return ResponseEntity.ok(artista);
    }

    @PostMapping("/albums")
    public ResponseEntity<Album> createAlbum(@RequestBody AlbumRequest albumRequest) {
        Album album = albumService.createOrGetAlbum(albumRequest.getTitulo());
        return ResponseEntity.ok(album);
    }
}

class CancionRequest {
    private String titulo;
    private String artista;
    private String album;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}

class ArtistaRequest {
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

class AlbumRequest {
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}