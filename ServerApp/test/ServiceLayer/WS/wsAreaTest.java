package ServiceLayer.WS;

import Business.Entities.Area;
import Business.Entities.Subarea;
import DataAccess.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class wsAreaTest {

    public wsAreaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteArea = s.createSQLQuery("DELETE FROM `area`").addEntity(Area.class);
        Query deleteSubArea = s.createSQLQuery("DELETE FROM `subarea`").addEntity(Subarea.class);
        Query setAutoIncrementArea = s.createSQLQuery("ALTER TABLE `area` AUTO_INCREMENT = 4");
        Query setAutoIncrementSubArea = s.createSQLQuery("ALTER TABLE `subarea` AUTO_INCREMENT = 4");
        Query insertArea = s.createSQLQuery("INSERT INTO `area` (`idArea`, `nombre`, `descripcion`, `estado`) VALUES\n"
                + "(1, 'Higiene', 'Area de higiene', 1),\n"
                + "(2, 'Transito', 'Area de transito', 1),\n"
                + "(3, 'Salud', 'Area de salud', 1);");
        Query insertSubAreaToArea = s.createSQLQuery("INSERT INTO `subarea` (`idSubArea`, `nombre`, `descripcion`, `estado`, `idArea`) VALUES\n"
                + "(1, 'Zonas publicas', 'Higiene de zonas privadas', 1, 1),\n"
                + "(2, 'Zonas privadas', 'Higiene de zonas publicas',1, 1),\n"
                + "(3, 'Mantenimiento', 'Mantenimiento de la via publica', 1,2);");
        deleteSubArea.executeUpdate();
        deleteArea.executeUpdate();
        insertArea.executeUpdate();
        insertSubAreaToArea.executeUpdate();
        setAutoIncrementArea.executeUpdate();
        setAutoIncrementSubArea.executeUpdate();
        tx.commit();
        s.close();
    }

    @AfterClass
    public static void tearDownClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteArea = s.createSQLQuery("DELETE FROM `area`").addEntity(Area.class);
        Query deleteSubArea = s.createSQLQuery("DELETE FROM `subarea`").addEntity(Subarea.class);
        deleteSubArea.executeUpdate();
        deleteArea.executeUpdate();
        tx.commit();
        s.close();
    }

    /**
     * Test of listarAreas method, of class wsArea.
     */
    @Test
    public void testlistarAreas() {
        System.out.println("* wsBarrioTest: testListarAreas()");
        wsArea instance = new wsArea();
        String expResult = "[{\"idArea\":1,\"nombre\":\"Higiene\",\"descripcion\":\"Area de higiene\",\"estado\":true},"
                + "{\"idArea\":2,\"nombre\":\"Transito\",\"descripcion\":\"Area de transito\",\"estado\":true},"
                + "{\"idArea\":3,\"nombre\":\"NuevaArea\",\"descripcion\":\"Area de prueba segunda vez\",\"estado\":true},"
                + "{\"idArea\":4,\"nombre\":\"Bromatologia\",\"descripcion\":\"Area de bromatologia\",\"estado\":true}]";
        String result = instance.listarAreas();
        assertEquals(expResult, result);
        System.out.println("OK ListarAreas!");
    }

    /**
     * Test of listarAreaFiltro method, of class wsArea.
     */
    @Test
    public void testListarAreaFiltro() {
        System.out.println("* wsBarrioTest: testListarAreaFiltro()");
        String filtroExiste = "gie";
        String filtroCoincide = "Transito";
        String filtroNoExiste = "wow";
        String filtroVacio = "";
        String filtroNulo = null;
        wsArea instance = new wsArea();
        String expResult1 = "[{\"idArea\":1,\"nombre\":\"Higiene\",\"descripcion\":\"Area de higiene\",\"estado\":true}]";
        String expResult2 = "[{\"idArea\":2,\"nombre\":\"Transito\",\"descripcion\":\"Area de transito\",\"estado\":true}]";
        String expResult3 = "[]";
        String expResult4 = "[{\"idArea\":1,\"nombre\":\"Higiene\",\"descripcion\":\"Area de higiene\",\"estado\":true},"
                + "{\"idArea\":2,\"nombre\":\"Transito\",\"descripcion\":\"Area de transito\",\"estado\":true},"
                + "{\"idArea\":3,\"nombre\":\"NuevaArea\",\"descripcion\":\"Area de prueba segunda vez\",\"estado\":true},"
                + "{\"idArea\":4,\"nombre\":\"Bromatologia\",\"descripcion\":\"Area de bromatologia\",\"estado\":true}]";
        String expResult5 = "[]";
        String result1 = instance.listarAreaFiltro(filtroExiste);
        String result2 = instance.listarAreaFiltro(filtroCoincide);
        String result3 = instance.listarAreaFiltro(filtroNoExiste);
        String result4 = instance.listarAreaFiltro(filtroVacio);
        String result5 = instance.listarAreaFiltro(filtroNulo);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult5, result5);
        System.out.println("OK ListarAreaFiltro!");
    }

    /**
     * Test of altaArea method, of class wsArea.
     */
    @Test
    public void testAltaArea() {
        System.out.println("* wsBarrioTest: testAltaArea()");
        String jsonAltaArea = "{\"nombre\":\"Bromatologia\",\"descripcion\":\"Area de bromatologia\"}";
        String jsonFaltaNombre = "{\"nombre\":\"\",\"descripcion\":\"Area de prueba\"}";
        String jsonNombreLargo = "{\"nombre\":\"NombreLargoDeMasDe25Caracteres\",\"descripcion\":\"Area de prueba\"}";
        String jsonExisteArea = "{\"nombre\":\"Higiene\",\"descripcion\":\"Area de higiene de prueba\"}";
        wsArea instance = new wsArea();
        String expResult1 = "{\"msg\":\"El área fue ingresada correctamente\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult3 = "{\"msg\":\"El nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String expResult5 = "{\"msg\":\"El area ya existe%\",\"tipo\":\"danger\"}";
        String result1 = instance.altaArea(jsonAltaArea);
        String result2 = instance.altaArea(jsonFaltaNombre);
        String result3 = instance.altaArea(jsonNombreLargo);
        String result5 = instance.altaArea(jsonExisteArea);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult5, result5);
        System.out.println("OK AltaArea!");
    }

    /**
     * Test of getArea method, of class wsArea.
     */
    @Test
    public void testGetArea() {
        System.out.println("* wsBarrioTest: testGetArea()");
        String jsonExiste = "1";
        String jsonNoExiste = "7";
        wsArea instance = new wsArea();
        String expResult1 = "{\"idArea\":1,\"nombre\":\"Higiene\",\"descripcion\":\"Area de higiene\",\"estado\":true}";
        String expResult2 = "null";
        String result1 = instance.getArea(jsonExiste);
        String result2 = instance.getArea(jsonNoExiste);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        System.out.println("OK GetArea!");
    }


    /**
     * Test of modificarArea method, of class wsArea.
     */
    @Test
    public void testModificarArea() {
        System.out.println("* wsBarrioTest: testModificarArea()");
        String jsonModificarArea1 = "{\"nombre\":\"NuevaArea\",\"descripcion\":\"Area de prueba\",\"estado\":true,\"idArea\":\"3\"}";
        String jsonModificarArea2 = "{\"nombre\":\"NuevaArea\",\"descripcion\":\"Area de prueba segunda vez\",\"estado\":true,\"idArea\":\"3\"}";
        String jsonFaltaNombre = "{\"nombre\":\"\",\"descripcion\":\"Area de prueba\",\"estado\":true,\"idArea\":\"2\"}";
        String jsonNombreLargo = "{\"nombre\":\"NombreLargoDeMasDe25Caracteres\",\"descripcion\":\"Area de prueba\",\"estado\":true,\"idArea\":\"2\"}";
        String jsonExisteArea = "{\"nombre\":\"Higiene\",\"descripcion\":\"Area de higiene de prueba\",\"estado\":true,\"idArea\":\"2\"}";
        String jsonNoExisteArea = "{\"nombre\":\"NuevaAreaDePrueba\",\"descripcion\":\"Area de prueba\",\"estado\":true,\"idArea\":\"9\"}";
        String jsonFaltaArea = "{\"nombre\":\"NuevaArea\",\"descripcion\":\"Area de prueba\",\"estado\":true,\"idArea\":null}";
        wsArea instance = new wsArea();
        String expResult1 = "{\"msg\":\"El área fue modificada correctamente\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"El área fue modificada correctamente\",\"tipo\":\"success\"}";
        String expResult3 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"El nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String expResult6 = "{\"msg\":\"El area ya existe%\",\"tipo\":\"danger\"}";
        String expResult7 = "{\"msg\":\"El area no existe%\",\"tipo\":\"danger\"}";
        String expResult8 = "{\"msg\":\"Identificador del area requerido%\",\"tipo\":\"danger\"}";
        String result1 = instance.modificarArea(jsonModificarArea1);
        String result2 = instance.modificarArea(jsonModificarArea2);
        String result3 = instance.modificarArea(jsonFaltaNombre);
        String result4 = instance.modificarArea(jsonNombreLargo);
        String result6 = instance.modificarArea(jsonExisteArea);
        String result7 = instance.modificarArea(jsonNoExisteArea);
        String result8 = instance.modificarArea(jsonFaltaArea);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult6, result6);
        assertEquals(expResult7, result7);
        assertEquals(expResult8, result8);
        System.out.println("OK ModificarArea!");
    }

    /**
     * Test of cambiarEstado method, of class wsArea.
     */
    @Test
    public void testBajaArea() {
        System.out.println("* wsBarrioTest: testBorrarArea()");
        String jsonExisteAreaConSubAreas = "1";
        String jsonExisteAreaSinSubAreas = "5";
        String jsonNoExisteArea = "8";
        wsArea instance = new wsArea();
        instance.altaArea("{\"nombre\":\"AreaABorrar\",\"descripcion\":\"Area a borrar\"}");
        String expResult1 = "{\"msg\":\"Existen subareas asociadas al area. La operacion no fue realizada%\",\"tipo\":\"danger\"}"; 
        String expResult2 = "{\"msg\":\"El area cambio su estado a inactivo\",\"tipo\":\"success\"}"; 
        String expResult3 = "{\"msg\":\"El area no existe%\",\"tipo\":\"danger\"}";
        String result1 = instance.bajaArea(jsonExisteAreaConSubAreas);
        String result2 = instance.bajaArea(jsonExisteAreaSinSubAreas);
        String result3 = instance.bajaArea(jsonNoExisteArea);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        System.out.println("OK BorrarArea!");
    }
}