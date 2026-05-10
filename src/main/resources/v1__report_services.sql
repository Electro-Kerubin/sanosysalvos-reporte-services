-- =============================================================
-- MIGRACIÓN V1 - Report Service
-- Proyecto: SanoSysAlvos
-- Fecha: 2026-04-22
-- Descripción: Creación inicial de tablas del microservicio
--              de reportes de mascotas.
-- =============================================================


-- =========================
-- TABLAS BASE / CATÁLOGOS
-- =========================

CREATE TABLE IF NOT EXISTS tipo_reporte (
    id_tipo_reporte     INT             PRIMARY KEY,
    descripcion_tipo_reporte VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS estatus (
    id_estatus          INT             PRIMARY KEY,
    descripcion_estatus VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS canal_preferencia (
    id_canal_preferencia INT            PRIMARY KEY,
    descripcion          VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS raza (
    id_raza             INT             PRIMARY KEY,
    descripcion         VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS especie (
    id_especie          INT             PRIMARY KEY,
    descripcion         VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS sexo (
    id_sexo             INT             PRIMARY KEY,
    descripcion         VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS marca_distintiva (
    id_marca_distintiva INT             PRIMARY KEY,
    descripcion         VARCHAR(255)
);


-- =========================
-- TABLA CONTACTO
-- =========================

CREATE TABLE IF NOT EXISTS contacto (
    id_contacto          SERIAL         PRIMARY KEY,
    nombres              VARCHAR(255),
    correo               VARCHAR(255),
    telefono             BIGINT,
    id_canal_preferencia INT,
    CONSTRAINT fk_contacto_canal
        FOREIGN KEY (id_canal_preferencia)
        REFERENCES canal_preferencia(id_canal_preferencia)
);


-- =========================
-- TABLA MASCOTA
-- =========================

CREATE TABLE IF NOT EXISTS mascota (
    id_mascota          SERIAL          PRIMARY KEY,
    nombre_mascota      VARCHAR(255),
    id_raza             INT,
    id_especie          INT,
    color_primario      VARCHAR(100),
    color_secundario    VARCHAR(100),
    tamano              VARCHAR(50),
    id_sexo             INT,
    edad                INT,
    detalles_extra      VARCHAR(255),
    id_chip             VARCHAR(100),
    CONSTRAINT fk_mascota_raza
        FOREIGN KEY (id_raza)
        REFERENCES raza(id_raza),
    CONSTRAINT fk_mascota_especie
        FOREIGN KEY (id_especie)
        REFERENCES especie(id_especie),
    CONSTRAINT fk_mascota_sexo
        FOREIGN KEY (id_sexo)
        REFERENCES sexo(id_sexo)
);


-- =========================
-- TABLA REPORTE MASCOTA
-- =========================

CREATE TABLE IF NOT EXISTS reporte_mascota (
    id_reporte_mascota  SERIAL          PRIMARY KEY,
    id_tipo_reporte     INT,
    id_estatus          INT,
    fecha_extravio      DATE,
    id_contacto         INT,
    fecha_avistamiento  DATE,
    fecha_reporte       TIMESTAMP,
    id_marca_distintiva INT,
    id_mascota          INT,
    CONSTRAINT fk_reporte_tipo
        FOREIGN KEY (id_tipo_reporte)
        REFERENCES tipo_reporte(id_tipo_reporte),
    CONSTRAINT fk_reporte_estatus
        FOREIGN KEY (id_estatus)
        REFERENCES estatus(id_estatus),
    CONSTRAINT fk_reporte_contacto
        FOREIGN KEY (id_contacto)
        REFERENCES contacto(id_contacto),
    CONSTRAINT fk_reporte_marca
        FOREIGN KEY (id_marca_distintiva)
        REFERENCES marca_distintiva(id_marca_distintiva),
    CONSTRAINT fk_reporte_mascota
        FOREIGN KEY (id_mascota)
        REFERENCES mascota(id_mascota)
);


-- =============================================================
-- ÍNDICES
-- PostgreSQL NO crea índices automáticamente en FK,
-- por eso se deben declarar explícitamente.
-- =============================================================

-- ---- contacto ----
-- Búsquedas frecuentes por correo y canal de preferencia
CREATE INDEX IF NOT EXISTS idx_contacto_canal_preferencia
    ON contacto(id_canal_preferencia);

CREATE INDEX IF NOT EXISTS idx_contacto_correo
    ON contacto(correo);

-- ---- mascota ----
-- Joins y filtros por raza, especie, sexo y chip
CREATE INDEX IF NOT EXISTS idx_mascota_raza
    ON mascota(id_raza);

CREATE INDEX IF NOT EXISTS idx_mascota_especie
    ON mascota(id_especie);

CREATE INDEX IF NOT EXISTS idx_mascota_sexo
    ON mascota(id_sexo);

CREATE INDEX IF NOT EXISTS idx_mascota_id_chip
    ON mascota(id_chip);

CREATE INDEX IF NOT EXISTS idx_mascota_nombre
    ON mascota(nombre_mascota);

-- ---- reporte_mascota ----
-- Consultas frecuentes por estado, tipo, mascota, contacto y fechas
CREATE INDEX IF NOT EXISTS idx_reporte_tipo_reporte
    ON reporte_mascota(id_tipo_reporte);

CREATE INDEX IF NOT EXISTS idx_reporte_estatus
    ON reporte_mascota(id_estatus);

CREATE INDEX IF NOT EXISTS idx_reporte_contacto
    ON reporte_mascota(id_contacto);

CREATE INDEX IF NOT EXISTS idx_reporte_marca_distintiva
    ON reporte_mascota(id_marca_distintiva);

CREATE INDEX IF NOT EXISTS idx_reporte_mascota
    ON reporte_mascota(id_mascota);

CREATE INDEX IF NOT EXISTS idx_reporte_fecha_reporte
    ON reporte_mascota(fecha_reporte DESC);

CREATE INDEX IF NOT EXISTS idx_reporte_fecha_extravio
    ON reporte_mascota(fecha_extravio DESC);

CREATE INDEX IF NOT EXISTS idx_reporte_fecha_avistamiento
    ON reporte_mascota(fecha_avistamiento DESC);

-- Índice compuesto: filtro más común (tipo + estatus)
CREATE INDEX IF NOT EXISTS idx_reporte_tipo_estatus
    ON reporte_mascota(id_tipo_reporte, id_estatus);

-- Índice compuesto: historial de reportes de una mascota ordenado por fecha
CREATE INDEX IF NOT EXISTS idx_reporte_mascota_fecha
    ON reporte_mascota(id_mascota, fecha_reporte DESC);


-- =============================================================
-- DATOS INICIALES (CATÁLOGOS)
-- =============================================================

INSERT INTO tipo_reporte (id_tipo_reporte, descripcion_tipo_reporte) VALUES
    (1, 'Mascota perdida'),
    (2, 'Mascota encontrada'),
    (3, 'Avistamiento')
ON CONFLICT (id_tipo_reporte) DO NOTHING;

INSERT INTO estatus (id_estatus, descripcion_estatus) VALUES
    (1, 'Activo'),
    (2, 'Resuelto'),
    (3, 'Cerrado')
ON CONFLICT (id_estatus) DO NOTHING;

INSERT INTO canal_preferencia (id_canal_preferencia, descripcion) VALUES
    (1, 'Correo electrónico'),
    (2, 'Teléfono'),
    (3, 'WhatsApp')
ON CONFLICT (id_canal_preferencia) DO NOTHING;

INSERT INTO raza (id_raza, descripcion) VALUES
    (1, 'Labrador'),
    (2, 'Golden Retriever'),
    (3, 'Bulldog'),
    (4, 'Pastor Alemán'),
    (5, 'Mestizo'),
    (6, 'Siamés'),
    (7, 'Persa'),
    (8, 'Sin raza definida')
ON CONFLICT (id_raza) DO NOTHING;

INSERT INTO especie (id_especie, descripcion) VALUES
    (1, 'Perro'),
    (2, 'Gato'),
    (3, 'Ave'),
    (4, 'Conejo'),
    (5, 'Otro')
ON CONFLICT (id_especie) DO NOTHING;

INSERT INTO sexo (id_sexo, descripcion) VALUES
    (1, 'Macho'),
    (2, 'Hembra'),
    (3, 'No identificado')
ON CONFLICT (id_sexo) DO NOTHING;

INSERT INTO marca_distintiva (id_marca_distintiva, descripcion) VALUES
    (1, 'Collar'),
    (2, 'Tatuaje'),
    (3, 'Microchip'),
    (4, 'Mancha en el pelaje'),
    (5, 'Cicatriz'),
    (6, 'Sin marca distintiva')
ON CONFLICT (id_marca_distintiva) DO NOTHING;

