-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 15-12-2016 a las 22:06:38
-- Versión del servidor: 5.5.52-MariaDB-1ubuntu0.14.04.1
-- Versión de PHP: 5.5.9-1ubuntu4.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `proyectoidm`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `area`
--

CREATE TABLE IF NOT EXISTS `area` (
  `idArea` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `descripcion` varchar(256) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idArea`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Volcado de datos para la tabla `area`
--

INSERT INTO `area` (`idArea`, `nombre`, `descripcion`, `estado`) VALUES
(1, 'Higiene', 'Area encargada de la higiene ', 1),
(2, 'Transito', 'Area encargada del transito', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `barrio`
--

CREATE TABLE IF NOT EXISTS `barrio` (
  `idBarrio` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `descripcion` varchar(256) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  `idMunicipio` int(11) NOT NULL,
  PRIMARY KEY (`idBarrio`),
  KEY `idMunicipio` (`idMunicipio`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=104 ;

--
-- Volcado de datos para la tabla `barrio`
--

INSERT INTO `barrio` (`idBarrio`, `nombre`, `descripcion`, `estado`, `idMunicipio`) VALUES
(1, 'Jardines de Córdoba', 'Jardines de Córdoba', 1, 1),
(2, 'Monaco', 'Barrio cercano a la terminal', 1, 2),
(3, 'Maldonado Nuevo', NULL, 1, 2),
(4, 'San Francisco', NULL, 1, 2),
(5, 'San Fernando', NULL, 1, 2),
(6, 'Biarritz', NULL, 1, 2),
(7, 'Maldonado Park', NULL, 1, 2),
(8, 'San Antonio 1', NULL, 1, 2),
(9, 'San Antonio 2', NULL, 1, 2),
(10, 'San Antonio 3', NULL, 1, 2),
(11, 'San Antonio 4', NULL, 1, 2),
(12, 'San Antonio 5', NULL, 1, 2),
(13, 'Cerro Pelado', NULL, 1, 2),
(14, 'Sarubbi', NULL, 1, 2),
(15, 'La Sonrisa', NULL, 1, 2),
(16, 'Iporá', NULL, 1, 2),
(17, 'La Fortuna', NULL, 1, 2),
(18, 'Altos de la Laguna', NULL, 1, 2),
(19, 'MIguez', NULL, 1, 2),
(20, 'Mussio', NULL, 1, 2),
(21, 'El Vigia', NULL, 1, 2),
(22, 'Los Olivos', NULL, 1, 2),
(23, 'Las Marias', NULL, 1, 2),
(24, 'Perlitas', NULL, 1, 2),
(25, 'Odizzio', NULL, 1, 2),
(26, 'Lavalleja', NULL, 1, 2),
(27, 'Las Cooperativas', NULL, 1, 2),
(28, 'La Cadelaria', NULL, 1, 2),
(29, 'San Gabriel', NULL, 1, 2),
(30, 'Lausana', NULL, 1, 2),
(31, 'Pinares', NULL, 1, 2),
(32, 'Nuevo Cantegril', NULL, 1, 2),
(33, 'Jaurena', NULL, 1, 2),
(34, 'Inve', NULL, 1, 2),
(35, 'Incar', NULL, 1, 2),
(36, 'Cantegril', NULL, 1, 1),
(37, 'Jaguel', NULL, 1, 1),
(38, 'Marly', NULL, 1, 1),
(39, 'Los Ángeles', NULL, 1, 1),
(40, 'Lobos', NULL, 1, 1),
(41, 'Aidy Grill', NULL, 1, 1),
(42, 'El Golf', NULL, 1, 1),
(43, 'El Placer', NULL, 1, 1),
(44, 'Deauville', NULL, 1, 1),
(45, 'Marconi', NULL, 1, 1),
(46, 'Beverly Hills', NULL, 1, 1),
(47, 'Kennedy', NULL, 1, 1),
(48, 'Rincón del Indio', NULL, 1, 1),
(49, 'Centro', NULL, 1, 6),
(50, 'Palermo', NULL, 1, 6),
(51, 'Maurente', NULL, 1, 6),
(52, 'Arturo Matta', NULL, 1, 6),
(53, 'Parque Anita', NULL, 1, 6),
(54, 'La Cuchilla', NULL, 1, 6),
(55, 'Figoli', NULL, 1, 6),
(56, 'Abasolo', NULL, 1, 6),
(57, 'Lavagna', NULL, 1, 6),
(58, 'Martín Vidal', NULL, 1, 6),
(59, 'Adams', NULL, 1, 6),
(60, 'América y Bauza', NULL, 1, 6),
(61, 'Ángel de León', NULL, 1, 6),
(62, 'La Estación', NULL, 1, 6),
(63, 'Capandeguy', NULL, 1, 6),
(64, 'Francisco Perez', NULL, 1, 6),
(65, 'Anibal Amorin', NULL, 1, 6),
(66, 'Rodriguez Barrios', NULL, 1, 6),
(67, 'Francisco De León', NULL, 1, 6),
(68, 'Asturias', NULL, 1, 6),
(69, 'Sanabria', NULL, 1, 6),
(70, 'Halty', NULL, 1, 6),
(71, 'La Boca', NULL, 1, 6),
(72, 'Vialidad', NULL, 1, 6),
(73, '17 Ombues', NULL, 1, 6),
(74, 'Huelmo', NULL, 1, 6),
(75, 'Del Virrey', NULL, 1, 6),
(76, 'Quinta de Medina', NULL, 1, 6),
(77, 'Valdeolmos', NULL, 1, 6),
(78, 'Las Delicias', NULL, 1, 2),
(79, 'Ocean Park', NULL, 1, 5),
(80, 'La Capuera', NULL, 1, 5),
(81, 'Punta Colorada', NULL, 1, 5),
(82, 'Punta Negra', NULL, 1, 5),
(83, 'Sauce de Portezuelo', NULL, 1, 5),
(84, 'La Falda', NULL, 1, 5),
(85, 'Playa Hermosa', NULL, 1, 5),
(86, 'Playa Grande', NULL, 1, 5),
(87, 'Playa Verde', NULL, 1, 5),
(88, 'El Toro', NULL, 1, 5),
(89, 'Village del Faro', NULL, 1, 9),
(92, 'Otro', NULL, 1, 2),
(94, 'Otro', NULL, 1, 3),
(95, 'Otro', NULL, 1, 5),
(97, 'Otro', NULL, 1, 6),
(99, 'Otro', NULL, 1, 8),
(102, 'Otro', NULL, 1, 9),
(103, 'Otro', NULL, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE IF NOT EXISTS `empresa` (
  `idEmpresa` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `rut` int(11) NOT NULL,
  `descripcion` varchar(256) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idEmpresa`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`idEmpresa`, `nombre`, `rut`, `descripcion`, `estado`) VALUES
(1, 'IDM', 345456547, 'Intendencia Departamental de Maldonado', 1),
(2, 'Barrido S.A.', 435456976, 'Empresa de barrido', 1),
(3, 'Ecotecno', 12345678, 'Empresa encargada de la gestion de contenedores', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historialservicio`
--

CREATE TABLE IF NOT EXISTS `historialservicio` (
  `idHistorialServicio` bigint(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(256) DEFAULT NULL,
  `estado` smallint(1) DEFAULT NULL,
  `fechaModificacion` date DEFAULT NULL,
  `idServicio` bigint(11) DEFAULT NULL,
  `idTipoServicio` int(11) DEFAULT NULL,
  `fechaIngreso` date DEFAULT NULL,
  `observaciones` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`idHistorialServicio`),
  KEY `idServicio` (`idServicio`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=44 ;

--
-- Volcado de datos para la tabla `historialservicio`
--

INSERT INTO `historialservicio` (`idHistorialServicio`, `descripcion`, `estado`, `fechaModificacion`, `idServicio`, `idTipoServicio`, `fechaIngreso`, `observaciones`) VALUES
(39, 'basura acumulada en la esquina', 3, '2016-12-15', 18, 2, '2016-12-15', 'A inspeccionar'),
(40, 'Mucha basura', 5, '2016-12-15', 19, 10, '2016-12-15', 'Inspeccionado'),
(41, 'Falta papelera en la cuadra', 2, '2016-12-15', 20, 14, '2016-12-15', 'Servicio finalizado exitosamente.'),
(42, 'basura acumulada en la esquina', 3, '2016-12-15', 18, 2, '2016-12-15', 'A inspeccionar'),
(43, 'Mucha basura', 5, '2016-12-15', 19, 10, '2016-12-15', 'Inspeccionado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `municipio`
--

CREATE TABLE IF NOT EXISTS `municipio` (
  `idMunicipio` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `descripcion` varchar(256) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idMunicipio`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Volcado de datos para la tabla `municipio`
--

INSERT INTO `municipio` (`idMunicipio`, `nombre`, `descripcion`, `estado`) VALUES
(1, 'Punta del Este', 'Municipio de Punta del Este', 1),
(2, 'Maldonado', 'Municipio de maldonado', 1),
(3, 'Aigua', NULL, 1),
(5, 'Piriápolis', NULL, 1),
(6, 'San Carlos', NULL, 1),
(8, 'Solís', NULL, 1),
(9, 'Garzón y José Ignacio', NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notificaciones`
--

CREATE TABLE IF NOT EXISTS `notificaciones` (
  `idNotificaciones` bigint(20) NOT NULL AUTO_INCREMENT,
  `asunto` varchar(100) DEFAULT NULL,
  `mensaje` varchar(1000) DEFAULT NULL,
  `correo` varchar(80) NOT NULL,
  `enviado` varchar(1) NOT NULL,
  PRIMARY KEY (`idNotificaciones`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Volcado de datos para la tabla `notificaciones`
--

INSERT INTO `notificaciones` (`idNotificaciones`, `asunto`, `mensaje`, `correo`, `enviado`) VALUES
(1, NULL, 'Prueba', 'christianconti95@gmail.com', 'S'),
(2, NULL, 'Prueba2', 'facundo24uru@gmail.com', 'S'),
(3, NULL, 'Prueba3', 'facundo24uru@gmail.com', 'S'),
(4, NULL, '<h2>Notificación de solicitud de servicio</h2><p>La solicitud de servicio número: 1 se encuentra en estado Finalizado. Se continuará informando de los cambios de estado de su solicitud.</p>', 'facundo24uru@gmail.com', 'N'),
(5, NULL, '<style type=''text/css''>body {margin: 0; padding: 0; min-width: 100%!important;}.content {width: 100%; max-width: 600px;}</style><body yahoo bgcolor=''#f6f8f1''><table width=''100%'' bgcolor=''#f6f8f1'' border=''0'' cellpadding=''0'' cellspacing=''0''><tr><td><center><h1>ATuServicio IDM</h1></center></td><tr><td><center><img style=''width:150px;'' src=''https://pbs.twimg.com/profile_images/639187735891329024/zT4n4ld5.jpg''/></center></td></tr><tr><td><table class=''content'' align=''center'' cellpadding=''0'' cellspacing=''0'' border=''0''><tr><td><h2>Notificación de solicitud de servicio</h2></td></tr><tr><td><p style=''font-size:18px;''>La solicitud de servicio número: <b>1</b> se encuentra en estado <b>En espera</b>.Se continuará informando sobre los cambios de estado de su solicitud.</p></td></tr></table></td></tr></table></body>', 'facundo24uru@gmail.com', 'N'),
(6, NULL, '<style type=''text/css''>body {margin: 0; padding: 0; min-width: 100%!important;}.content {width: 100%; max-width: 600px;}</style><body yahoo bgcolor=''#f6f8f1''><table width=''100%'' bgcolor=''#f6f8f1'' border=''0'' cellpadding=''0'' cellspacing=''0''><tr><td><center><h1>ATuServicio IDM</h1></center></td><tr><td><center><img style=''width:150px;'' src=''https://pbs.twimg.com/profile_images/639187735891329024/zT4n4ld5.jpg''/></center></td></tr><tr><td><table class=''content'' align=''center'' cellpadding=''0'' cellspacing=''0'' border=''0''><tr><td><h2>Notificación de solicitud de servicio</h2></td></tr><tr><td><p style=''font-size:18px;''>La solicitud de servicio número: <b>1</b> se encuentra en estado <b>Transito</b>.Se continuará informando sobre los cambios de estado de su solicitud.</p></td></tr></table></td></tr></table></body>', 'facundo24uru@gmail.com', 'N'),
(7, NULL, '<style type=''text/css''>body {margin: 0; padding: 0; min-width: 100%!important;}.content {width: 100%; max-width: 600px;}</style><body yahoo bgcolor=''#f6f8f1''><table width=''100%'' bgcolor=''#f6f8f1'' border=''0'' cellpadding=''0'' cellspacing=''0''><tr><td><center><h1>ATuServicio IDM</h1></center></td><tr><td><center><img style=''width:150px;'' src=''https://pbs.twimg.com/profile_images/639187735891329024/zT4n4ld5.jpg''/></center></td></tr><tr><td><table class=''content'' align=''center'' cellpadding=''0'' cellspacing=''0'' border=''0''><tr><td><h2>Notificación de solicitud de servicio</h2></td></tr><tr><td><p style=''font-size:18px;''>La solicitud de servicio número: <b>1</b> se encuentra en estado <b>En espera</b>.Se continuará informando sobre los cambios de estado de su solicitud.</p></td></tr></table></td></tr></table></body>', 'facundo24uru@gmail.com', 'N');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `permiso`
--

CREATE TABLE IF NOT EXISTS `permiso` (
  `idPermiso` int(11) NOT NULL AUTO_INCREMENT,
  `nombreUnico` varchar(100) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `url` varchar(100) DEFAULT NULL,
  `descripcion` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`idPermiso`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=29 ;

--
-- Volcado de datos para la tabla `permiso`
--

INSERT INTO `permiso` (`idPermiso`, `nombreUnico`, `nombre`, `estado`, `url`, `descripcion`) VALUES
(1, 'Muu_Mantenimiento', 'Menú Mantenimiento', 1, NULL, ''),
(2, 'Muu_Repotes', 'Menú Reportes', 1, NULL, ''),
(3, 'Page_Municipio', 'Municipio', 1, '', NULL),
(4, 'Page_Area', 'Area', 1, '', NULL),
(5, 'Page_Area', 'Area', 1, '', NULL),
(6, 'Mmu_Registro', 'Registro', 1, '', NULL),
(7, 'Page_Usuario', 'Usuarios', 1, '', NULL),
(8, 'Muu_Seguridad', 'Menú Seguridad', 1, NULL, NULL),
(9, 'Page_Permisos', 'Permisos', 1, NULL, NULL),
(10, 'Page_Roles', 'Roles', 1, NULL, NULL),
(11, 'Page_CambioDeClave', 'Cambio de Clave', 1, NULL, NULL),
(12, 'Page_MapaSolicitudes', 'Mapa de Solicitudes', 1, NULL, NULL),
(13, 'Page_Supervisor', 'Supervisores', 1, NULL, NULL),
(14, 'Muu_Servicios', 'Menú Servicios', 1, NULL, NULL),
(15, 'Page_ListSol', 'Listado de Solicitudes', 1, NULL, NULL),
(16, 'Page_Reporte_QtSol', 'Catnidad de Solicitudes', 1, NULL, NULL),
(17, 'Page_AltaServicio', 'Alta Servicio', 1, NULL, NULL),
(18, 'Page_Empresa', 'Empresas', 1, NULL, NULL),
(19, 'Page_ReporteSolicitudesPorEstado', 'Reporte por Estado', 1, NULL, NULL),
(20, 'Page_TipoServicio', 'Tipo de Servicio', 1, NULL, NULL),
(21, 'Page_Barrio', 'Barrio', 1, NULL, NULL),
(22, 'Page_AsignarEmpresa_Servicio', 'Asignar Empresa Servicio', 1, NULL, NULL),
(23, 'Page_Asignar_Supervisor', 'Asignar Supervisor', 1, NULL, NULL),
(24, 'Page_Ver_Solicitantes', 'Ver Solicitantes', 1, NULL, NULL),
(25, 'Page_Ver_Servicio', 'Ver Servicio', 1, NULL, NULL),
(26, 'Page_Empresas_Usuario', 'Asignar Empresas Usuario', 1, NULL, NULL),
(27, 'Page_Mapa_Solicitudes', 'Mapa de Solicitudes', 1, NULL, NULL),
(28, 'Page_Servicios_Por_Supervisor', 'Servicios Por Supervisor', 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE IF NOT EXISTS `rol` (
  `idRol` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `descripcion` varchar(256) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idRol`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`idRol`, `nombre`, `descripcion`, `estado`) VALUES
(1, 'Administrador', 'Administrador del sistema', 1),
(2, 'Entidades Externas', 'Empresas', 1),
(3, 'Movil', 'Usuarios de la Aplicacion Movil', 1),
(4, 'Sistemas Externos', 'Consumidores de los web service', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rolpermiso`
--

CREATE TABLE IF NOT EXISTS `rolpermiso` (
  `idRol` int(11) NOT NULL,
  `idPermiso` int(11) NOT NULL,
  PRIMARY KEY (`idRol`,`idPermiso`),
  KEY `idPermiso` (`idPermiso`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `rolpermiso`
--

INSERT INTO `rolpermiso` (`idRol`, `idPermiso`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(1, 14),
(1, 15),
(1, 16),
(1, 17),
(1, 18),
(1, 19),
(1, 20),
(1, 21),
(1, 22),
(1, 23),
(1, 24),
(1, 25),
(1, 26),
(1, 27),
(1, 28),
(2, 8),
(2, 11),
(2, 12),
(2, 14),
(2, 15),
(2, 25);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicio`
--

CREATE TABLE IF NOT EXISTS `servicio` (
  `idServicio` bigint(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(256) DEFAULT NULL,
  `rutaImagen` varchar(200) DEFAULT NULL,
  `estado` smallint(1) NOT NULL,
  `fechaIngreso` date NOT NULL,
  `fechaModificacion` date NOT NULL,
  `idTipoServicio` int(11) NOT NULL,
  `idUbicacionServicio` int(11) NOT NULL,
  `idUsuarioFuncionario` int(11) DEFAULT NULL,
  `tipoOrigen` int(11) DEFAULT NULL,
  `observaciones` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`idServicio`),
  KEY `idTipoServicio` (`idTipoServicio`),
  KEY `idUbicacionServicio` (`idUbicacionServicio`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=21 ;

--
-- Volcado de datos para la tabla `servicio`
--

INSERT INTO `servicio` (`idServicio`, `descripcion`, `rutaImagen`, `estado`, `fechaIngreso`, `fechaModificacion`, `idTipoServicio`, `idUbicacionServicio`, `idUsuarioFuncionario`, `tipoOrigen`, `observaciones`) VALUES
(18, 'basura acumulada en la esquina', '/ServerApp/STORAGE/18.jpg', 3, '2016-12-15', '2016-12-15', 2, 18, 1, 1, 'A inspeccionar'),
(19, 'Mucha basura', '/ServerApp/STORAGE/19.jpg', 5, '2016-12-15', '2016-12-15', 10, 19, 1, 1, 'Inspeccionado'),
(20, 'Falta papelera en la cuadra', NULL, 2, '2016-12-15', '2016-12-15', 14, 20, 1, 1, 'Servicio finalizado exitosamente.');

--
-- Disparadores `servicio`
--
DROP TRIGGER IF EXISTS `Asignar_Empresa`;
DELIMITER //
CREATE TRIGGER `Asignar_Empresa` AFTER INSERT ON `servicio`
 FOR EACH ROW begin
 
DECLARE idEmp int;
 Select ts.idEmpresa into idEmp from tiposervicio as ts where ts.idTipoServicio=NEW.idTipoServicio;

IF idEmp IS NULL THEN
	SET idEmp = 1;
END IF;

 Insert into servicioempresa (idServicio, idEmpresa) values(NEW.idServicio, idEmp);


end
//
DELIMITER ;
DROP TRIGGER IF EXISTS `Update_Historial`;
DELIMITER //
CREATE TRIGGER `Update_Historial` AFTER UPDATE ON `servicio`
 FOR EACH ROW INSERT INTO historialservicio
       (idServicio,descripcion, estado, fechaModificacion,idTipoServicio, fechaIngreso, observaciones) 
VALUES (NEW.idServicio, NEW.descripcion, NEW.estado, NEW.fechaModificacion, NEW.idTipoServicio, NEW.fechaIngreso, NEW.observaciones)
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicioempresa`
--

CREATE TABLE IF NOT EXISTS `servicioempresa` (
  `idEmpresa` int(11) NOT NULL,
  `idServicio` bigint(11) NOT NULL,
  PRIMARY KEY (`idEmpresa`,`idServicio`),
  KEY `idServicio` (`idServicio`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `servicioempresa`
--

INSERT INTO `servicioempresa` (`idEmpresa`, `idServicio`) VALUES
(1, 18),
(1, 19),
(1, 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `serviciosolicitante`
--

CREATE TABLE IF NOT EXISTS `serviciosolicitante` (
  `idServicioSolicitante` bigint(20) NOT NULL AUTO_INCREMENT,
  `nroIdentidad` varchar(30) DEFAULT NULL,
  `tipoIdentidad` int(11) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `idServicio` bigint(20) NOT NULL,
  `idUsuario` int(11) DEFAULT NULL,
  `nombreSolicitante` varchar(100) DEFAULT NULL,
  `correoSolicitante` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idServicioSolicitante`),
  KEY `idServicio` (`idServicio`),
  KEY `idUsuario` (`idUsuario`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=21 ;

--
-- Volcado de datos para la tabla `serviciosolicitante`
--

INSERT INTO `serviciosolicitante` (`idServicioSolicitante`, `nroIdentidad`, `tipoIdentidad`, `telefono`, `idServicio`, `idUsuario`, `nombreSolicitante`, `correoSolicitante`) VALUES
(18, '45671238', 1, NULL, 18, NULL, NULL, NULL),
(19, '31234567', 1, NULL, 19, NULL, NULL, NULL),
(20, '54321237', 1, NULL, 20, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `subarea`
--

CREATE TABLE IF NOT EXISTS `subarea` (
  `idSubArea` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `descripcion` varchar(256) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  `idArea` int(11) NOT NULL,
  PRIMARY KEY (`idSubArea`,`idArea`),
  KEY `idArea` (`idArea`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `subarea`
--

INSERT INTO `subarea` (`idSubArea`, `nombre`, `descripcion`, `estado`, `idArea`) VALUES
(1, 'Aseo Urbano', 'Sub area de higiene encargda del aseo urbano', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `supervisor`
--

CREATE TABLE IF NOT EXISTS `supervisor` (
  `idSupervisor` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `apellido` varchar(25) NOT NULL,
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idSupervisor`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Volcado de datos para la tabla `supervisor`
--

INSERT INTO `supervisor` (`idSupervisor`, `nombre`, `apellido`, `estado`) VALUES
(1, 'Jose', 'Perez', 1),
(2, 'Pablo', 'Sanchez', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `supervisorservicio`
--

CREATE TABLE IF NOT EXISTS `supervisorservicio` (
  `idSupervisor` int(11) NOT NULL,
  `idServicio` bigint(11) NOT NULL,
  PRIMARY KEY (`idSupervisor`,`idServicio`),
  KEY `idServicio` (`idServicio`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo`
--

CREATE TABLE IF NOT EXISTS `tipo` (
  `idTipo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `descripcion` varchar(256) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idTipo`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `tipo`
--

INSERT INTO `tipo` (`idTipo`, `nombre`, `descripcion`, `estado`) VALUES
(1, 'Tipo de Identidad', 'Son los tipos de identidades', 1),
(2, 'Estados de Servicio', 'Estados de las solicitudes de servicio', 1),
(3, 'Tipo de origen', 'Tipos de orígenes de un servicio', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipodetalle`
--

CREATE TABLE IF NOT EXISTS `tipodetalle` (
  `idTipoDetalle` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(256) DEFAULT NULL,
  `idTipo` int(11) NOT NULL,
  PRIMARY KEY (`idTipoDetalle`,`idTipo`),
  KEY `idTipo` (`idTipo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tipodetalle`
--

INSERT INTO `tipodetalle` (`idTipoDetalle`, `nombre`, `descripcion`, `idTipo`) VALUES
(1, 'Cédula Uruguaya', 'Identidad en la R.O.U', 1),
(1, 'Pendiente', 'Estado Pendiente', 2),
(1, 'Panel de Administraación', NULL, 3),
(2, 'Finalizado', 'Estado Resuelto\r\n', 2),
(2, 'Aplicación Movil', NULL, 3),
(3, 'En espera', NULL, 2),
(4, 'Cancelado', NULL, 2),
(5, 'Transito', NULL, 2),
(6, 'DNI', NULL, 1),
(7, 'RUT', NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiposervicio`
--

CREATE TABLE IF NOT EXISTS `tiposervicio` (
  `idTipoServicio` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(256) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  `idArea` int(11) DEFAULT NULL,
  `idEmpresa` int(11) DEFAULT NULL,
  PRIMARY KEY (`idTipoServicio`),
  KEY `idArea` (`idArea`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=25 ;

--
-- Volcado de datos para la tabla `tiposervicio`
--

INSERT INTO `tiposervicio` (`idTipoServicio`, `nombre`, `descripcion`, `estado`, `idArea`, `idEmpresa`) VALUES
(2, 'Recolección no pasa', '', 1, 1, NULL),
(3, 'Contenedor lleno ', '', 1, 1, NULL),
(4, 'Contenedor roto', '', 1, 1, NULL),
(5, 'Contenedor sucio', '', 1, 1, NULL),
(6, 'Contenedor con diseminación afuera', '', 1, 1, NULL),
(7, 'Contenedor con voluminosos afuera', '', 1, 1, NULL),
(8, 'Calle sucia', '', 1, 1, NULL),
(9, 'Vereda sucia', '', 1, 1, NULL),
(10, 'Playa sucia', '', 1, 1, NULL),
(11, 'Papeleras de playa llenas', '', 1, 1, NULL),
(12, 'Papelera rota', '', 1, 1, NULL),
(13, 'Papelera sucia', '', 1, 1, NULL),
(14, 'Papelera faltante', '', 1, 1, NULL),
(15, 'Fumigación', '', 1, 1, NULL),
(16, 'Desratización', '', 1, 1, NULL),
(17, 'Árbol con riesgo de caída en espacio público', '', 1, 1, NULL),
(18, 'Árbol caído en espacio público', '', 1, 1, NULL),
(19, 'Corte de pasto en espacio público', '', 1, 1, NULL),
(20, 'Poda en espacio público', '', 1, 1, NULL),
(21, 'Basural en espacio público', '', 1, 1, NULL),
(22, 'Caja roll off de podas y ramas llena', '', 1, 1, NULL),
(23, 'Otro', '', 1, 1, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ubicacionservicio`
--

CREATE TABLE IF NOT EXISTS `ubicacionservicio` (
  `idUbicacion` int(11) NOT NULL AUTO_INCREMENT,
  `latitud` float(9,6) DEFAULT NULL,
  `calle` varchar(256) DEFAULT NULL,
  `nroPuerta` varchar(15) DEFAULT NULL,
  `apto` varchar(5) DEFAULT NULL,
  `entreCalles` varchar(256) DEFAULT NULL,
  `nroManzana` varchar(15) DEFAULT NULL,
  `nroSolar` varchar(15) DEFAULT NULL,
  `nroPadron` varchar(15) DEFAULT NULL,
  `idBarrio` int(11) DEFAULT NULL,
  `longitud` float(9,6) DEFAULT NULL,
  PRIMARY KEY (`idUbicacion`),
  KEY `idBarrio` (`idBarrio`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=21 ;

--
-- Volcado de datos para la tabla `ubicacionservicio`
--

INSERT INTO `ubicacionservicio` (`idUbicacion`, `latitud`, `calle`, `nroPuerta`, `apto`, `entreCalles`, `nroManzana`, `nroSolar`, `nroPadron`, `idBarrio`, `longitud`) VALUES
(1, NULL, 'Lope de Vega', NULL, '', 'Victor Hugo y Av. S', NULL, NULL, '', 1, NULL),
(2, -34.921089, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, -54.951881),
(3, -34.915405, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, -54.886986),
(4, -34.915897, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, -54.906986),
(5, -34.929089, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, -54.906986),
(18, -34.914124, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, -54.950016),
(19, -34.958099, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, -54.944485),
(20, NULL, 'Medina', NULL, NULL, 'Arturo Santana', NULL, NULL, NULL, 14, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `apellido` varchar(25) NOT NULL,
  `nickname` varchar(15) NOT NULL,
  `password` varchar(500) NOT NULL,
  `token` varchar(100) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  `nroIdentidad` varchar(30) DEFAULT NULL,
  `tipoIdentidad` int(11) DEFAULT NULL,
  `idRol` int(11) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `notificar` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `idRol` (`idRol`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nombre`, `apellido`, `nickname`, `password`, `token`, `correo`, `estado`, `nroIdentidad`, `tipoIdentidad`, `idRol`, `telefono`, `notificar`) VALUES
(1, 'admin', 'admin', 'admin', '81dc9bdb52d04dc20036dbd8313ed055', 'e39a69c0-3c0b-47d5-bbe5-bf22ac10978d', NULL, 1, NULL, NULL, 1, NULL, NULL),
(2, 'Ecotecno', 'Ecotecno', 'Ecotecno', '81dc9bdb52d04dc20036dbd8313ed055', 'a6a02e94-4b9c-492a-93e7-a2bd58381ed4', NULL, 1, NULL, NULL, 2, NULL, NULL),
(3, 'Sistema Externo', 'Sistema Externo', 'sistema', '81dc9bdb52d04dc20036dbd8313ed055', '390454fc-9e23-416b-a3fe-ccc9dd2a1413', '', 1, NULL, NULL, 4, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarioempresa`
--

CREATE TABLE IF NOT EXISTS `usuarioempresa` (
  `idUsuario` int(11) NOT NULL,
  `idEmpresa` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`,`idEmpresa`),
  KEY `idEmpresa` (`idEmpresa`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarioempresa`
--

INSERT INTO `usuarioempresa` (`idUsuario`, `idEmpresa`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 2),
(2, 3),
(3, 1),
(3, 2),
(3, 3);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `viewhistorialservicio`
--
CREATE TABLE IF NOT EXISTS `viewhistorialservicio` (
`idHistorialServicio` bigint(11)
,`descripcion` varchar(256)
,`estado` smallint(1)
,`fechaModificacion` date
,`idServicio` bigint(11)
,`idTipoServicio` int(11)
,`fechaIngreso` date
,`nombreTipo` varchar(100)
,`nombreEstado` varchar(50)
,`observaciones` varchar(512)
);
-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `viewservicio`
--
CREATE TABLE IF NOT EXISTS `viewservicio` (
`idServicio` bigint(11)
,`descripcionServicio` varchar(256)
,`estado` smallint(1)
,`nombre` varchar(50)
,`idTipoServicio` int(11)
,`nombreTipoServicio` varchar(100)
,`descripcionTipoServicio` varchar(256)
,`idArea` int(11)
,`nombreArea` varchar(25)
,`idUbicacionServicio` int(11)
,`calle` varchar(256)
,`nroPuerta` varchar(15)
,`apto` varchar(5)
,`entreCalles` varchar(256)
,`nroManzana` varchar(15)
,`nroSolar` varchar(15)
,`nroPadron` varchar(15)
,`latitud` float(9,6)
,`longitud` float(9,6)
,`idBarrio` int(11)
,`nombreBarrio` varchar(25)
,`idMunicipio` int(11)
,`nombreMunicipiom` varchar(25)
,`fechaIngreso` date
,`fechaModificacion` date
,`tipoOrigen` int(11)
,`observaciones` varchar(512)
,`rutaImagen` varchar(200)
);
-- --------------------------------------------------------

--
-- Estructura para la vista `viewhistorialservicio`
--
DROP TABLE IF EXISTS `viewhistorialservicio`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `viewhistorialservicio` AS (select `hs`.`idHistorialServicio` AS `idHistorialServicio`,`hs`.`descripcion` AS `descripcion`,`hs`.`estado` AS `estado`,`hs`.`fechaModificacion` AS `fechaModificacion`,`hs`.`idServicio` AS `idServicio`,`hs`.`idTipoServicio` AS `idTipoServicio`,`hs`.`fechaIngreso` AS `fechaIngreso`,`ts`.`nombre` AS `nombreTipo`,`td`.`nombre` AS `nombreEstado`,`hs`.`observaciones` AS `observaciones` from ((`historialservicio` `hs` join `tiposervicio` `ts` on((`hs`.`idTipoServicio` = `ts`.`idTipoServicio`))) join `tipodetalle` `td` on((`hs`.`estado` = `td`.`idTipoDetalle`))) where (`td`.`idTipo` = 2));

-- --------------------------------------------------------

--
-- Estructura para la vista `viewservicio`
--
DROP TABLE IF EXISTS `viewservicio`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `viewservicio` AS (select `ser`.`idServicio` AS `idServicio`,`ser`.`descripcion` AS `descripcionServicio`,`ser`.`estado` AS `estado`,`tid`.`nombre` AS `nombre`,`ser`.`idTipoServicio` AS `idTipoServicio`,`tis`.`nombre` AS `nombreTipoServicio`,`tis`.`descripcion` AS `descripcionTipoServicio`,`tis`.`idArea` AS `idArea`,`ar`.`nombre` AS `nombreArea`,`ser`.`idUbicacionServicio` AS `idUbicacionServicio`,`ubs`.`calle` AS `calle`,`ubs`.`nroPuerta` AS `nroPuerta`,`ubs`.`apto` AS `apto`,`ubs`.`entreCalles` AS `entreCalles`,`ubs`.`nroManzana` AS `nroManzana`,`ubs`.`nroSolar` AS `nroSolar`,`ubs`.`nroPadron` AS `nroPadron`,`ubs`.`latitud` AS `latitud`,`ubs`.`longitud` AS `longitud`,`ubs`.`idBarrio` AS `idBarrio`,`ba`.`nombre` AS `nombreBarrio`,`mu`.`idMunicipio` AS `idMunicipio`,`mu`.`nombre` AS `nombreMunicipiom`,`ser`.`fechaIngreso` AS `fechaIngreso`,`ser`.`fechaModificacion` AS `fechaModificacion`,`ser`.`tipoOrigen` AS `tipoOrigen`,`ser`.`observaciones` AS `observaciones`,`ser`.`rutaImagen` AS `rutaImagen` from ((((((`servicio` `ser` join `ubicacionservicio` `ubs` on((`ser`.`idUbicacionServicio` = `ubs`.`idUbicacion`))) join `tiposervicio` `tis` on((`ser`.`idTipoServicio` = `tis`.`idTipoServicio`))) join `area` `ar` on((`tis`.`idArea` = `ar`.`idArea`))) join `tipodetalle` `tid` on((`ser`.`estado` = `tid`.`idTipoDetalle`))) left join `barrio` `ba` on((`ubs`.`idBarrio` = `ba`.`idBarrio`))) left join `municipio` `mu` on((`ba`.`idMunicipio` = `mu`.`idMunicipio`))) where (`tid`.`idTipo` = 2));

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `barrio`
--
ALTER TABLE `barrio`
  ADD CONSTRAINT `barrio_ibfk_1` FOREIGN KEY (`idMunicipio`) REFERENCES `municipio` (`idMunicipio`);

--
-- Filtros para la tabla `historialservicio`
--
ALTER TABLE `historialservicio`
  ADD CONSTRAINT `historialservicio_ibfk_1` FOREIGN KEY (`idServicio`) REFERENCES `servicio` (`idServicio`);

--
-- Filtros para la tabla `rolpermiso`
--
ALTER TABLE `rolpermiso`
  ADD CONSTRAINT `rolPermiso_ibfk_1` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`),
  ADD CONSTRAINT `rolPermiso_ibfk_2` FOREIGN KEY (`idPermiso`) REFERENCES `permiso` (`idPermiso`);

--
-- Filtros para la tabla `servicio`
--
ALTER TABLE `servicio`
  ADD CONSTRAINT `servicio_ibfk_1` FOREIGN KEY (`idTipoServicio`) REFERENCES `tiposervicio` (`idTipoServicio`),
  ADD CONSTRAINT `servicio_ibfk_2` FOREIGN KEY (`idUbicacionServicio`) REFERENCES `ubicacionservicio` (`idUbicacion`);

--
-- Filtros para la tabla `servicioempresa`
--
ALTER TABLE `servicioempresa`
  ADD CONSTRAINT `servicioEmpresa_ibfk_1` FOREIGN KEY (`idEmpresa`) REFERENCES `empresa` (`idEmpresa`),
  ADD CONSTRAINT `servicioEmpresa_ibfk_2` FOREIGN KEY (`idServicio`) REFERENCES `servicio` (`idServicio`);

--
-- Filtros para la tabla `serviciosolicitante`
--
ALTER TABLE `serviciosolicitante`
  ADD CONSTRAINT `serviciosolicitante_ibfk_1` FOREIGN KEY (`idServicio`) REFERENCES `servicio` (`idServicio`),
  ADD CONSTRAINT `serviciosolicitante_ibfk_2` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`);

--
-- Filtros para la tabla `subarea`
--
ALTER TABLE `subarea`
  ADD CONSTRAINT `subarea_ibfk_1` FOREIGN KEY (`idArea`) REFERENCES `area` (`idArea`);

--
-- Filtros para la tabla `supervisorservicio`
--
ALTER TABLE `supervisorservicio`
  ADD CONSTRAINT `supervisorServicio_ibfk_1` FOREIGN KEY (`idSupervisor`) REFERENCES `supervisor` (`idSupervisor`),
  ADD CONSTRAINT `supervisorServicio_ibfk_3` FOREIGN KEY (`idServicio`) REFERENCES `servicio` (`idServicio`);

--
-- Filtros para la tabla `tipodetalle`
--
ALTER TABLE `tipodetalle`
  ADD CONSTRAINT `tipoDetalle_ibfk_1` FOREIGN KEY (`idTipo`) REFERENCES `tipo` (`idTipo`);

--
-- Filtros para la tabla `tiposervicio`
--
ALTER TABLE `tiposervicio`
  ADD CONSTRAINT `tipoServicio_ibfk_1` FOREIGN KEY (`idArea`) REFERENCES `area` (`idArea`);

--
-- Filtros para la tabla `ubicacionservicio`
--
ALTER TABLE `ubicacionservicio`
  ADD CONSTRAINT `ubicacionServicio_ibfk_1` FOREIGN KEY (`idBarrio`) REFERENCES `barrio` (`idBarrio`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`);

--
-- Filtros para la tabla `usuarioempresa`
--
ALTER TABLE `usuarioempresa`
  ADD CONSTRAINT `usuarioEmpresa_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`),
  ADD CONSTRAINT `usuarioEmpresa_ibfk_2` FOREIGN KEY (`idEmpresa`) REFERENCES `empresa` (`idEmpresa`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
