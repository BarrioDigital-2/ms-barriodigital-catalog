-- Tipos de tramite iniciales (mismos codigos que el front).
-- INSERT IGNORE: si el codigo ya existe, no hace nada; asi se puede reiniciar la app sin duplicar ni pisar datos
INSERT IGNORE INTO tipo_tramite (codigo, nombre, requisitos, plazo_estimado_dias, cupo_maximo, cupo_disponible, activo) VALUES
('CERTIFICADO_RESIDENCIA', 'Certificado de residencia', 'Cedula de identidad y comprobante de domicilio', 2, 20, 20, TRUE),
('LUMINARIA_APAGADA', 'Luminaria apagada', 'Direccion exacta o numero del poste', 5, 10, 10, TRUE),
('HOYO_CALLE', 'Hoyo en la calle', 'Direccion y foto del dano', 10, 8, 8, TRUE),
('PODA_ARBOLES', 'Poda de arboles', 'Direccion y descripcion del arbol', 15, 5, 5, TRUE),
('RETIRO_CACHUREOS', 'Retiro de cachureos', 'Direccion y listado de objetos', 7, 3, 3, TRUE);
