-- 1. Creación de la base de datos
CREATE DATABASE IF NOT EXISTS gestion_db;
USE gestion_db;

-- 2. Tabla base: Usuarios
CREATE TABLE IF NOT EXISTS usuarios (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE
    ) ENGINE=InnoDB;

-- 3. Tabla derivada: Administradores (Herencia JOINED)
CREATE TABLE IF NOT EXISTS administradores (
                                               id BIGINT PRIMARY KEY,
                                               nivel_acceso INT NOT NULL,
                                               CONSTRAINT fk_administrador_usuario FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE
    ) ENGINE=InnoDB;

-- 4. Tabla derivada: Empleados (Herencia JOINED)
CREATE TABLE IF NOT EXISTS empleados (
                                         id BIGINT PRIMARY KEY,
                                         cargo VARCHAR(100) NOT NULL,
    salario DOUBLE NOT NULL,
    CONSTRAINT fk_empleado_usuario FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE
    ) ENGINE=InnoDB;

-- 5. Tabla derivada: Clientes (Herencia JOINED)
CREATE TABLE IF NOT EXISTS clientes (
                                        id BIGINT PRIMARY KEY,
                                        puntos_fidelidad INT DEFAULT 0,
                                        CONSTRAINT fk_cliente_usuario FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE
    ) ENGINE=InnoDB;

-- 6. Tabla: Solicitudes (Relación N:1 con Cliente)
CREATE TABLE IF NOT EXISTS solicitudes (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           descripcion TEXT NOT NULL,
                                           estado VARCHAR(50) DEFAULT 'PENDIENTE',
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_solicitud_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
    ) ENGINE=InnoDB;