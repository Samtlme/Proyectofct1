package com.example.demo.Controller;

import com.example.demo.Models.Proyectos;
import com.example.demo.Services.ProyectosService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/proyectos")
public class ProyectosController {
    @Autowired
    private ProyectosService proyectosService;

    @GetMapping
    public List<Proyectos> obtenerTodosLosProyectos() {
        return proyectosService.getAllProjects();
    }

    @GetMapping("/{id}")
    public Proyectos obtenerProyectoPorId(@PathVariable Integer id) {
        return proyectosService.getProjectById(id);
    }

    @PostMapping
    public Proyectos guardarProyecto(@RequestBody Proyectos proyecto) {
        return proyectosService.saveProject(proyecto);
    }

    @DeleteMapping("/{id}")
    public void eliminarProyecto(@PathVariable Integer id) {
        proyectosService.deleteProject(id);
    }
}