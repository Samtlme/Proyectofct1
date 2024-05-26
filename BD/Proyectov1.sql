DROP DATABASE IF EXISTS proyectofct;

CREATE DATABASE proyectofct;
USE proyectoFCT;

-- Tabla Usuarios
CREATE TABLE Usuarios (
    id_usuario INTEGER PRIMARY KEY auto_increment,
    nombre_usuario VARCHAR(255),
    contrasena VARCHAR(255),
    puntos_totales INTEGER DEFAULT 0,
    rol INTEGER DEFAULT 0	-- 0 normal, 1 admin
);

-- Tabla Proyectos
CREATE TABLE Proyectos (
    idproyecto INTEGER PRIMARY KEY auto_increment,
    nombre_proyecto VARCHAR(255),
    descripcion VARCHAR(500),
    fecha_inicio DATETIME,
    fecha_fin DATETIME	-- si tienen. fecha final, está terminada
);

-- Tabla Tareas
CREATE TABLE Tareas (
    id_tarea INTEGER PRIMARY KEY auto_increment,
    idproyecto INTEGER,
    nombre_tarea VARCHAR(255) NOT NULL,
    descripcion VARCHAR(500) NOT NULL, -- 500 caracteres maximo
    estado VARCHAR(50) DEFAULT 'pendiente',  -- 'pendiente', 'completada'
    puntos_tarea INTEGER DEFAULT 0,
    prioridad_tarea VARCHAR(1) DEFAULT 'B', -- S, A, B , es para buscar una excusa para los iconos/imágenes de prioridad
    FOREIGN KEY (idproyecto) REFERENCES Proyectos(idproyecto) ON DELETE CASCADE
);

-- Tabla Asignaciones
CREATE TABLE Asignaciones (
    id_asignacion INTEGER PRIMARY KEY auto_increment,
    id_usuario INTEGER,
    id_tarea INTEGER,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_tarea) REFERENCES Tareas(id_tarea) ON DELETE CASCADE
);

# MOCK 

INSERT INTO Usuarios (nombre_usuario, contrasena, puntos_totales, rol) VALUES
('admin', 'admin', 1000, 1),
('Samuel', 'abc123.', 50, 0),
('Poncho', 'contraseña', 75, 0),
('Cuartouser', 'pass', 0, 0);

INSERT INTO Proyectos (nombre_proyecto, descripcion, fecha_inicio, fecha_fin) VALUES
('PFC', 'El tipico proyecto que nunca se termina', '2024-04-01',NULL),
('PFC real', 'Este proyecto fue terminado en el futuro', '2024-04-10', '2024-06-30');

INSERT INTO Tareas (idproyecto, nombre_tarea, descripcion, estado, puntos_tarea, prioridad_tarea) VALUES
(1, 'Tarea 1', 'Descripción de la Tarea 1', 'pendiente', 10, 'A'),
(1, 'Tarea 2', 'Descripción de la Tarea 2', 'pendiente', 20, 'B'),
(2, 'Diseñar la BD', 'Descripción de la Tarea 3', 'completada', 15, 'B'),
(2, 'API fase 1', 'Diseñar la versión inicial de la api', 'completada', 30, 'S'),
(2, 'API fase 2', 'Intentar no odiar a java, spring boot y la madre que los parió', 'pendiente', 30, 'S');

INSERT INTO Asignaciones (id_usuario, id_tarea) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(2, 5);

