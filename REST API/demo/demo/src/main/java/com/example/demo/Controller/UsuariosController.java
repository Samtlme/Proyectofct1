package com.example.demo.Controller;

import com.example.demo.Models.Usuarios;
import com.example.demo.Services.UsuariosService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
    @Autowired
    private UsuariosService usuariosService;

    @GetMapping
    public List<Usuarios> obtenerTodosLosUsuarios() {
        return usuariosService.obtenerTodosLosUsuarios();
    }

    @GetMapping("/{id}")
    public Usuarios obtenerUsuarioPorId(@PathVariable Integer id) {
        return usuariosService.obtenerUsuarioPorId(id);
    }

    @PostMapping
    public Usuarios guardarUsuario(@RequestBody Usuarios usuario) {
        return usuariosService.guardarUsuario(usuario);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String> loginRequest) {
        boolean isAuthenticated = usuariosService.autenticarUsuario(loginRequest.get("user"), loginRequest.get("pass"));
        if (isAuthenticated) {
            return ResponseEntity.ok("Login exitoso.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login fallido.");
        }
    }
    
    @PutMapping
    public Usuarios actualizarUsuario(@RequestBody Usuarios usuario) {  
        return usuariosService.actualizarUsuario(usuario);              
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Integer id) {
        usuariosService.eliminarUsuario(id);
    }
}