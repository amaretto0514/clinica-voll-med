ALTER TABLE medicos ADD COLUMN activo boolean;
UPDATE medicos SET activo = true;