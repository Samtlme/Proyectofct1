
package com.example.demo.Services;

import com.example.demo.Models.Usuarios;
import com.example.demo.Repositories.UsuariosRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuariosService {
    @Autowired
    private UsuariosRepository usuariosRepository;

    public List<Usuarios> obtenerTodosLosUsuarios() {
        return usuariosRepository.findAll();
    }

    public Usuarios obtenerUsuarioPorId(Integer id) {
        return usuariosRepository.findById(id).orElse(null);
    }
    
    public Usuarios obtenerUsuarioPorNombre(String nombre) {
    Optional<Usuarios> optionalUsuario = usuariosRepository.findBynombre(nombre);

    if (optionalUsuario.isPresent()) {
        Usuarios usuario = optionalUsuario.get();
        return usuario;
    }
    return null;
    
    }

    public Usuarios guardarUsuario(Usuarios usuario) {
        return usuariosRepository.save(usuario);
    }
    
    public boolean autenticarUsuario(String user, String pass) {
    Optional<Usuarios> optionalUsuario = usuariosRepository.findBynombre(user);

    if (optionalUsuario.isPresent()) {
        Usuarios usuario = optionalUsuario.get();
        if (usuario != null && usuario.getContrasena().equals(pass)) {
            return true;
        }
    }
    return false;
}
    
    public Usuarios actualizarUsuario(Usuarios usuario) {
    return usuariosRepository.save(usuario);
    }
    
    public void eliminarUsuario(Integer id) {
        usuariosRepository.deleteById(id);
    }
}
