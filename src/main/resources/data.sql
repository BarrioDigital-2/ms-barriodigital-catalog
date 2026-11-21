-- Tipos de trámite iniciales (mismos códigos que el front).
-- INSERT IGNORE: si el código ya existe, no hace nada; así se puede reiniciar la app sin duplicar ni pisar datos
INSERT IGNORE INTO tipo_tramite (codigo, nombre, requisitos, plazo_estimado_dias, cupo_maximo, cupo_disponible, activo) VALUES
('CERTIFICADO_RESIDENCIA', 'Certificado de residencia', 'Cédula de identidad y comprobante de domicilio', 2, 20, 20, TRUE),
('LUMINARIA_APAGADA', 'Luminaria apagada', 'Dirección exacta o número del poste', 5, 10, 10, TRUE),
('HOYO_CALLE', 'Hoyo en la calle', 'Dirección y foto del daño', 10, 8, 8, TRUE),
('PODA_ARBOLES', 'Poda de árboles', 'Dirección y descripción del árbol', 15, 5, 5, TRUE),
('RETIRO_CACHUREOS', 'Retiro de cachureos', 'Dirección y listado de objetos', 7, 3, 3, TRUE);
