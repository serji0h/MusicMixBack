package com.app.musicmix.musicmix.Services;

import com.app.musicmix.musicmix.Model.Usuario;
import com.app.musicmix.musicmix.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(String nombre,String email, String contrasena) {
        if (usuarioRepository.findByEmail(email) != null) {
            throw new RuntimeException("El email ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContrasena(passwordEncoder.encode(contrasena)); // Encriptar
        return usuarioRepository.save(usuario);
    }

    public Usuario autenticarUsuario(String email, String contrasena) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null || !passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        return usuario;
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id) {

        usuarioRepository.deleteById(id);
    }
}