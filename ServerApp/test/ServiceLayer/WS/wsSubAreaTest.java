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

public class wsSubAreaTest {

    public wsSubAreaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteArea = s.createSQLQuery("DELETE FROM `area`").addEntity(Area.class);
        Query deleteSubArea = s.createSQLQuery("DELETE FROM `subarea`").addEntity(Subarea.class);
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
     * Test of getSubAreas method, of class wsSubArea.
     */
  /*  @Test
    public void testGetSubAreas() {
        System.out.println("* wsSubAreaTest: testGetSubAreas()");
        wsSubArea instance = new wsSubArea();
        String expResult = "";
        String result = instance.getSubAreas();
        assertEquals(expResult, result);
        System.out.println("OK GetSubAreas!");
    }*/

    /**
     * Test of getSubAreasDeArea method, of class wsSubArea.
     */
    @Test
    public void testGetSubAreasDeArea() {
        System.out.println("* wsSubAreaTest: testGetSubAreasDeArea()");
        String idAreaConSubAreas = "1";
        String idAreaSinSubAreas = "3";
        String idAreaNoExiste = "9";
        wsSubArea instance = new wsSubArea();
        String expResult1 = "[{\"idSubArea\":1,\"idArea\":1,\"nombre\":\"Zonas publicas\",\"descripcion\":\"Higiene de zonas privadas\",\"estado\":true},{\"idSubArea\":2,\"idArea\":1,\"nombre\":\"Zonas privadas\",\"descripcion\":\"Higiene de zonas publicas\",\"estado\":true}]";
        String expResult2 = "[]";
        String expResult3 = "[]";
        String result1 = instance.getSubAreasDeArea(idAreaConSubAreas);
        String result2 = instance.getSubAreasDeArea(idAreaSinSubAreas);
        String result3 = instance.getSubAreasDeArea(idAreaNoExiste);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        System.out.println("OK GetSubAreasDeArea!");
    }

    /**
     * Test of altaSubArea method, of class wsSubArea.
     */
    /*@Test
    public void testAltaSubArea() {
        System.out.println("* wsSubAreaTest: testAltaSubArea()");
        String json = "";
        wsSubArea instance = new wsSubArea();
        String expResult = "";
        String result = instance.altaSubArea(json);
        assertEquals(expResult, result);
        System.out.println("OK AltaSubArea!");
    }*/

    /**
     * Test of borrarSubArea method, of class wsSubArea.
     */
    @Test
    public void testBorrarSubArea() {
        System.out.println("* wsSubAreaTest: testBorrarSubArea()");
        String idSubAreaExiste = "3";
        String idSubAreaNoExiste = "9";
        wsSubArea instance = new wsSubArea();
        String expResult1 = "{\"msg\":\"Subarea eliminada exitosamente\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"Subarea a eliminar inexistente\",\"tipo\":\"danger\"}";
        String result1 = instance.borrarSubArea(idSubAreaExiste);
        String result2 = instance.borrarSubArea(idSubAreaNoExiste);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        System.out.println("OK BorrarSubArea!");
    }

    /**
     * Test of modificarSubArea method, of class wsSubArea.
     */
 /*   @Test
    public void testModificarSubArea() { //+ "(1, 'Zonas publicas', 'Higiene de zonas privadas', 1, 1),\n"
        System.out.println("* wsSubAreaTest: testModificarSubArea()");
        String jsonModificarSubArea1 = "{'nombre':'Zonas Verdes','descripcion':'Bosques, plazas y parques','idArea':'1','idSubArea':'1'}";
        String jsonModificarSubArea2 = "{'nombre':'Zonas Verdes','descripcion':'Modificacion de zonas verdes','idArea':'1','idSubArea':'1'}";
        String jsonRepiteNombre = "{'nombre':'Mantenimiento','descripcion':'De test1','idArea':'1','idSubArea':'1'}";
        String jsonFaltaNombre = "{'nombre':'','descripcion':'De test2','idArea':'1','idSubArea':'1'}";
        String jsonNombreLargo = "{'nombre':'NombreLargoDeMasDe25Caracteres','descripcion':'De test3','idArea':'1','idSubArea':'1'}";
        String jsonFaltaArea = "{'nombre':'SubAreaTest','descripcion':'De test4','idArea':null,'idSubArea':'1'}";
        String jsonAreaNoExiste = "{'nombre':'SubAreaTest','descripcion':'De test5','idArea':'8','idSubArea':'1'}";
        String jsonFaltaIdSubArea = "{'nombre':'SubAreaTest','descripcion':'De test5','idSubArea':null,'idSubArea':'1'}";
        String jsonNoExisteSubArea = "{'nombre':'SubAreaTest','descripcion':'De test5','idSubArea':'9','idSubArea':'1'}";
        wsSubArea instance = new wsSubArea();
        String expResult = "";
        String result = instance.modificarSubArea(json);
        assertEquals(expResult, result);
        System.out.println("OK ModificarSubArea!");
    }*/

}
