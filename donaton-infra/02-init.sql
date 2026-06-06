USE db_logistica;

INSERT IGNORE INTO comuna (id_comuna, nombre) VALUES
(5101, 'Valparaiso'),
(5102, 'Vina del Mar'),
(5103, 'Quilpue'),
(5104, 'Villa Alemana'),
(5105, 'Concon');

INSERT IGNORE INTO centro_acopio (id_centro, nombre, direccion, id_comuna, estado) VALUES
('cccccccc-1111-1111-1111-111111111111', 'Gimnasio Municipal Valparaiso', 'Av. Pedro Montt 123', 5101, 'ACTIVO'),
('cccccccc-2222-2222-2222-222222222222', 'Colegio San Jose', 'Calle Los Pinos 456', 5102, 'ACTIVO'),
('cccccccc-3333-3333-3333-333333333333', 'Sede Vecinal Quilpue', 'Pasaje Las Rosas 789', 5103, 'ACTIVO');


USE db_usuarios;

-- Añadimos 'tipo_donante' que es obligatorio en tu clase Usuario
INSERT IGNORE INTO usuario (id_usuario, documento_identidad, nombre_completo, email, password, rol, tipo_donante) VALUES
('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1', 'Admin General', 'admin@donaton.com', '$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP.', 'ADMINISTRADOR', 'PERSONA_NATURAL'),
('22222222-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2', 'Juan Donante', 'juan@correo.com', '$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP.', 'DONANTE', 'PERSONA_NATURAL');


USE db_necesidades;

INSERT IGNORE INTO necesidad (id_necesidad, id_usuario_creador, titulo_emergencia, id_comuna, direccion_especifica, estado) VALUES
('nnnnnnnn-1111-1111-1111-111111111111', '11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Incendio Forestal Cerro Cordillera', 5101, 'Sector Alto, Cerro Cordillera', 'ACTIVA');

INSERT IGNORE INTO item_necesidad (id_item_req, id_necesidad, categoria, descripcion_item, cantidad_requerida, cantidad_cubierta, unidad_medida, prioridad, estado) VALUES
('eeeeeeee-1111-1111-1111-111111111111', 'nnnnnnnn-1111-1111-1111-111111111111', 'AGUA', 'Agua embotellada sin gas', 1000, 250, 'LITROS', 'ALTA_URGENCIA', 'PENDIENTE'),
('eeeeeeee-3333-3333-3333-333333333333', 'nnnnnnnn-1111-1111-1111-111111111111', 'HERRAMIENTAS', 'Palas y Carretillas', 50, 10, 'UNIDADES', 'MEDIA', 'PENDIENTE');