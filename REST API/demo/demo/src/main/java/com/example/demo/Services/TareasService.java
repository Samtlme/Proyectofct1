package com.example.demo.Services;

import com.example.demo.Models.Tareas;
import com.example.demo.Repositories.TareasRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TareasService {
    @Autowired
    private TareasRepository tareasRepository;

    public List<Tareas> getAllWorks() {
        return tareasRepository.findAll();
    }

    public Tareas getWorkById(Integer id) {
        return tareasRepository.findById(id).orElse(null);
    }

    public Tareas saveWork(Tareas tarea) {
        return tareasRepository.save(tarea);
    }

    public void deleteWork(Integer id) {
        tareasRepository.deleteById(id);
    }
    
    public List<Tareas> getTareasByProyectoId(Integer idProyecto){
        return tareasRepository.findByProyectoIdproyecto(idProyecto);
    }
}