package ServiceLayer.WS;

import Business.Entities.Area;
import Business.Entities.Empresa;
import Business.Entities.Tiposervicio;
import DataAccess.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class wsTipoServicioTest {
    
    public wsTipoServicioTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteRol = s.createSQLQuery("DELETE FROM `rol`");
        Query deleteArea = s.createSQLQuery("DELETE FROM `area`").addEntity(Area.class);
        Query deleteEmpresa = s.createSQLQuery("DELETE FROM `empresa`").addEntity(Empresa.class);
        Query deleteTipoServicio = s.createSQLQuery("DELETE FROM `tiposervicio`").addEntity(Tiposervicio.class);
        Query deleteUsuario = s.createSQLQuery("DELETE FROM `usuario`");
        Query insertRol = s.createSQLQuery("INSERT INTO `rol` (`idRol`, `nombre`, `descripcion`, `estado`) VALUES\n"
                + "(1, 'Administrador', 'Administrador del sistema', 1),\n"
                + "(2, 'Entidades Externas', 'Empresas', 1),\n"
                + "(3, 'Movil', 'Usuarios de la Aplicacion Movil', 1);");
        Query insertTipoServicio = s.createSQLQuery("INSERT INTO `tiposervicio` (`idTipoServicio`, `nombre`, `descripcion`, `estado`, `idArea`, `idEmpresa`) VALUES\n"
                + "(2, 'DESRATIZACION', '', 1, 1, NULL),\n"
                + "(3, 'EXTRACCION ARBOL ESPACIO PUBLICO', '', 1, 1, NULL),\n"
                + "(4, 'FINCA RUINOSA', '', 1, 1, NULL),\n"
                + "(5, 'FUMIGACION', '', 1, 1, NULL);");
        Query insertArea = s.createSQLQuery("INSERT INTO `area` (`idArea`, `nombre`, `descripcion`, `estado`) VALUES\n"
                + "(1, 'Higiene', 'Area de higiene', 1),\n"
                + "(2, 'Transito', 'Area de transito', 1),\n"
                + "(3, 'Salud', 'Area de salud', 1);");
        Query insertEmpresa = s.createSQLQuery("INSERT INTO `empresa` (`idEmpresa`, `nombre`, `rut`, `descripcion`, `estado`) VALUES\n"
                + "(1, 'Ecotenco', 345456547, 'Empresa encargada de la gestion de contenedores', 1), \n"
                + "(2, 'Barrido S.A.', 435456976, 'Empresa de barrido', 1);");
        Query setAutoIncrementTipoServicio = s.createSQLQuery("ALTER TABLE `tiposervicio` AUTO_INCREMENT = 6");
        deleteUsuario.executeUpdate();
        deleteRol.executeUpdate();
        deleteTipoServicio.executeUpdate();
        deleteArea.executeUpdate();
        deleteEmpresa.executeUpdate();
        insertArea.executeUpdate();
        insertRol.executeUpdate();
        insertEmpresa.executeUpdate();
        insertTipoServicio.executeUpdate();
        setAutoIncrementTipoServicio.executeUpdate();
        tx.commit();
        s.close();
    }
    
    @AfterClass
    public static void tearDownClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteRol = s.createSQLQuery("DELETE FROM `rol`");
        Query deleteArea = s.createSQLQuery("DELETE FROM `area`").addEntity(Area.class);
        Query deleteEmpresa = s.createSQLQuery("DELETE FROM `empresa`").addEntity(Empresa.class);
        Query deleteTipoServicio = s.createSQLQuery("DELETE FROM `tiposervicio`").addEntity(Tiposervicio.class);
        Query deleteUsuario = s.createSQLQuery("DELETE FROM `usuario`");
        deleteUsuario.executeUpdate();
        deleteRol.executeUpdate();
        deleteTipoServicio.executeUpdate();
        deleteArea.executeUpdate();
        deleteEmpresa.executeUpdate();
        tx.commit();
        s.close();
    }

    /**
     * Test of altaTipoServicio method, of class wsTipoServicio.
     */
    @Test
    public void testAltaTipoServicio() {
        System.out.println("* wsTipoServicioTest: testAltaTipoServicio()");
        String jsonAltaTipoServicio = "{'nombre':'TipoServicioPrueba','descripcion':'De prueba','idArea':'1','idEmpresa':'1'}";
        String jsonFaltaNombre = "{'nombre':'','descripcion':'De prueba','idArea':'1','idEmpresa':'1'}";
        String jsonNombreLargo = "{'nombre':'NombreLargoDeMasDe25Caracteres','descripcion':'De prueba','idArea':'1','idEmpresa':'1'}";
        String jsonFaltaArea = "{'nombre':'TipoServicioPrueba2','descripcion':'De prueba','idArea':null,'idEmpresa':'1'}";
        String jsonNoExisteArea = "{'nombre':'TipoServicioPrueba2','descripcion':'De prueba','idArea':'9','idEmpresa':'1'}";
        String jsonFaltaEmpresa = "{'nombre':'TipoServicioPrueba2','descripcion':'De prueba','idArea':'1','idEmpresa':null}";
        String jsonNoExisteEmpresa = "{'nombre':'TipoServicioPrueba2','descripcion':'De prueba','idArea':'1','idEmpresa':'9'}";
        String jsonExisteTipoServicio = "{'nombre':'TipoServicioPrueba','descripcion':'De prueba','idArea':'1','idEmpresa':'1'}";
        wsTipoServicio instance = new wsTipoServicio();
        String expResult1 = "{\"msg\":\"Tipo de Servicio ingresado correctamente\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult3 = "{\"msg\":\"El nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"Area requerida%\",\"tipo\":\"danger\"}";
        String expResult5 = "{\"msg\":\"El area no existe%\",\"tipo\":\"danger\"}";
        String expResult6 = "{\"msg\":\"Empresa requerida%\",\"tipo\":\"danger\"}";
        String expResult7 = "{\"msg\":\"La empresa no existe%\",\"tipo\":\"danger\"}";
        String expResult8 = "{\"msg\":\"El tipo de servicio ya existe%\",\"tipo\":\"danger\"}";
        String result1 = instance.altaTipoServicio(jsonAltaTipoServicio);
        String result2 = instance.altaTipoServicio(jsonFaltaNombre);
        String result3 = instance.altaTipoServicio(jsonNombreLargo);
        String result4 = instance.altaTipoServicio(jsonFaltaArea);
        String result5 = instance.altaTipoServicio(jsonNoExisteArea);
        String result6 = instance.altaTipoServicio(jsonFaltaEmpresa);
        String result7 = instance.altaTipoServicio(jsonNoExisteEmpresa);
        String result8 = instance.altaTipoServicio(jsonExisteTipoServicio);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult5, result5);
        assertEquals(expResult6, result6);
        assertEquals(expResult7, result7);
        assertEquals(expResult8, result8);
        System.out.println("OK AltaTipoServicio!");
    }

    /**
     * Test of bajaTipoServicio method, of class wsTipoServicio.
     */
    @Test
    public void testBajaTipoServicio() {
        System.out.println("* wsTipoServicioTest: testBajaTipoServicio()");
        String idTipoServicioExiste = "6";
        String idTipoServicioNoExiste = "9";
        wsTipoServicio instance = new wsTipoServicio();
        String expResult1 = "{\"msg\":\"El tipo de servicio cambio su estado a inactivo\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"El tipo de servicio no existe%\",\"tipo\":\"danger\"}";;
        String result1 = instance.bajaTipoServicio(idTipoServicioExiste);
        String result2 = instance.bajaTipoServicio(idTipoServicioNoExiste);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        System.out.println("OK BajaTipoServicio!");
    }

    /**
     * Test of modificarTipoServicio method, of class wsTipoServicio.
     */
    @Test
    public void testModificarTipoServicio() {
        System.out.println("* wsTipoServicioTest: testModificarTipoServicio()");
        String jsonModificarTipoServicio1 = "{'nombre':'TipoServicioPrueba2','descripcion':'De prueba2','idArea':'2','idEmpresa':'2','idTipoServicio':'6'}";
        String jsonModificarTipoServicio2 = "{'nombre':'TipoServicioPrueba3','descripcion':'De prueba3','idArea':'2','idEmpresa':'2','idTipoServicio':'6'}";
        String jsonFaltaNombre = "{'nombre':'','descripcion':'De prueba','idArea':'1','idEmpresa':'1','idTipoServicio':'6'}";
        String jsonNombreLargo = "{'nombre':'NombreLargoDeMasDe25Caracteres','descripcion':'De prueba','idArea':'1','idEmpresa':'1','idTipoServicio':'6'}";
        String jsonFaltaArea = "{'nombre':'TipoServicioPrueba2','descripcion':'De prueba','idArea':null,'idEmpresa':'1','idTipoServicio':'6'}";
        String jsonNoExisteArea = "{'nombre':'TipoServicioPrueba2','descripcion':'De prueba','idArea':'9','idEmpresa':'1','idTipoServicio':'6'}";
        String jsonFaltaEmpresa = "{'nombre':'TipoServicioPrueba2','descripcion':'De prueba','idArea':'1','idEmpresa':null,'idTipoServicio':'6'}";
        String jsonNoExisteEmpresa = "{'nombre':'TipoServicioPrueba2','descripcion':'De prueba','idArea':'1','idEmpresa':'9','idTipoServicio':'6'}";
        String jsonExisteTipoServicio = "{'nombre':'TipoServicioPrueba3','descripcion':'De prueba','idArea':'1','idEmpresa':'1','idTipoServicio':'5'}";
        wsTipoServicio instance = new wsTipoServicio();
        String expResult1 = "{\"msg\":\"Tipo de servicio modificado exitosamente\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"Tipo de servicio modificado exitosamente\",\"tipo\":\"success\"}";
        String expResult3 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"El nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String expResult5 = "{\"msg\":\"Area requerida%\",\"tipo\":\"danger\"}";
        String expResult6 = "{\"msg\":\"El area no existe%\",\"tipo\":\"danger\"}";
        String expResult7 = "{\"msg\":\"Empresa requerida%\",\"tipo\":\"danger\"}";
        String expResult8 = "{\"msg\":\"La empresa no existe%\",\"tipo\":\"danger\"}";
        String expResult9 = "{\"msg\":\"El tipo de servicio ya existe%\",\"tipo\":\"danger\"}";
        String result1 = instance.modificarTipoServicio(jsonModificarTipoServicio1);
        String result2 = instance.modificarTipoServicio(jsonModificarTipoServicio2);
        String result3 = instance.modificarTipoServicio(jsonFaltaNombre);
        String result4 = instance.modificarTipoServicio(jsonNombreLargo);
        String result5 = instance.modificarTipoServicio(jsonFaltaArea);
        String result6 = instance.modificarTipoServicio(jsonNoExisteArea);
        String result7 = instance.modificarTipoServicio(jsonFaltaEmpresa);
        String result8 = instance.modificarTipoServicio(jsonNoExisteEmpresa);
        String result9 = instance.modificarTipoServicio(jsonExisteTipoServicio);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult5, result5);
        assertEquals(expResult6, result6);
        assertEquals(expResult7, result7);
        assertEquals(expResult8, result8);
        assertEquals(expResult9, result9);
        System.out.println("OK ModificarTipoServicio!");
    }

    /**
     * Test of listarTipoServicio method, of class wsTipoServicio.
     */
    @Test
    public void testListarTipoServicio() {
        System.out.println("* wsTipoServicioTest: testListarTipoServicio()");
        wsTipoServicio instance = new wsTipoServicio();
        String expResult = "[{\"idTipoServicio\":2,\"nombre\":\"DESRATIZACION\",\"descripcion\":\"\",\"estado\":\"Activo\",\"idArea\":1,\"nombreArea\":\"Higiene\"},{\"idTipoServicio\":3,\"nombre\":\"EXTRACCION ARBOL ESPACIO PUBLICO\",\"descripcion\":\"\",\"estado\":\"Activo\",\"idArea\":1,\"nombreArea\":\"Higiene\"},{\"idTipoServicio\":4,\"nombre\":\"FINCA RUINOSA\",\"descripcion\":\"\",\"estado\":\"Activo\",\"idArea\":1,\"nombreArea\":\"Higiene\"},{\"idTipoServicio\":5,\"nombre\":\"FUMIGACION\",\"descripcion\":\"\",\"estado\":\"Activo\",\"idArea\":1,\"nombreArea\":\"Higiene\"}]";
        String result = instance.listarTipoServicio();
        assertEquals(expResult, result);
        System.out.println("OK ListarTipoServicio!");
    }
    
}
