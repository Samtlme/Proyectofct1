package com.example.demo.Controller;

import com.example.demo.Models.Tareas;
import com.example.demo.Services.TareasService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tareas")
public class TareasController {
    @Autowired
    private TareasService tareasService;

    @GetMapping
    public List<Tareas> obtenerTodasLasTareas() {
        return tareasService.getAllWorks();
    }
    
    @GetMapping("/proyecto/{idProyecto}")
    public List<Tareas> getTareasByProyectoId(@PathVariable Integer idProyecto){
        return tareasService.getTareasByProyectoId(idProyecto);
    }

    @GetMapping("/{id}")
    public Tareas obtenerTareaPorId(@PathVariable Integer id) {
        return tareasService.getWorkById(id);
    }

    @PostMapping
    public Tareas guardarTarea(@RequestBody Tareas tarea) {
        return tareasService.saveWork(tarea);
    }

    @DeleteMapping("/{id}")
    public void eliminarTarea(@PathVariable Integer id) {
        tareasService.deleteWork(id);
    }
}