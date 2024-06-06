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
('Cuartouser', 'pass', 0, 0),
('Ana', 'ana123', 200, 0),
('Luis', 'luis321', 150, 0),
('Maria', 'maria456', 80, 0),
('Pedro', 'pedro654', 60, 0),
('Laura', 'laura789', 90, 0),
('Juan', 'juan987', 110, 0);

INSERT INTO Proyectos (nombre_proyecto, descripcion, fecha_inicio, fecha_fin) VALUES
('Proyecto de fin de ciclo', 'El proyecto principal', '2024-04-01', NULL),
('PFC real', 'Pensado para terminar en el futuro', '2024-04-10', '2024-06-30'),
('Sistema de gestión', 'Sistema para gestionar tareas y proyectos', '2024-05-01', NULL),
('Aplicación móvil', 'Desarrollo de una app móvil', '2024-05-15', '2024-07-15'),
('Página web', 'Desarrollo de una página web corporativa', '2024-06-01', NULL),
('Traducciones de la aplicación', 'Traducir las cadenas de texto de la sección de opciones y login', '2024-06-01', NULL);

INSERT INTO Tareas (idproyecto, nombre_tarea, descripcion, estado, puntos_tarea, prioridad_tarea) VALUES
(1, 'Revisar Documentación', 'Revisar la documentación del proyecto y corregir errores.', 'pendiente', 10, 'A'),
(1, 'Implementar Funcionalidad', 'Desarrollar y probar la nueva funcionalidad solicitada.', 'pendiente', 20, 'B'),
(2, 'Diseñar la BD', 'Crear el esquema y diseño de la base de datos del proyecto.', 'completada', 15, 'B'),
(2, 'API fase 1', 'Diseñar la versión inicial de la API', 'completada', 30, 'S'),
(2, 'API fase 2', 'Implementar funcionalidades', 'pendiente', 30, 'S'),
(3, 'Reunión inicial', 'Primera reunión para definir el proyecto', 'pendiente', 5, 'A'),
(3, 'Definir requerimientos', 'Análisis y definición de requerimientos', 'pendiente', 15, 'A'),
(3, 'Desarrollo backend', 'Desarrollo de la lógica de negocio', 'pendiente', 25, 'S'),
(4, 'Diseño UI', 'Diseñar la interfaz de usuario', 'pendiente', 20, 'A'),
(4, 'Desarrollo frontend', 'Desarrollar la interfaz de usuario', 'pendiente', 30, 'S'),
(5, 'Reunión con cliente', 'Reunión inicial con el cliente', 'pendiente', 10, 'B'),
(5, 'Definir estructura', 'Definir la estructura del sitio web', 'pendiente', 15, 'A'),
(5, 'Desarrollo HTML/CSS', 'Desarrollar la parte estática del sitio', 'pendiente', 20, 'S'),
(5, 'Integración con backend', 'Integrar la lógica de negocio con el frontend', 'pendiente', 25, 'S'),
(6, 'Traducir las strings de opciones', 'Traducir las strings del opciones al gallego y al ruso', 'pendiente', 20, 'B'),
(6, 'Traducir las strings de login', 'Traducir las strings del login al gallego y al ruso', 'pendiente', 30, 'A');


INSERT INTO Asignaciones (id_usuario, id_tarea) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(2, 5),
(3, 6),
(3, 7),
(4, 8),
(5, 9),
(5, 10),
(6, 11),
(6, 12),
(7, 13),
(7, 14),
(8, 14);

