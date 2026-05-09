-- Este script se ejecutará automáticamente al levantar el contenedor por primera vez
CREATE DATABASE IF NOT EXISTS db_usuarios;
CREATE DATABASE IF NOT EXISTS db_necesidades;
CREATE DATABASE IF NOT EXISTS db_donaciones;
CREATE DATABASE IF NOT EXISTS db_logistica;
CREATE DATABASE IF NOT EXISTS db_catalogos;
CREATE DATABASE IF NOT EXISTS db_notificaciones;

-- Dar permisos al usuario sobre todas estas bases de datos
GRANT ALL PRIVILEGES ON db_usuarios.* TO 'donaton_user'@'%';
GRANT ALL PRIVILEGES ON db_necesidades.* TO 'donaton_user'@'%';
GRANT ALL PRIVILEGES ON db_donaciones.* TO 'donaton_user'@'%';
GRANT ALL PRIVILEGES ON db_logistica.* TO 'donaton_user'@'%';
GRANT ALL PRIVILEGES ON db_catalogos.* TO 'donaton_user'@'%';
GRANT ALL PRIVILEGES ON db_notificaciones.* TO 'donaton_user'@'%';

FLUSH PRIVILEGES;