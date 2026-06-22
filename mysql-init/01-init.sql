-- 1. Crear las bases de datos para cada microservicio
CREATE DATABASE IF NOT EXISTS db_usuarios;
CREATE DATABASE IF NOT EXISTS db_logistica;
CREATE DATABASE IF NOT EXISTS db_necesidades;
CREATE DATABASE IF NOT EXISTS db_donaciones;

-- 2. Dar permisos totales al usuario 'donaton_user' sobre todas ellas
GRANT ALL PRIVILEGES ON db_usuarios.* TO 'donaton_user'@'%';
GRANT ALL PRIVILEGES ON db_logistica.* TO 'donaton_user'@'%';
GRANT ALL PRIVILEGES ON db_necesidades.* TO 'donaton_user'@'%';
GRANT ALL PRIVILEGES ON db_donaciones.* TO 'donaton_user'@'%';

-- 3. Aplicar los cambios
FLUSH PRIVILEGES;