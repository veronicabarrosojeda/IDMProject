-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 12, 2016 at 03:33 PM
-- Server version: 5.5.53-MariaDB-1ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `proyectoidm`
--

-- --------------------------------------------------------

--
-- Table structure for table `area`
--

CREATE TABLE IF NOT EXISTS `area` (
  `idArea` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `descripcion` varchar(256) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idArea`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `area`
--

-- --------------------------------------------------------

--
-- Table structure for table `barrio`
--

CREATE TABLE IF NOT EXISTS `barrio` (
  `idBarrio` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `descripcion` varchar(256) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  `idMunicipio` int(11) NOT NULL,
  PRIMARY KEY (`idBarrio`),
  KEY `idMunicipio` (`idMunicipio`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `barrio`
--

-- --------------------------------------------------------

--
-- Table structure for table `empresa`
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
-- Dumping data for table `empresa`
--

-- --------------------------------------------------------

--
-- Table structure for table `historialservicio`
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `historialservicio`
--

-- --------------------------------------------------------

--
-- Table structure for table `municipio`
--

CREATE TABLE IF NOT EXISTS `municipio` (
  `idMunicipio` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `descripcion` varchar(256) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idMunicipio`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `municipio`
--

-- --------------------------------------------------------

--
-- Table structure for table `notificaciones`
--

CREATE TABLE IF NOT EXISTS `notificaciones` (
  `idNotificaciones` bigint(20) NOT NULL AUTO_INCREMENT,
  `asunto` varchar(100) DEFAULT NULL,
  `mensaje` varchar(1000) DEFAULT NULL,
  `correo` varchar(80) NOT NULL,
  `enviado` varchar(1) NOT NULL,
  PRIMARY KEY (`idNotificaciones`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `notificaciones`
--

-- --------------------------------------------------------

--
-- Table structure for table `permiso`
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
-- Dumping data for table `permiso`
--

-- --------------------------------------------------------

--
-- Table structure for table `rol`
--

CREATE TABLE IF NOT EXISTS `rol` (
  `idRol` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `descripcion` varchar(256) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idRol`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `rol`
--

-- --------------------------------------------------------

--
-- Table structure for table `rolpermiso`
--

CREATE TABLE IF NOT EXISTS `rolpermiso` (
  `idRol` int(11) NOT NULL,
  `idPermiso` int(11) NOT NULL,
  PRIMARY KEY (`idRol`,`idPermiso`),
  KEY `idPermiso` (`idPermiso`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rolpermiso`
--

-- --------------------------------------------------------

--
-- Table structure for table `servicio`
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `servicio`
--

--
-- Triggers `servicio`
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
-- Table structure for table `servicioempresa`
--

CREATE TABLE IF NOT EXISTS `servicioempresa` (
  `idEmpresa` int(11) NOT NULL,
  `idServicio` bigint(11) NOT NULL,
  PRIMARY KEY (`idEmpresa`,`idServicio`),
  KEY `idServicio` (`idServicio`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `servicioempresa`
--

-- --------------------------------------------------------

--
-- Table structure for table `serviciosolicitante`
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `serviciosolicitante`
--

-- --------------------------------------------------------

--
-- Table structure for table `subarea`
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
-- Dumping data for table `subarea`
--

-- --------------------------------------------------------

--
-- Table structure for table `supervisor`
--

CREATE TABLE IF NOT EXISTS `supervisor` (
  `idSupervisor` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `apellido` varchar(25) NOT NULL,
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idSupervisor`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `supervisor`
--

-- --------------------------------------------------------

--
-- Table structure for table `supervisorservicio`
--

CREATE TABLE IF NOT EXISTS `supervisorservicio` (
  `idSupervisor` int(11) NOT NULL,
  `idServicio` bigint(11) NOT NULL,
  PRIMARY KEY (`idSupervisor`,`idServicio`),
  KEY `idServicio` (`idServicio`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supervisorservicio`
--

-- --------------------------------------------------------

--
-- Table structure for table `tipo`
--

CREATE TABLE IF NOT EXISTS `tipo` (
  `idTipo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `descripcion` varchar(256) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idTipo`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `tipo`
--

-- --------------------------------------------------------

--
-- Table structure for table `tipodetalle`
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
-- Dumping data for table `tipodetalle`
--

-- --------------------------------------------------------

--
-- Table structure for table `tiposervicio`
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
-- Dumping data for table `tiposervicio`
--

-- --------------------------------------------------------

--
-- Table structure for table `ubicacionservicio`
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `ubicacionservicio`
--

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
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
-- Dumping data for table `usuario`
--

-- --------------------------------------------------------

--
-- Table structure for table `usuarioempresa`
--

CREATE TABLE IF NOT EXISTS `usuarioempresa` (
  `idUsuario` int(11) NOT NULL,
  `idEmpresa` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`,`idEmpresa`),
  KEY `idEmpresa` (`idEmpresa`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuarioempresa`
--

-- --------------------------------------------------------

--
-- Stand-in structure for view `viewhistorialservicio`
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
-- Stand-in structure for view `viewservicio`
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
-- Structure for view `viewhistorialservicio`
--
DROP TABLE IF EXISTS `viewhistorialservicio`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `viewhistorialservicio` AS (select `hs`.`idHistorialServicio` AS `idHistorialServicio`,`hs`.`descripcion` AS `descripcion`,`hs`.`estado` AS `estado`,`hs`.`fechaModificacion` AS `fechaModificacion`,`hs`.`idServicio` AS `idServicio`,`hs`.`idTipoServicio` AS `idTipoServicio`,`hs`.`fechaIngreso` AS `fechaIngreso`,`ts`.`nombre` AS `nombreTipo`,`td`.`nombre` AS `nombreEstado`,`hs`.`observaciones` AS `observaciones` from ((`historialservicio` `hs` join `tiposervicio` `ts` on((`hs`.`idTipoServicio` = `ts`.`idTipoServicio`))) join `tipodetalle` `td` on((`hs`.`estado` = `td`.`idTipoDetalle`))) where (`td`.`idTipo` = 2));

-- --------------------------------------------------------

--
-- Structure for view `viewservicio`
--
DROP TABLE IF EXISTS `viewservicio`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `viewservicio` AS (select `ser`.`idServicio` AS `idServicio`,`ser`.`descripcion` AS `descripcionServicio`,`ser`.`estado` AS `estado`,`tid`.`nombre` AS `nombre`,`ser`.`idTipoServicio` AS `idTipoServicio`,`tis`.`nombre` AS `nombreTipoServicio`,`tis`.`descripcion` AS `descripcionTipoServicio`,`tis`.`idArea` AS `idArea`,`ar`.`nombre` AS `nombreArea`,`ser`.`idUbicacionServicio` AS `idUbicacionServicio`,`ubs`.`calle` AS `calle`,`ubs`.`nroPuerta` AS `nroPuerta`,`ubs`.`apto` AS `apto`,`ubs`.`entreCalles` AS `entreCalles`,`ubs`.`nroManzana` AS `nroManzana`,`ubs`.`nroSolar` AS `nroSolar`,`ubs`.`nroPadron` AS `nroPadron`,`ubs`.`latitud` AS `latitud`,`ubs`.`longitud` AS `longitud`,`ubs`.`idBarrio` AS `idBarrio`,`ba`.`nombre` AS `nombreBarrio`,`mu`.`idMunicipio` AS `idMunicipio`,`mu`.`nombre` AS `nombreMunicipiom`,`ser`.`fechaIngreso` AS `fechaIngreso`,`ser`.`fechaModificacion` AS `fechaModificacion`,`ser`.`tipoOrigen` AS `tipoOrigen`,`ser`.`observaciones` AS `observaciones`,`ser`.`rutaImagen` AS `rutaImagen` from ((((((`servicio` `ser` join `ubicacionservicio` `ubs` on((`ser`.`idUbicacionServicio` = `ubs`.`idUbicacion`))) join `tiposervicio` `tis` on((`ser`.`idTipoServicio` = `tis`.`idTipoServicio`))) join `area` `ar` on((`tis`.`idArea` = `ar`.`idArea`))) join `tipodetalle` `tid` on((`ser`.`estado` = `tid`.`idTipoDetalle`))) left join `barrio` `ba` on((`ubs`.`idBarrio` = `ba`.`idBarrio`))) left join `municipio` `mu` on((`ba`.`idMunicipio` = `mu`.`idMunicipio`))) where (`tid`.`idTipo` = 2));

--
-- Constraints for dumped tables
--

--
-- Constraints for table `barrio`
--
ALTER TABLE `barrio`
  ADD CONSTRAINT `barrio_ibfk_1` FOREIGN KEY (`idMunicipio`) REFERENCES `municipio` (`idMunicipio`);

--
-- Constraints for table `historialservicio`
--
ALTER TABLE `historialservicio`
  ADD CONSTRAINT `historialservicio_ibfk_1` FOREIGN KEY (`idServicio`) REFERENCES `servicio` (`idServicio`);

--
-- Constraints for table `rolpermiso`
--
ALTER TABLE `rolpermiso`
  ADD CONSTRAINT `rolPermiso_ibfk_1` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`),
  ADD CONSTRAINT `rolPermiso_ibfk_2` FOREIGN KEY (`idPermiso`) REFERENCES `permiso` (`idPermiso`);

--
-- Constraints for table `servicio`
--
ALTER TABLE `servicio`
  ADD CONSTRAINT `servicio_ibfk_1` FOREIGN KEY (`idTipoServicio`) REFERENCES `tiposervicio` (`idTipoServicio`),
  ADD CONSTRAINT `servicio_ibfk_2` FOREIGN KEY (`idUbicacionServicio`) REFERENCES `ubicacionservicio` (`idUbicacion`);

--
-- Constraints for table `servicioempresa`
--
ALTER TABLE `servicioempresa`
  ADD CONSTRAINT `servicioEmpresa_ibfk_1` FOREIGN KEY (`idEmpresa`) REFERENCES `empresa` (`idEmpresa`),
  ADD CONSTRAINT `servicioEmpresa_ibfk_2` FOREIGN KEY (`idServicio`) REFERENCES `servicio` (`idServicio`);

--
-- Constraints for table `serviciosolicitante`
--
ALTER TABLE `serviciosolicitante`
  ADD CONSTRAINT `serviciosolicitante_ibfk_1` FOREIGN KEY (`idServicio`) REFERENCES `servicio` (`idServicio`),
  ADD CONSTRAINT `serviciosolicitante_ibfk_2` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`);

--
-- Constraints for table `subarea`
--
ALTER TABLE `subarea`
  ADD CONSTRAINT `subarea_ibfk_1` FOREIGN KEY (`idArea`) REFERENCES `area` (`idArea`);

--
-- Constraints for table `supervisorservicio`
--
ALTER TABLE `supervisorservicio`
  ADD CONSTRAINT `supervisorServicio_ibfk_1` FOREIGN KEY (`idSupervisor`) REFERENCES `supervisor` (`idSupervisor`),
  ADD CONSTRAINT `supervisorServicio_ibfk_3` FOREIGN KEY (`idServicio`) REFERENCES `servicio` (`idServicio`);

--
-- Constraints for table `tipodetalle`
--
ALTER TABLE `tipodetalle`
  ADD CONSTRAINT `tipoDetalle_ibfk_1` FOREIGN KEY (`idTipo`) REFERENCES `tipo` (`idTipo`);

--
-- Constraints for table `tiposervicio`
--
ALTER TABLE `tiposervicio`
  ADD CONSTRAINT `tipoServicio_ibfk_1` FOREIGN KEY (`idArea`) REFERENCES `area` (`idArea`);

--
-- Constraints for table `ubicacionservicio`
--
ALTER TABLE `ubicacionservicio`
  ADD CONSTRAINT `ubicacionServicio_ibfk_1` FOREIGN KEY (`idBarrio`) REFERENCES `barrio` (`idBarrio`);

--
-- Constraints for table `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`);

--
-- Constraints for table `usuarioempresa`
--
ALTER TABLE `usuarioempresa`
  ADD CONSTRAINT `usuarioEmpresa_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`),
  ADD CONSTRAINT `usuarioEmpresa_ibfk_2` FOREIGN KEY (`idEmpresa`) REFERENCES `empresa` (`idEmpresa`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
