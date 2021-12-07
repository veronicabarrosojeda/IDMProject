package ServiceLayer.WS;

import Business.Entities.Area;
import Business.Entities.Empresa;
import Business.Entities.Servicio;
import Business.Entities.Tiposervicio;
import DataAccess.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class wsEmpresaTest {

    public wsEmpresaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteArea = s.createSQLQuery("DELETE FROM `area`").addEntity(Area.class);
        Query deleteServicioEmpresa = s.createSQLQuery("DELETE FROM `servicioempresa`");
        Query deleteUsuarioEmpresa = s.createSQLQuery("DELETE FROM `usuarioempresa`");
        Query deleteEmpresa = s.createSQLQuery("DELETE FROM `empresa`").addEntity(Empresa.class);
        Query deleteTipoServicio = s.createSQLQuery("DELETE FROM `tiposervicio`").addEntity(Tiposervicio.class);
        Query deleteServicio = s.createSQLQuery("DELETE FROM `servicio`").addEntity(Servicio.class);
        Query deleteUsuario = s.createSQLQuery("DELETE FROM `usuario`");
        Query deleteRol = s.createSQLQuery("DELETE FROM `rol`");
        Query deleteMunicipio = s.createSQLQuery("DELETE FROM `municipio`");
        Query deleteBarrio = s.createSQLQuery("DELETE FROM `barrio`");
        Query deleteUbicacionServicio = s.createSQLQuery("DELETE FROM `ubicacionservicio`");
        Query insertRol = s.createSQLQuery("INSERT INTO `rol` (`idRol`, `nombre`, `descripcion`, `estado`) VALUES\n"
                + "(1, 'Administrador', 'Administrador del sistema', 1),\n"
                + "(2, 'Entidades Externas', 'Empresas', 1),\n"
                + "(3, 'Movil', 'Usuarios de la Aplicacion Movil', 1);");
        Query insertUsuario = s.createSQLQuery("INSERT INTO `usuario` (`idUsuario`, `nombre`, `apellido`, `nickname`, `password`, `token`, `correo`, `estado`, `nroIdentidad`, `tipoIdentidad`, `idRol`, `telefono`) VALUES\n"
                + "(1, 'admin', 'admin', 'admin', '81dc9bdb52d04dc20036dbd8313ed055', '282e1e2b-a963-46f5-ac42-b9364617fcbf', NULL, 1, NULL, NULL, 1, NULL);");
        Query insertEmpresa = s.createSQLQuery("INSERT INTO `empresa` (`idEmpresa`, `nombre`, `rut`, `descripcion`, `estado`) VALUES\n"
                + "(1, 'Ecotenco', 345456547, 'Empresa encargada de la gestion de contenedores', 1), \n"
                + "(2, 'Barrido S.A.', 435456976, 'Empresa de barrido', 1);");
        Query insertTipoServicio = s.createSQLQuery("INSERT INTO `tiposervicio` (`idTipoServicio`, `nombre`, `descripcion`, `estado`, `idArea`, `idEmpresa`) VALUES\n"
                + "(2, 'DESRATIZACION', '', 1, 1, NULL),\n"
                + "(3, 'EXTRACCION ARBOL ESPACIO PUBLICO', '', 1, 1, NULL),\n"
                + "(4, 'FINCA RUINOSA', '', 1, 1, NULL),\n"
                + "(5, 'FUMIGACION', '', 1, 1, NULL);");
        Query insertUbicacionServicio = s.createSQLQuery("INSERT INTO `ubicacionservicio` (`idUbicacion`, `latitud`, `calle`, `nroPuerta`, `apto`, `entreCalles`, `nroManzana`, `nroSolar`, `nroPadron`, `idBarrio`, `longitud`) VALUES\n"
                + "(1, -34.912678, NULL, NULL, NULL, 'Victor Hugo y Av. S', NULL, NULL, '', 1, -54.953152),\n"
                + "(2, -34.921089, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, -54.951881),\n"
                + "(3, -34.915405, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, -54.886986),\n"
                + "(4, -34.915897, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, -54.906986);");
        Query insertMunicipio = s.createSQLQuery("INSERT INTO `municipio` (`idMunicipio`, `nombre`, `descripcion`, `estado`) VALUES\n"
                + "(1, 'Punta del Este', 'Municipio de Punta del Este', 1),\n"
                + "(2, 'Maldonado', 'Municipio de maldonado', 1);");
        Query insertBarrio = s.createSQLQuery("INSERT INTO `barrio` (`idBarrio`, `nombre`, `descripcion`, `estado`, `idMunicipio`) VALUES\n"
                + "(1, 'Jardines de Córdoba', 'Jardines de Córdoba', 1, 1),\n"
                + "(2, 'Monaco', 'Barrio cercano a la terminal', 1, 2);");
        Query insertServicio = s.createSQLQuery("INSERT INTO `servicio` (`idServicio`, `descripcion`, `rutaImagen`, `estado`, `fechaIngreso`, `fechaModificacion`, `idTipoServicio`, `idUbicacionServicio`, `idUsuarioFuncionario`, `tipoOrigen`) VALUES\n"
                + "(1, 'Caída de árbol ', NULL, 1, '2016-11-25', '2016-11-25', 5, 1, 1, 1),\n"
                + "(2, 'Fumigación de mosquitos', NULL, 2, '2016-11-25', '2016-11-25', 5, 2, NULL, NULL),\n"
                + "(3, 'Contenedor Lleno', NULL, 5, '2016-11-25', '2016-11-09', 4, 3, NULL, NULL),\n"
                + "(4, 'Calle sucia', NULL, 1, '2016-11-25', '2016-11-25', 3, 4, 1, 1);");
        Query insertServicioEmpresa = s.createSQLQuery("INSERT INTO `servicioempresa` (`idEmpresa`, `idServicio`) VALUES\n"
                + "(2, 1);");
        Query insertUsuarioEmpresa = s.createSQLQuery("INSERT INTO `usuarioempresa` (`idUsuario`, `idEmpresa`) VALUES\n"
                + "(1, 1);");
        Query insertArea = s.createSQLQuery("INSERT INTO `area` (`idArea`, `nombre`, `descripcion`, `estado`) VALUES\n"
                + "(1, 'Higiene', 'Area de higiene', 1),\n"
                + "(2, 'Transito', 'Area de transito', 1),\n"
                + "(3, 'Salud', 'Area de salud', 1);");
        Query setAutoIncrementEmpresa = s.createSQLQuery("ALTER TABLE `empresa` AUTO_INCREMENT = 3");
        deleteArea.executeUpdate();
        deleteServicioEmpresa.executeUpdate();
        deleteUsuarioEmpresa.executeUpdate();
        deleteUsuario.executeUpdate();
        deleteRol.executeUpdate();
        deleteEmpresa.executeUpdate();
        deleteServicio.executeUpdate();
        deleteUbicacionServicio.executeUpdate();
        deleteBarrio.executeUpdate();
        deleteMunicipio.executeUpdate();
        deleteTipoServicio.executeUpdate();
        insertArea.executeUpdate();
        insertRol.executeUpdate();
        insertUsuario.executeUpdate();
        insertEmpresa.executeUpdate();
        insertTipoServicio.executeUpdate();
        insertMunicipio.executeUpdate();
        insertBarrio.executeUpdate();
        insertUbicacionServicio.executeUpdate();
        insertServicio.executeUpdate();
        insertServicioEmpresa.executeUpdate();
        insertUsuarioEmpresa.executeUpdate();
        setAutoIncrementEmpresa.executeUpdate();
        tx.commit();
        s.close();
    }

    @AfterClass
    public static void tearDownClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteServicioEmpresa = s.createSQLQuery("DELETE FROM `servicioempresa`");
        Query deleteUsuarioEmpresa = s.createSQLQuery("DELETE FROM `usuarioempresa`");
        Query deleteEmpresa = s.createSQLQuery("DELETE FROM `empresa`").addEntity(Empresa.class);
        Query deleteTipoServicio = s.createSQLQuery("DELETE FROM `tiposervicio`").addEntity(Tiposervicio.class);
        Query deleteServicio = s.createSQLQuery("DELETE FROM `servicio`").addEntity(Servicio.class);
        Query deleteUsuario = s.createSQLQuery("DELETE FROM `usuario`");
        Query deleteRol = s.createSQLQuery("DELETE FROM `rol`");
        Query deleteMunicipio = s.createSQLQuery("DELETE FROM `municipio`");
        Query deleteBarrio = s.createSQLQuery("DELETE FROM `barrio`");
        Query deleteUbicacionServicio = s.createSQLQuery("DELETE FROM `ubicacionservicio`");
        deleteServicioEmpresa.executeUpdate();
        deleteUsuarioEmpresa.executeUpdate();
        deleteUsuario.executeUpdate();
        deleteRol.executeUpdate();
        deleteEmpresa.executeUpdate();
        deleteServicio.executeUpdate();
        deleteUbicacionServicio.executeUpdate();
        deleteBarrio.executeUpdate();
        deleteMunicipio.executeUpdate();
        deleteTipoServicio.executeUpdate();
        tx.commit();
        s.close();
    }

    /**
     * Test of getEmpresas method, of class wsEmpresa.
     */
    @Test
    public void testGetEmpresas() {
        System.out.println("* wsEmpresaTest: testGetEmpresas()");
        wsEmpresa instance = new wsEmpresa();
        String expResult = "[{\"idEmpresa\":1,\"nombre\":\"Ecotenco\",\"rut\":345456547,\"descripcion\":\"Empresa encargada de la gestion de contenedores\",\"estado\":true},"
                + "{\"idEmpresa\":2,\"nombre\":\"Barrido S.A.\",\"rut\":435456976,\"descripcion\":\"Empresa de barrido\",\"estado\":true}]";
        String result = instance.getEmpresas();
        assertEquals(expResult, result);
        System.out.println("OK GetEmpresas!");
    }

    /**
     * Test of getEmpresasByUser method, of class wsEmpresa.
     */
    @Test
    public void testGetEmpresasByUser() {
        System.out.println("* wsEmpresaTest: testGetEmpresasByUser()");
        String jsonExisteUsuario = "1";
        String jsonNoExisteUsuario = "2";
        wsEmpresa instance = new wsEmpresa();
        String expResult1 = "[{\"idEmpresa\":1,\"nombre\":\"EmpresaCambioDeNombre\",\"rut\":987654321,\"descripcion\":\"Empresa de prueba en modificar segunda vez\",\"estado\":true}]";
        String expResult2 = "[]";
        String result1 = instance.getEmpresasByUser(jsonExisteUsuario);
        String result2 = instance.getEmpresasByUser(jsonNoExisteUsuario);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        System.out.println("OK GetEmpresasByUser!");
    }

    /**
     * Test of getEmpresasByService method, of class wsEmpresa.
     */
    @Test
    public void testGetEmpresasByService() {
        System.out.println("* wsEmpresaTest: testGetEmpresasByService()");
        String jsonExisteServicio = "1";
        String jsonNoExisteServicio = "12";
        wsEmpresa instance = new wsEmpresa();
        String expResult1 = "[{\"idEmpresa\":1,\"nombre\":\"Ecotenco\",\"rut\":345456547,\"descripcion\":\"Empresa encargada de la gestion de contenedores\",\"estado\":true},{\"idEmpresa\":2,\"nombre\":\"Barrido S.A.\",\"rut\":435456976,\"descripcion\":\"Empresa de barrido\",\"estado\":true}]";
        String expResult2 = "[]";
        String result1 = instance.getEmpresasByService(jsonExisteServicio);
        String result2 = instance.getEmpresasByService(jsonNoExisteServicio);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        System.out.println("OK GetEmpresasByService!");
    }

    /**
     * Test of altaEmpresa method, of class wsEmpresa.
     */
    @Test
    public void testAltaEmpresa() {
        System.out.println("* wsEmpresaTest: testAltaEmpresa()");
        String jsonAltaEmpresa = "{'nombre':'EmpresaDePrueba','rut':123456789,'descripcion':'Empresa de prueba'}";
        String jsonFaltaNombre = "{'nombre':'','rut':111222333,'descripcion':'Empresa de prueba'}";
        String jsonNombreLargo = "{'nombre':'NombreLargoDeMasDe25Caracteres','rut':111222333,'descripcion':'Empresa de prueba'}";
        String jsonFaltaRut = "{'nombre':'EmpresaDePrueba2','rut':null,'descripcion':'Empresa de prueba'}";
        String jsonExisteEmpresa1 = "{'nombre':'EmpresaDePrueba','rut':111222333,'descripcion':'Empresa de prueba'}";
        String jsonExisteEmpresa2 = "{'nombre':'EmpresaDePrueba2','rut':123456789,'descripcion':'Empresa de prueba'}";
        wsEmpresa instance = new wsEmpresa();
        String expResult1 = "{\"msg\":\"La empresa fue ingresada correctamente!\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult3 = "{\"msg\":\"El nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"Rut requerido%\",\"tipo\":\"danger\"}";
        String expResult6 = "{\"msg\":\"La empresa ya existe%\",\"tipo\":\"danger\"}";
        String expResult7 = "{\"msg\":\"La empresa ya existe%\",\"tipo\":\"danger\"}";
        String result1 = instance.altaEmpresa(jsonAltaEmpresa);
        String result2 = instance.altaEmpresa(jsonFaltaNombre);
        String result3 = instance.altaEmpresa(jsonNombreLargo);
        String result4 = instance.altaEmpresa(jsonFaltaRut);
        String result6 = instance.altaEmpresa(jsonExisteEmpresa1);
        String result7 = instance.altaEmpresa(jsonExisteEmpresa2);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult6, result6);
        assertEquals(expResult7, result7);
        System.out.println("OK AltaEmpresa!");
    }

    /**
     * Test of modificarEmpresa method, of class wsEmpresa.
     */
    @Test
    public void testModificarEmpresa() {
        System.out.println("* wsEmpresaTest: testModificarEmpresa()");
        String jsonModificarEmpresa1 = "{'nombre':'EmpresaCambioDeNombre','rut':987654321,'descripcion':'Empresa de prueba en modificar','estado':'true','idEmpresa':1}";
        String jsonModificarEmpresa2 = "{'nombre':'EmpresaCambioDeNombre','rut':987654321,'descripcion':'Empresa de prueba en modificar segunda vez','estado':'true','idEmpresa':1}";
        String jsonFaltaNombre = "{'nombre':'','rut':111222333,'descripcion':'Empresa de prueba','estado':'true','idEmpresa':1}";
        String jsonNombreLargo = "{'nombre':'NombreLargoDeMasDe25Caracteres','rut':111222333,'descripcion':'Empresa de prueba','estado':'true','idEmpresa':1}";
        String jsonFaltaRut = "{'nombre':'EmpresaDePrueba2','rut':null,'descripcion':'Empresa de prueba','estado':'true','idEmpresa':1}";
        String jsonExisteEmpresa1 = "{'nombre':'Barrido S.A.','rut':111222333,'descripcion':'Empresa de prueba','estado':'true','idEmpresa':1}";
        String jsonExisteEmpresa2 = "{'nombre':'EmpresaDePrueba2','rut':435456976,'descripcion':'Empresa de prueba','estado':'true','idEmpresa':1}";
        String jsonNoExisteEmpresa = "{'nombre':'EmpresaDePrueba2','rut':555666777,'descripcion':'Empresa de prueba','estado':'true','idEmpresa':13}";
        String jsonFaltaIdEmpresa = "{'nombre':'EmpresaDePrueba2','rut':555666777,'descripcion':'Empresa de prueba','estado':'true','idEmpresa':null}";
        wsEmpresa instance = new wsEmpresa();
        String expResult1 = "{\"msg\":\"La empresa fue mofidicada correctamente!\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"La empresa fue mofidicada correctamente!\",\"tipo\":\"success\"}";
        String expResult3 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"El nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String expResult5 = "{\"msg\":\"Rut requerido%\",\"tipo\":\"danger\"}";
        String expResult7 = "{\"msg\":\"La empresa ya existe%\",\"tipo\":\"danger\"}";
        String expResult8 = "{\"msg\":\"La empresa ya existe%\",\"tipo\":\"danger\"}";
        String expResult9 = "{\"msg\":\"La empresa no existe%\",\"tipo\":\"danger\"}";
        String expResult10 = "{\"msg\":\"Identificador de la empresa requerido%\",\"tipo\":\"danger\"}";
        String result1 = instance.modificarEmpresa(jsonModificarEmpresa1);
        String result2 = instance.modificarEmpresa(jsonModificarEmpresa2);
        String result3 = instance.modificarEmpresa(jsonFaltaNombre);
        String result4 = instance.modificarEmpresa(jsonNombreLargo);
        String result5 = instance.modificarEmpresa(jsonFaltaRut);
        String result7 = instance.modificarEmpresa(jsonExisteEmpresa1);
        String result8 = instance.modificarEmpresa(jsonExisteEmpresa2);
        String result9 = instance.modificarEmpresa(jsonNoExisteEmpresa);
        String result10 = instance.modificarEmpresa(jsonFaltaIdEmpresa);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult5, result5);
        assertEquals(expResult7, result7);
        assertEquals(expResult8, result8);
        assertEquals(expResult9, result9);
        assertEquals(expResult10, result10);
        System.out.println("OK ModificarEmpresa!");
    }

    /**
     * Test of bajaEmpresa method, of class wsEmpresa.
     */
    @Test
    public void testBajaEmpresa() {
        System.out.println("* wsEmpresaTest: testBajaEmpresa()");
        String jsonExisteEmpresa = "2";
        String jsonNoExisteEmpresa = "12";
        wsEmpresa instance = new wsEmpresa();
        String expResult1 = "{\"msg\":\"La empresa fue dada de baja correctamente!\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"La empresa no existe%\",\"tipo\":\"danger\"}";
        String result1 = instance.bajaEmpresa(jsonExisteEmpresa);
        String result2 = instance.bajaEmpresa(jsonNoExisteEmpresa);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        System.out.println("OK BajaEmpresa!");
    }
}
