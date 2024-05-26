
package com.example.demo.Repositories;

import com.example.demo.Models.Proyectos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectosRepository extends JpaRepository<Proyectos, Integer> {
    //
    //
}
