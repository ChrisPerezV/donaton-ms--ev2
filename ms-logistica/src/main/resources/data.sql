INSERT IGNORE INTO comuna (id_comuna, nombre) VALUES
(5101, 'Valparaíso'),
(5102, 'Viña del Mar'),
(5103, 'Quilpué'),
(5104, 'Villa Alemana'),
(5105, 'Concón');

INSERT IGNORE INTO centro_acopio (id_centro, nombre, direccion, id_comuna, estado) VALUES
('cccccccc-1111-1111-1111-111111111111', 'Gimnasio Municipal Valparaíso', 'Av. Pedro Montt 123', 5101, 'ACTIVO'),
('cccccccc-2222-2222-2222-222222222222', 'Colegio San José', 'Calle Los Pinos 456', 5102, 'ACTIVO'),
('cccccccc-3333-3333-3333-333333333333', 'Sede Vecinal Quilpué', 'Pasaje Las Rosas 789', 5103, 'ACTIVO');