
package com.example.demo.Services;

import com.example.demo.Models.Tareas;
import com.example.demo.Models.Usuarios;
import com.example.demo.Repositories.TareasRepository;
import com.example.demo.Repositories.UsuariosRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuariosService {
    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private TareasRepository tareasRepository;

    public List<Usuarios> obtenerTodosLosUsuarios() {
        return usuariosRepository.findAll();
    }

    public Usuarios obtenerUsuarioPorId(Integer id) {
        return usuariosRepository.findById(id).orElse(null);
    }
    
    public boolean iniciarTareaUsuario(Integer idTarea, Integer userId){
       Optional<Usuarios> optionalUsuario = usuariosRepository.findById(userId);
       Optional<Tareas> optionalTarea = tareasRepository.findById(idTarea);
       
       if (optionalUsuario.isPresent() && optionalTarea.isPresent()) {
            Usuarios usuario = optionalUsuario.get();
            Tareas tarea = optionalTarea.get();
            usuario.getTareas().add(tarea);
            usuariosRepository.save(usuario);
            return true;
        }
       return false;   
    }
    
       public boolean completarTareaUsuario(Integer idTarea, Integer userId){
       Optional<Usuarios> optionalUsuario = usuariosRepository.findById(userId);
       Optional<Tareas> optionalTarea = tareasRepository.findById(idTarea);
       
       if (optionalUsuario.isPresent() && optionalTarea.isPresent()) {
            Usuarios usuario = optionalUsuario.get();
            Tareas tarea = optionalTarea.get();
            usuario.setPuntos_totales(usuario.getPuntos_totales() + 
                    tarea.getPuntos_tarea());
            usuariosRepository.save(usuario);
            tarea.setEstado("completada");
            tareasRepository.save(tarea);
            return true;
        }
       return false;   
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
