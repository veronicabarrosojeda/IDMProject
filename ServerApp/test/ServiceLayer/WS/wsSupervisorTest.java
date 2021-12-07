package ServiceLayer.WS;

import DataAccess.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class wsSupervisorTest {

    public wsSupervisorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteTipoServicio = s.createSQLQuery("DELETE FROM `tiposervicio`");
        Query deleteUbicacionServicio = s.createSQLQuery("DELETE FROM `ubicacionservicio`");
        Query deleteArea = s.createSQLQuery("DELETE FROM `area`");
        Query deleteBarrio = s.createSQLQuery("DELETE FROM `barrio`");
        Query deleteMunicipio = s.createSQLQuery("DELETE FROM `municipio`");
        Query deleteSupervisorServicio = s.createSQLQuery("DELETE FROM `supervisorservicio`");
        Query deleteServicioEmpresa = s.createSQLQuery("DELETE FROM `servicioempresa`");
        Query deleteEmpresa = s.createSQLQuery("DELETE FROM `empresa`");
        Query deleteServicio = s.createSQLQuery("DELETE FROM `servicio`");
        Query deleteSupervisor = s.createSQLQuery("DELETE FROM `supervisor`");
        Query setAutoIncrementSupervisor = s.createSQLQuery("ALTER TABLE `supervisor` AUTO_INCREMENT = 3");
        Query insertMunicipio = s.createSQLQuery("INSERT INTO `municipio` (`idMunicipio`, `nombre`, `descripcion`, `estado`) VALUES\n"
                + "(1, 'Punta Del Este', 'Municipio de Punta Del Este', 1),\n"
                + "(2, 'Maldonado', 'Maldonado', 1),\n"
                + "(3, 'La Barra', 'Municipio de la Barra', 1);");
        Query insertBarrios = s.createSQLQuery("INSERT INTO `barrio` (`idBarrio`, `nombre`, `descripcion`, `estado`, `idMunicipio`) VALUES\n"
                + "(1, 'Monaco', 'Barrio cerca de la terminal', 1, 2),\n"
                + "(2, 'Sarubi', 'Por la Cachimba',1, 2),\n"
                + "(3, 'Gorlero', 'Calle principal de punta', 1,1);");
        Query insertUbicacionServicio = s.createSQLQuery("INSERT INTO `ubicacionservicio` (`idUbicacion`, `latitud`, `calle`, `nroPuerta`, `apto`, `entreCalles`, `nroManzana`, `nroSolar`, `nroPadron`, `idBarrio`, `longitud`) VALUES\n"
                + "(1, -34.912678, NULL, NULL, NULL, 'Victor Hugo y Av. S', NULL, NULL, '', 1, -54.953152),\n"
                + "(2, -34.921089, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, -54.951881),\n"
                + "(3, -34.915405, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, -54.886986),\n"
                + "(4, -34.915897, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, -54.906986);");
        Query insertArea = s.createSQLQuery("INSERT INTO `area` (`idArea`, `nombre`, `descripcion`, `estado`) VALUES\n"
                + "(1, 'Higiene', 'Area de higiene', 1),\n"
                + "(2, 'Transito', 'Area de transito', 1),\n"
                + "(3, 'Salud', 'Area de salud', 1);");
        Query insertTipoServicio = s.createSQLQuery("INSERT INTO `tiposervicio` (`idTipoServicio`, `nombre`, `descripcion`, `estado`, `idArea`, `idEmpresa`) VALUES\n"
                + "(2, 'DESRATIZACION', '', 1, 1,NULL),\n"
                + "(3, 'EXTRACCION ARBOL ESPACIO PUBLICO', '', 1, 1,NULL),\n"
                + "(4, 'FINCA RUINOSA', '', 1, 1,NULL),\n"
                + "(5, 'FUMIGACION', '', 1, 1,NULL);");
        Query insertEmpresa = s.createSQLQuery("INSERT INTO `empresa` (`idEmpresa`, `nombre`, `rut`, `descripcion`, `estado`) VALUES\n"
                + "(1, 'IDM', 345456547, 'Intendencia Departamental de Maldonado', 1),\n"
                + "(2, 'Barrido S.A.', 435456976, 'Empresa de barrido', 1),\n"
                + "(3, 'Ecotecno', 343, 'Empresa encargada de la gestion de contenedores', 1);");
        Query insertServicio = s.createSQLQuery("INSERT INTO `servicio` (`idServicio`, `descripcion`, `rutaImagen`, `estado`, `fechaIngreso`, `fechaModificacion`, `idTipoServicio`, `idUbicacionServicio`, `idUsuarioFuncionario`, `tipoOrigen`,`observaciones`) VALUES\n"
                + "(1, 'Caída de árbol ', NULL, 1, '2016-11-25', '2016-11-25', 5, 1, NULL, NULL, NULL),\n"
                + "(2, 'Fumigación de mosquitos', NULL, 2, '2016-11-25', '2016-11-25', 5, 2, NULL, NULL, NULL),\n"
                + "(3, 'Contenedor Lleno', NULL, 4, '2016-11-25', '2016-11-09', 4, 3, NULL, NULL, NULL),\n"
                + "(4, 'Calle sucia', NULL, 1, '2016-11-25', '2016-11-25', 3, 4, NULL, NULL, NULL);");
        Query insertSupervisor = s.createSQLQuery("INSERT INTO `supervisor` (`idSupervisor`, `nombre`, `apellido`, `estado`) VALUES"
                + "(1, 'Jose', 'Perez', 1),"
                + "(2, 'Pablo', 'Sanchez', 1),"
                + "(3, 'Pedro', 'Pedrozo', 1);");

        Query insertSupervisorServicio = s.createSQLQuery("INSERT INTO `supervisorservicio` (`idSupervisor`, `idServicio`) VALUES\n"
                + "(1, 1),\n"
                + "(1, 2),\n"
                + "(2, 2),\n"
                + "(2, 3);");
        Query insertUsuario = s.createSQLQuery("INSERT INTO `usuario` (`idUsuario`, `nombre`, `apellido`, `nickname`, `password`, `token`, `correo`, `estado`, `nroIdentidad`, `tipoIdentidad`, `idRol`, `telefono`) VALUES\n"
                + "(1, 'admin', 'admin', 'admin', '81dc9bdb52d04dc20036dbd8313ed055', '282e1e2b-a963-46f5-ac42-b9364617fcbf', NULL, 1, NULL, NULL, 1, NULL);");
        deleteServicioEmpresa.executeUpdate();
        deleteSupervisorServicio.executeUpdate();
        deleteServicio.executeUpdate();
        deleteUbicacionServicio.executeUpdate();
        deleteTipoServicio.executeUpdate();
        deleteArea.executeUpdate();
        deleteBarrio.executeUpdate();
        deleteMunicipio.executeUpdate();
        deleteEmpresa.executeUpdate();
        deleteSupervisor.executeUpdate();
        insertMunicipio.executeUpdate();
        insertBarrios.executeUpdate();
        insertUbicacionServicio.executeUpdate();
        insertArea.executeUpdate();
        insertTipoServicio.executeUpdate();
        insertEmpresa.executeUpdate();
        insertServicio.executeUpdate();
        insertSupervisor.executeUpdate();
        insertSupervisorServicio.executeUpdate();
        setAutoIncrementSupervisor.executeUpdate();
        insertUsuario.executeUpdate();
        tx.commit();
        s.close();
    }

    @AfterClass
    public static void tearDownClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteTipoServicio = s.createSQLQuery("DELETE FROM `tiposervicio`");
        Query deleteUbicacionServicio = s.createSQLQuery("DELETE FROM `ubicacionservicio`");
        Query deleteArea = s.createSQLQuery("DELETE FROM `area`");
        Query deleteBarrio = s.createSQLQuery("DELETE FROM `barrio`");
        Query deleteMunicipio = s.createSQLQuery("DELETE FROM `municipio`");
        Query deleteSupervisorServicio = s.createSQLQuery("DELETE FROM `supervisorservicio`");
        Query deleteServicioEmpresa = s.createSQLQuery("DELETE FROM `servicioempresa`");
        Query deleteEmpresa = s.createSQLQuery("DELETE FROM `empresa`");
        Query deleteServicio = s.createSQLQuery("DELETE FROM `servicio`");
        Query deleteSupervisor = s.createSQLQuery("DELETE FROM `supervisor`");
        deleteServicioEmpresa.executeUpdate();
        deleteSupervisorServicio.executeUpdate();
        deleteServicio.executeUpdate();
        deleteUbicacionServicio.executeUpdate();
        deleteTipoServicio.executeUpdate();
        deleteArea.executeUpdate();
        deleteBarrio.executeUpdate();
        deleteMunicipio.executeUpdate();
        deleteEmpresa.executeUpdate();
        deleteSupervisor.executeUpdate();
        tx.commit();
        s.close();
    }

    /**
     * Test of getSupervisores method, of class wsSupervisor.
     */
    @Test
    public void testGetSupervisores() {
        System.out.println("* wsSupervisorTest: testGetSupervisores()");
        String jsonToken = "282e1e2b-a963-46f5-ac42-b9364617fcbf";
        String jsonSinToken = "";
        String jsonTokenFormato = "282e1e2b-a963-46f5-ac42";
        wsSupervisor instance = new wsSupervisor();
        String expResult1 = "{\"msg\":\"\",\"tipo\":\"\",\"colSup\"[{\"idSupervisor\":1,\"nombre\":\"Jose\",\"apellido\":\"Perez\",\"estado\":\"Activo\"},"
                + "{\"idSupervisor\":2,\"nombre\":\"Pablo\",\"apellido\":\"Sanchez\",\"estado\":\"Activo\"},"
                + "{\"idSupervisor\":3,\"nombre\":\"Pedro\",\"apellido\":\"Pedrozo\",\"estado\":\"Activo\"}]}";
        String expResult2 = "{\"msg\":\"Error no tiene autorización 19C1%\",\"tipo\":\"danger\"}";
        String expResult3 = "{\"msg\":\"Error no tiene autorización 19C1%\",\"tipo\":\"danger\"}";
        String result1 = instance.getSupervisores(jsonToken);
        String result2 = instance.getSupervisores(jsonSinToken);
        String result3 = instance.getSupervisores(jsonTokenFormato);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        System.out.println("OK GetSupervisores!");
    }

    /**
     * Test of getSupervisores method, of class wsSupervisor.
     */
    @Test
    public void testGetSupervisoresByServicio() {
        System.out.println("* wsSupervisorTest: testGetSupervisoresByServicio()");
        String idServicioTieneSupervisor = "2";
        String idServicioNoTieneSupervisor = "4";
        String idServicioNoExisteServicio = "9";
        wsSupervisor instance = new wsSupervisor();
        String expResult1 = "[{\"idSupervisor\":1,\"nombre\":\"Jose\",\"apellido\":\"Perez\",\"estado\":\"Activo\"},"
                + "{\"idSupervisor\":2,\"nombre\":\"Pablo\",\"apellido\":\"Sanchez\",\"estado\":\"Activo\"}]";
        String expResult2 = "[]";
        String expResult3 = "[]";
        String result1 = instance.getSupervisoresByServicio(idServicioTieneSupervisor);
        String result2 = instance.getSupervisoresByServicio(idServicioNoTieneSupervisor);
        String result3 = instance.getSupervisoresByServicio(idServicioNoExisteServicio);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        System.out.println("OK GetSupervisoresByServicio!");
    }

    /**
     * Test of altaSupervisor method, of class wsSupervisor.
     */
    @Test
    public void testAltaSupervisor() {
        System.out.println("* wsSupervisorTest: testAltaSupervisor()");
        String jsonAltaSupervisor = "{'nombre':'Maria','apellido':'Perez','token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaNombre = "{'nombre':'','apellido':'Lopez','token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonNombreLargo = "{'nombre':'NombreLargoDeMasDe25Caracteres','apellido':'Lopez','token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaApellido = "{'nombre':'Andrea','apellido':'','token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonApellidoLargo = "{'nombre':'Andrea','apellido':'ApellidoLargoDeMasDe25Caracteres','token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonExisteSupervisor = "{'nombre':'Jose','apellido':'Perez','token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaToken = "{'nombre':'supertoken','apellido':'supertoke','token':''}";
        String jsonTokenNulo = "{'nombre':'supertoke','apellido':'supertoke','token':null}";
        String jsonTokenFormato = "{'nombre':'supertoke','apellido':'supertoke','token':'82e1e2b-a963-46f5-ac42-'}";
        String jsonSinDatos = "{}";
        wsSupervisor instance = new wsSupervisor();
        String expResult1 = "{\"msg\":\"Supervisor registrado correctamente\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult3 = "{\"msg\":\"El nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"Apellido requerido%\",\"tipo\":\"danger\"}";
        String expResult5 = "{\"msg\":\"El apellido supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String expResult6 = "{\"msg\":\"El supervisor ya existe%\",\"tipo\":\"danger\"}";
        String expResult7 = "{\"msg\":\"Error no tiene autorización 16C1%\",\"tipo\":\"danger\"}";
        String expResult8 = "{\"msg\":\"Error no tiene autorización 16C1%\",\"tipo\":\"danger\"}";
        String expResult9 = "{\"msg\":\"Error no tiene autorización 16C1%\",\"tipo\":\"danger\"}";
        String expResult10 = "{\"msg\":\"Datos inexistentes intente nuevamente%\",\"tipo\":\"danger\"}";
        String result1 = instance.altaSupervisor(jsonAltaSupervisor);
        String result2 = instance.altaSupervisor(jsonFaltaNombre);
        String result3 = instance.altaSupervisor(jsonNombreLargo);
        String result4 = instance.altaSupervisor(jsonFaltaApellido);
        String result5 = instance.altaSupervisor(jsonApellidoLargo);
        String result6 = instance.altaSupervisor(jsonExisteSupervisor);
        String result7 = instance.altaSupervisor(jsonFaltaToken);
        String result8 = instance.altaSupervisor(jsonTokenFormato);
        String result9 = instance.altaSupervisor(jsonTokenNulo);
        String result10 = instance.altaSupervisor(jsonSinDatos);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult5, result5);
        assertEquals(expResult6, result6);
        assertEquals(expResult7, result7);
        assertEquals(expResult8, result8);
        assertEquals(expResult9, result9);
        assertEquals(expResult10, result10);
        System.out.println("OK AltaSupervisor!");
    }

    /**
     * Test of modificarSupervisor method, of class wsSupervisor.
     */
    @Test
    public void testModificarSupervisor() {
        System.out.println("* wsSupervisorTest: testModificarSupervisor()");
        String jsonModificarSupervisor1 = "{'nombre':'Esteban','apellido':'Quito','idSupervisor':1,'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonModificarSupervisor2 = "{'nombre':'Raul','apellido':'Cito','idSupervisor':1,'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaNombre = "{'nombre':'','apellido':'Quito','idSupervisor':1,'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonNombreLargo = "{'nombre':'NombreLargoDeMasDe25Caracteres','apellido':'Quito','idSupervisor':1,'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaApellido = "{'nombre':'Esteban','apellido':'','idSupervisor':1,'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonApellidoLargo = "{'nombre':'Esteban','apellido':'ApellidoLargoDeMasDe25caracteres','idSupervisor':1,'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaIdSupervisor = "{'nombre':'Claudia','apellido':'Terra','idSupervisor':null,'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonNoExisteSupervisor = "{'nombre':'Claudia','apellido':'Terra','idSupervisor':9,'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaToken = "{'nombre':'Claudia','apellido':'Terra','idSupervisor':9,'token':''}";
        String jsonTokenNulo = "{'nombre':'Claudia','apellido':'Terra','idSupervisor':9,'token':null}";
        String jsonTokenFormato = "{'nombre':'Claudia','apellido':'Terra','idSupervisor':9,'token':'282e1e2-ac42-b9364617fcbf'}";
        wsSupervisor instance = new wsSupervisor();
        String expResult1 = "{\"msg\":\"Supervisor modificado exitosamente\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"Supervisor modificado exitosamente\",\"tipo\":\"success\"}";
        String expResult3 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"El nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String expResult5 = "{\"msg\":\"Apellido requerido%\",\"tipo\":\"danger\"}";
        String expResult6 = "{\"msg\":\"El apellido supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String expResult7 = "{\"msg\":\"Identificador del supervisor requerido%\",\"tipo\":\"danger\"}";
        String expResult8 = "{\"msg\":\"El supervisor no existe%\",\"tipo\":\"danger\"}";
        String expResult9 = "{\"msg\":\"Error no tiene autorización 18C1%\",\"tipo\":\"danger\"}";;
        String expResult10 = "{\"msg\":\"Error no tiene autorización 18C1%\",\"tipo\":\"danger\"}";;
        String expResult11 = "{\"msg\":\"Error no tiene autorización 18C1%\",\"tipo\":\"danger\"}";;
        String result1 = instance.modificarSupervisor(jsonModificarSupervisor1);
        String result2 = instance.modificarSupervisor(jsonModificarSupervisor2);
        String result3 = instance.modificarSupervisor(jsonFaltaNombre);
        String result4 = instance.modificarSupervisor(jsonNombreLargo);
        String result5 = instance.modificarSupervisor(jsonFaltaApellido);
        String result6 = instance.modificarSupervisor(jsonApellidoLargo);
        String result7 = instance.modificarSupervisor(jsonFaltaIdSupervisor);
        String result8 = instance.modificarSupervisor(jsonNoExisteSupervisor);
        String result9 = instance.modificarSupervisor(jsonFaltaToken);
        String result10 = instance.modificarSupervisor(jsonTokenFormato);
        String result11 = instance.modificarSupervisor(jsonTokenNulo);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult5, result5);
        assertEquals(expResult6, result6);
        assertEquals(expResult7, result7);
        assertEquals(expResult8, result8);
        assertEquals(expResult9, result9);
        assertEquals(expResult10, result10);
        assertEquals(expResult11, result11);
        System.out.println("OK ModificarSupervisor!");
    }

    /**
     * Test of borrarSupervisor method, of class wsSupervisor.
     */
    @Test
    public void testBorrarSupervisor() {
        System.out.println("* wsSupervisorTest: testBorrarSupervisor()");
        String idSupervisorConServiciosActivos = "{\"idSupervisor\":1,\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\"}";
        String idSupervisorSinServiciosActivos = "{\"idSupervisor\":2,\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\"}";
        String idSupervisorSinServicios = "{\"idSupervisor\":3,\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\"}";
        String idSupervisorNoExiste = "{\"idSupervisor\":18089,\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\"}";
        String faltaToken = "{\"idSupervisor\":3}";
        String formatoToken = "{\"idSupervisor\":3,\"token\":\"c42-b9364617fcbf\"}";
        String nuloToken = "{\"idSupervisor\":3,\"token\":null}";
        wsSupervisor instance = new wsSupervisor();
        String expResult1 = "{\"msg\":\"Existen servicios activos asignados al supervisor%\",\"tipo\":\"danger\"}";
        String expResult2 = "{\"msg\":\"Supervisor eliminado con éxito\",\"tipo\":\"success\"}";
        String expResult3 = "{\"msg\":\"Supervisor eliminado con éxito\",\"tipo\":\"success\"}";
        String expResult4 = "{\"msg\":\"Supervisor inexistente%\",\"tipo\":\"danger\"}";
        String expResult5 ="{\"msg\":\"Error no tiene autorización 17C1%\",\"tipo\":\"danger\"}";
        String expResult6 = "{\"msg\":\"Error no tiene autorización 17C1%\",\"tipo\":\"danger\"}";
        String expResult7 = "{\"msg\":\"Error no tiene autorización 17C1%\",\"tipo\":\"danger\"}";
        String result1 = instance.borrarSupervisor(idSupervisorConServiciosActivos);
        String result2 = instance.borrarSupervisor(idSupervisorSinServiciosActivos);
        String result3 = instance.borrarSupervisor(idSupervisorSinServicios);
        String result4 = instance.borrarSupervisor(idSupervisorNoExiste);
        String result5 = instance.borrarSupervisor(faltaToken);
        String result6 =instance.borrarSupervisor(formatoToken);
        String result7 =instance.borrarSupervisor(nuloToken);
        
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult5, result5);
        assertEquals(expResult6, result6);
        assertEquals(expResult7, result7);
        
        System.out.println("OK BorrarSupervisor!");
    }

}
