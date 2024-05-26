
package com.example.demo.Repositories;

import com.example.demo.Models.Tareas;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TareasRepository extends JpaRepository<Tareas, Integer> {
    //
    //

    List<Tareas> findByProyectoIdproyecto(Integer idproyecto);
}
