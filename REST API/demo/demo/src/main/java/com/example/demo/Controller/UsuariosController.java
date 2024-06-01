package com.example.demo.Controller;

import com.example.demo.Models.Tareas;
import com.example.demo.Models.Usuarios;
import com.example.demo.Services.TareasService;
import com.example.demo.Services.UsuariosService;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    
    @GetMapping("/tareas/{userId}")
    public Set<Tareas> obtenerTareasUsuario(@PathVariable Integer userId) {
        
        return usuariosService.obtenerUsuarioPorId(userId).getTareas();
    }

    @PostMapping
    public Usuarios guardarUsuario(@RequestBody Usuarios usuario) {
        return usuariosService.guardarUsuario(usuario);
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> guardarUsuario(@RequestBody Map<String,String> loginRequest) {
        Usuarios usuario = new Usuarios();
        usuario.setNombre_usuario(loginRequest.get("user"));
        usuario.setContrasena(loginRequest.get("pass"));
        
        if(usuariosService.obtenerUsuarioPorNombre(loginRequest.get("user")) == null){
            usuariosService.guardarUsuario(usuario);
            return ResponseEntity.ok("Usuario registrado!");
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario actualmente registrado.");
        }

    }
    
    @GetMapping("/tareas/iniciar/{idtarea}/{userId}")
    public ResponseEntity<String> iniciarTareaUsuario(@PathVariable Integer userId, @PathVariable Integer idtarea) {
        if(usuariosService.iniciarTareaUsuario(idtarea, userId)){
            return ResponseEntity.ok("Tarea asignada correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se pudo asignar tarea");
        }
    }
    
    @GetMapping("/tareas/completar/{idtarea}/{userId}")
    public ResponseEntity<String> completarTareaUsuario(@PathVariable Integer userId, @PathVariable Integer idtarea) {
        if(usuariosService.completarTareaUsuario(idtarea, userId)){
            return ResponseEntity.ok("Tarea completada.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se pudo completar tarea");
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String> loginRequest) {
        boolean isAuthenticated = usuariosService.autenticarUsuario(loginRequest.get("user"), loginRequest.get("pass"));
        if (isAuthenticated) {
            Usuarios usuario = usuariosService.obtenerUsuarioPorNombre(loginRequest.get("user"));
            return ResponseEntity.ok(usuario.getId_usuario().toString());
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