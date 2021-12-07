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

public class wsMunicipioTest {
    
    public wsMunicipioTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteMunicipio = s.createSQLQuery("DELETE FROM `municipio`").addEntity(Municipio.class);
        Query deleteBarrio = s.createSQLQuery("DELETE FROM `barrio`").addEntity(Barrio.class);
        Query setAutoIncrementMunicipio = s.createSQLQuery("ALTER TABLE `municipio` AUTO_INCREMENT = 3");
        Query insertMunicipio = s.createSQLQuery("INSERT INTO `municipio` (`idMunicipio`, `nombre`, `descripcion`, `estado`) VALUES\n" +
        "(1, 'Punta Del Este', 'Municipio de Punta Del Este', 1),\n" +
        "(2, 'Maldonado', 'Maldonado', 1),\n" +
        "(3, 'La Barra', 'Municipio de la Barra', 1);");
        Query insertBarriosToMunicipio = s.createSQLQuery("INSERT INTO `barrio` (`idBarrio`, `nombre`, `descripcion`, `estado`, `idMunicipio`) VALUES\n" +
        "(1, 'Monaco', 'Barrio cerca de la terminal', 1, 2),\n" +
        "(2, 'Sarubi', 'Por la Cachimba',1, 2),\n" +
        "(3, 'Gorlero', 'Calle principal de punta', 1,1);");
        deleteBarrio.executeUpdate();
        deleteMunicipio.executeUpdate();
        insertMunicipio.executeUpdate();
        insertBarriosToMunicipio.executeUpdate();
        setAutoIncrementMunicipio.executeUpdate();
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
     * Test of altaMuncipio method, of class wsMunicipio.
     */
    @Test
    public void testAltaMuncipio() {
        System.out.println("* wsMunicipioTest: testAltaMunicipio()");
        String jsonAltaMunicipio = "{'nombre':'Municipio','descripcion':'De test','estado':'1'}";
        String jsonRepiteNombre = "{'nombre':'Municipio','descripcion':'De test1','estado':'1'}";
        String jsonFaltaNombre = "{'nombre':'','descripcion':'De test2','estado':'1'}";
        String jsonNombreLargo = "{'nombre':'NombreLargoDeMasDe25Caracteres','descripcion':'De test3','estado':'1'}";
        wsMunicipio instance = new wsMunicipio();
        String expResult1 = "{\"msg\":\"Municipio agregado exitosamente\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"El municipio ya existe%\",\"tipo\":\"danger\"}";
        String expResult3 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"El nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String expResult5 = "{\"msg\":\"Descripcion requerida%\",\"tipo\":\"danger\"}";
        String result1 = instance.altaMuncipio(jsonAltaMunicipio);
        String result2 = instance.altaMuncipio(jsonRepiteNombre);
        String result3 = instance.altaMuncipio(jsonFaltaNombre);
        String result4 = instance.altaMuncipio(jsonNombreLargo);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        System.out.println("OK AltaMunicipio!");
    }

    /**
     * Test of modificarMunicipio method, of class wsMunicipio.
     */
    @Test
    public void testModificarMunicipio() {
        System.out.println("* wsMunicipioTest: testModificarMunicipio()");
        String jsonModificarMunicipio1 = "{'nombre':'San Carlos','descripcion':'Municipio de San Carlos','idMunicipio':'1'}";
         String jsonModificarMunicipio2 = "{'nombre':'San Carlos','descripcion':'Municipio de San Carlos segunda vez','idMunicipio':'1'}";
        String jsonRepiteNombre = "{'nombre':'Maldonado','descripcion':'De test1','idMunicipio':'1'}";
        String jsonFaltaNombre = "{'nombre':'','descripcion':'De test2','idMunicipio':'1'}";
        String jsonNombreLargo = "{'nombre':'NombreLargoDeMasDe25Caracteres','descripcion':'De test3','idMunicipio':'1'}";
        String jsonFaltaMunicipio = "{'nombre':'Municipio1','descripcion':'Municipio de prueba','idMunicipio':null}";
        String jsonNoExisteMunicipio = "{'nombre':'Municipio1','descripcion':'Municipio de prueba','idMunicipio':'15'}";
        wsMunicipio instance = new wsMunicipio();
        String expResult1 = "{\"msg\":\"Municipio modificado exitosamente\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"Municipio modificado exitosamente\",\"tipo\":\"success\"}";
        String expResult3 = "{\"msg\":\"El municipio ya existe%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult5 = "{\"msg\":\"El nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String expResult7 = "{\"msg\":\"Identificador del municipio requerido%\",\"tipo\":\"danger\"}";
        String expResult8 = "{\"msg\":\"El municipio no existe%\",\"tipo\":\"danger\"}";
        String result1 = instance.modificarMunicipio(jsonModificarMunicipio1);
        String result2 = instance.modificarMunicipio(jsonModificarMunicipio2);
        String result3 = instance.modificarMunicipio(jsonRepiteNombre);
        String result4 = instance.modificarMunicipio(jsonFaltaNombre);
        String result5 = instance.modificarMunicipio(jsonNombreLargo);
        String result7 = instance.modificarMunicipio(jsonFaltaMunicipio);
        String result8 = instance.modificarMunicipio(jsonNoExisteMunicipio);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult5, result5);
        assertEquals(expResult7, result7);
        assertEquals(expResult8, result8);
        System.out.println("OK ModificarMunicipio!");
    }

    /**
     * Test of borrarMunicipio method, of /class wsMunicipio.
     */
   @Test
    public void testBorrarMunicipio() {
        System.out.println("* wsMunicipioTest: testBorrarMunicipio()");
        String idMunicipioConBarrios = "2";
        String idMunicipioSinBarrios = "3";
        String idMunicipioNoExiste = "4";
        wsMunicipio instance = new wsMunicipio();
        String expResult1 = "{\"msg\":\"Existen barrios asociados al municipio. La operacion no fue realizada%\",\"tipo\":\"danger\"}";
        String expResult2 = "{\"msg\":\"El municipio cambio su estado a inactivo\",\"tipo\":\"success\"}";
        String expResult3 = "{\"msg\":\"El municipio no existe%\",\"tipo\":\"danger\"}";
        String result1 = instance.borrarMunicipio(idMunicipioConBarrios);
        String result2 = instance.borrarMunicipio(idMunicipioSinBarrios);
        String result3 = instance.borrarMunicipio(idMunicipioNoExiste);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        System.out.println("OK BorrarMunicipio!");
    }

    /**
     * Test of getMunicipios method, of class wsMunicipio.
     */
    @Test
    public void testGetMunicipios() {
        System.out.println("* wsMunicipioTest: testGetMunicipio()");
        wsMunicipio instance = new wsMunicipio();
        String expResult = "[{\"idMunicipio\":1,\"nombre\":\"San Carlos\",\"descripcion\":\"Municipio de San Carlos segunda vez\",\"estado\":true},{\"idMunicipio\":2,\"nombre\":\"Maldonado\",\"descripcion\":\"Maldonado\",\"estado\":true},{\"idMunicipio\":4,\"nombre\":\"Municipio\",\"descripcion\":\"De test\",\"estado\":true}]";
        String result = instance.getMunicipios();
        assertEquals(expResult, result);
        System.out.println("OK GetMunicipio!");
    }
}