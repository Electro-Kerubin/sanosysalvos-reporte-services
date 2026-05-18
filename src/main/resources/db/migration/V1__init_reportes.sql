-- Initial schema for reportes service
CREATE TABLE IF NOT EXISTS reportes_mascotas (
    id BIGSERIAL PRIMARY KEY,
    mascota_id BIGINT NOT NULL,
    contacto_id BIGINT,
    ubicacion_extravio VARCHAR(255) NOT NULL,
    descripcion VARCHAR(1000) NOT NULL,
    fecha_reporte TIMESTAMP NOT NULL,
    estado VARCHAR(50) NOT NULL,
    imagen_url VARCHAR(512)
);
