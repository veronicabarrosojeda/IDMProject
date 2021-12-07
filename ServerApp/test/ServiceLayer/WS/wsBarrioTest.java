package ServiceLayer.WS;

import Business.Entities.Barrio;
import Business.Entities.Municipio;
import DataAccess.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class wsBarrioTest {
    
    public wsBarrioTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteMunicipio = s.createSQLQuery("DELETE FROM `municipio`").addEntity(Municipio.class);
        Query deleteBarrio = s.createSQLQuery("DELETE FROM `barrio`").addEntity(Barrio.class);
        Query setAutoIncrementMunicipio = s.createSQLQuery("ALTER TABLE `municipio` AUTO_INCREMENT = 3");
        Query setAutoIncrementBarrio = s.createSQLQuery("ALTER TABLE `barrio` AUTO_INCREMENT = 5");
        Query insertMunicipio = s.createSQLQuery("INSERT INTO `municipio` (`idMunicipio`, `nombre`, `descripcion`, `estado`) VALUES\n" +
        "(1, 'Punta Del Este', 'Municipio de Punta Del Este', 1),\n" +
        "(2, 'Maldonado', 'Maldonado', 1),\n" +
        "(3, 'La Barra', 'Municipio de la Barra', 1),\n" +
        "(4, 'BarrioInactivo', 'De test', 0);");
        Query insertBarriosToMunicipio = s.createSQLQuery("INSERT INTO `barrio` (`idBarrio`, `nombre`, `descripcion`, `estado`, `idMunicipio`) VALUES\n" +
        "(1, 'Monaco', 'Barrio cerca de la terminal', 1, 2),\n" +
        "(2, 'Sarubi', 'Por la Cachimba',1, 2),\n" +
        "(3, 'Gorlero', 'Calle principal de punta', 1,1);");
        deleteBarrio.executeUpdate();
        deleteMunicipio.executeUpdate();
        insertMunicipio.executeUpdate();
        insertBarriosToMunicipio.executeUpdate();
        setAutoIncrementMunicipio.executeUpdate();
        setAutoIncrementBarrio.executeUpdate();
        tx.commit();
        s.close();
    }
    
    @AfterClass
    public static void tearDownClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteMunicipio = s.createSQLQuery("DELETE FROM `municipio`").addEntity(Municipio.class);
        Query deleteBarrio = s.createSQLQuery("DELETE FROM `barrio`").addEntity(Barrio.class);
        deleteBarrio.executeUpdate();
        deleteMunicipio.executeUpdate();
        tx.commit();
        s.close();
    }

    /**
     * Test of altaBarrio method, of class wsBarrio.
     */
    @Test
    public void testAltaBarrio() {
        System.out.println("* wsBarrioTest: testAltaBarrio()");
        String jsonAltaBarrio = "{'nombre':'NuevoBarrio1','descripcion':'Descripcion de nuevo barrio1','idMunicipio':'1'}";
        String jsonFaltaNombre = "{'nombre':'','descripcion':'Descripcion de nuevo barrio2','idMunicipio':'1'}";
        String jsonNombreLargo = "{'nombre':'NombreLargoDeMasDe25Caracteres','descripcion':'Descripcion de nuevo barrio2','idMunicipio':'1'}";
        String jsonFaltaMunicipio = "{'nombre':'NuevoBarrio4','descripcion':'Descripcion de nuevo barrio4','idMunicipio':null}";
        String jsonNoExisteMunicipio = "{'nombre':'NuevoBarrio4','descripcion':'Descripcion de nuevo barrio4','idMunicipio':'7'}";
        String jsonRepiteBarrio = "{'nombre':'NuevoBarrio1','descripcion':'Descripcion de nuevo barrio1','idMunicipio':'1'}";
        wsBarrio instance = new wsBarrio();
        String expResult1 = "{\"msg\":\"Barrio ingresado correctamente\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult3 = "{\"msg\":\"El nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String expResult5 = "{\"msg\":\"Municipio requerido%\",\"tipo\":\"danger\"}";
        String expResult6 = "{\"msg\":\"El municipio no existe%\",\"tipo\":\"danger\"}";
        String expResult7 = "{\"msg\":\"El barrio ya existe%\",\"tipo\":\"danger\"}";
        String result1 = instance.altaBarrio(jsonAltaBarrio);
        String result2 = instance.altaBarrio(jsonFaltaNombre);
        String result3 = instance.altaBarrio(jsonNombreLargo);
        String result5 = instance.altaBarrio(jsonFaltaMunicipio);
        String result6 = instance.altaBarrio(jsonNoExisteMunicipio);
        String result7 = instance.altaBarrio(jsonRepiteBarrio);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult5, result5);
        assertEquals(expResult6, result6);
        assertEquals(expResult7, result7);
        System.out.println("OK AltaBarrio!");
    }

    /**
     * Test of bajaBarrio method, of class wsBarrio.
     */
    @Test
    public void testBajaBarrio() {
        System.out.println("* wsBarrioTest: testBajaBarrio()");
        String idBarrioExiste = "3";
        String idBarrioNoExiste = "6";
        wsBarrio instance = new wsBarrio();
        String expResult1 = "{\"msg\":\"El barrio cambio su estado a inactivo\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"El barrio no existe%\",\"tipo\":\"danger\"}";
        String result1 = instance.bajaBarrio(idBarrioExiste);
        String result2 = instance.bajaBarrio(idBarrioNoExiste);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        System.out.println("OK BajaBarrio!");
    }

    /**
     * Test of modificarBarrio method, of class wsBarrio.
     */
    @Test
    public void testModificarBarrio() {
        System.out.println("* wsBarrioTest: testModificarBarrio()");
        String jsonModificarBarrio1 = "{'nombre':'Terminal','descripcion':'Roosevlt y Figueroa','idBarrio':'1','idMunicipio':'1'}";
        String jsonModificarBarrio2 = "{'nombre':'Terminal','descripcion':'Roosevlt y Figueroa segunda vez','idBarrio':'1','idMunicipio':'1'}";
        String jsonRepiteNombre = "{'nombre':'Sarubi','descripcion':'De test1','idBarrio':'1','idMunicipio':'1'}";
        String jsonFaltaNombre = "{'nombre':'','descripcion':'De test2','idBarrio':'1','idMunicipio':'1'}";
        String jsonNombreLargo = "{'nombre':'NombreLargoDeMasDe25Caracteres','descripcion':'De test3','idBarrio':'1','idMunicipio':'1'}";
        String jsonFaltaMunicipio = "{'nombre':'BarrioTest','descripcion':'De test4','idBarrio':'1','idMunicipio':null}";
        String jsonMunicipioNoExiste = "{'nombre':'BarrioTest','descripcion':'De test5','idBarrio':'1','idMunicipio':'8'}";
        String jsonFaltaIdBarrio = "{'nombre':'BarrioTest','descripcion':'De test5','idBarrio':null,'idMunicipio':'1'}";
        String jsonNoExisteBarrio = "{'nombre':'BarrioTest','descripcion':'De test5','idBarrio':'9','idMunicipio':'1'}";
        wsBarrio instance = new wsBarrio();
        String expResult1 = "{\"msg\":\"Barrio modificado exitosamente\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"Barrio modificado exitosamente\",\"tipo\":\"success\"}";
        String expResult3 = "{\"msg\":\"El barrio ya existe%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult5 = "{\"msg\":\"El nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String expResult7 = "{\"msg\":\"Municipio requerido%\",\"tipo\":\"danger\"}";
        String expResult8 = "{\"msg\":\"El municipio no existe%\",\"tipo\":\"danger\"}";
        String expResult9 = "{\"msg\":\"Identificador del barrio requerido%\",\"tipo\":\"danger\"}";
        String expResult10 = "{\"msg\":\"El barrio no existe%\",\"tipo\":\"danger\"}";
        String result1 = instance.modificarBarrio(jsonModificarBarrio1);
        String result2 = instance.modificarBarrio(jsonModificarBarrio2);
        String result3 = instance.modificarBarrio(jsonRepiteNombre);
        String result4 = instance.modificarBarrio(jsonFaltaNombre);
        String result5 = instance.modificarBarrio(jsonNombreLargo);
        String result7 = instance.modificarBarrio(jsonFaltaMunicipio);
        String result8 = instance.modificarBarrio(jsonMunicipioNoExiste);
        String result9 = instance.modificarBarrio(jsonFaltaIdBarrio);
        String result10 = instance.modificarBarrio(jsonNoExisteBarrio);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult5, result5);
        assertEquals(expResult7, result7);
        assertEquals(expResult8, result8);
        assertEquals(expResult9, result9);
        assertEquals(expResult10, result10);
        System.out.println("OK ModificarBarrio!");
    }
    
    /**
     * Test of listarBarrios method, of class wsBarrio.
     */
    @Test
    public void testListarBarrios() {
        System.out.println("* wsBarrioTest: testListarBarrio()");
        wsBarrio instance = new wsBarrio();
        String expResult = "[{\"idBarrio\":1,\"nombre\":\"Monaco\",\"descripcion\":\"Barrio cerca de la terminal\",\"estado\":\"Activo\",\"idMunicipio\":2,\"nombreMunicipio\":\"Maldonado\"},{\"idBarrio\":2,\"nombre\":\"Sarubi\",\"descripcion\":\"Por la Cachimba\",\"estado\":\"Activo\",\"idMunicipio\":2,\"nombreMunicipio\":\"Maldonado\"},{\"idBarrio\":3,\"nombre\":\"Gorlero\",\"descripcion\":\"Calle principal de punta\",\"estado\":\"Activo\",\"idMunicipio\":1,\"nombreMunicipio\":\"Punta Del Este\"},{\"idBarrio\":5,\"nombre\":\"NuevoBarrio1\",\"descripcion\":\"Descripcion de nuevo barrio1\",\"estado\":\"Activo\",\"idMunicipio\":1,\"nombreMunicipio\":\"Punta Del Este\"}]";
        String result = instance.listarBarrios();
        assertEquals(expResult, result);
        System.out.println("OK ListarBarrio!");
    }    
}