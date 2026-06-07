INSERT IGNORE INTO necesidad (id_necesidad, id_usuario_creador, titulo_emergencia, id_comuna, direccion_especifica, estado, estado_registro) VALUES
('nnnnnnnn-1111-1111-1111-111111111111', '11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Incendio Forestal Cerro Cordillera', 5101, 'Sector Alto, Cerro Cordillera', 'ACTIVA', 'ACTIVO');

INSERT IGNORE INTO item_necesidad (id_item_req, id_necesidad, categoria, descripcion_item, cantidad_requerida, cantidad_cubierta, unidad_medida, prioridad, estado) VALUES
('eeeeeeee-1111-1111-1111-111111111111', 'nnnnnnnn-1111-1111-1111-111111111111', 'AGUA', 'Agua embotellada sin gas', 1000, 250, 'LITROS', 'ALTA_URGENCIA', 'PENDIENTE'),
('eeeeeeee-2222-2222-2222-222222222222', 'nnnnnnnn-1111-1111-1111-111111111111', 'ALIMENTO_NO_PERECIBLE', 'Fideos y Arroz', 500, 500, 'KILOS', 'ALTA_URGENCIA', 'CUBIERTO');