-- =============================================================
-- MIGRACIÓN V2 - Agregar columnas faltantes
-- Proyecto: SanoSysAlvos
-- Fecha: 2026-06-22
-- Descripción: Agrega columnas foto_url y qr_uuid a mascota,
--              es_temporal y creado_en a contacto si no existen.
-- =============================================================

-- Agregar columna foto_url a mascota
DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_name='mascota' AND column_name='foto_url'
  ) THEN
    ALTER TABLE mascota ADD COLUMN foto_url VARCHAR(512);
  END IF;
END $$;

-- Agregar columna qr_uuid a mascota
DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_name='mascota' AND column_name='qr_uuid'
  ) THEN
    ALTER TABLE mascota ADD COLUMN qr_uuid UUID UNIQUE;
  END IF;
END $$;

-- Agregar columna es_temporal a contacto
DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_name='contacto' AND column_name='es_temporal'
  ) THEN
    ALTER TABLE contacto ADD COLUMN es_temporal BOOLEAN DEFAULT FALSE;
  END IF;
END $$;

-- Agregar columna creado_en a contacto
DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_name='contacto' AND column_name='creado_en'
  ) THEN
    ALTER TABLE contacto ADD COLUMN creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
  END IF;
END $$;
