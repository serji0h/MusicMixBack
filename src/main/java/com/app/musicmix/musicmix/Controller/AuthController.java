package com.app.musicmix.musicmix.Controller;

import com.app.musicmix.musicmix.Model.Usuario;
import com.app.musicmix.musicmix.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8100")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Usuario usuario = usuarioService.autenticarUsuario(loginRequest.getEmail(), loginRequest.getContrasena());
            return ResponseEntity.ok(new AuthResponse(usuario.getId()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest loginRequest) {
        try {
            Usuario usuario = usuarioService.registrarUsuario(loginRequest.getNombre(),loginRequest.getEmail(), loginRequest.getContrasena());
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

class LoginRequest {
    private String nombre;
    private String email;
    private String contrasena;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}

class AuthResponse {
    private Long userId;

    public AuthResponse(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() { return userId; }
}