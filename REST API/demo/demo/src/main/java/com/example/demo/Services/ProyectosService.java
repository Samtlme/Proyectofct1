package com.example.demo.Services;

import com.example.demo.Models.Proyectos;
import com.example.demo.Repositories.ProyectosRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProyectosService {
    @Autowired
    private ProyectosRepository proyectosRepository;

    public List<Proyectos> getAllProjects() {
        return proyectosRepository.findAll();
    }

    public Proyectos getProjectById(Integer id) {
        return proyectosRepository.findById(id).orElse(null);
    }

    public Proyectos saveProject(Proyectos proyecto) {
        return proyectosRepository.save(proyecto);
    }

    public void deleteProject(Integer id) {
        proyectosRepository.deleteById(id);
    }
}
