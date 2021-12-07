package ServiceLayer.WS;

import DataAccess.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class wsRolTest {

    public wsRolTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteRolPermiso = s.createSQLQuery("DELETE FROM `rolpermiso`");
        Query deletePermiso = s.createSQLQuery("DELETE FROM `permiso`");
        Query deleteRol = s.createSQLQuery("DELETE FROM `rol`");
        Query deleteUsuario = s.createSQLQuery("DELETE FROM `usuario`");
        Query insertPermiso = s.createSQLQuery("INSERT INTO `permiso` (`idPermiso`, `nombreUnico`, `nombre`, `estado`, `url`, `descripcion`) VALUES\n"
                + "(1, 'Muu_Mantenimiento', 'Mantenimiento', 1, NULL, ''),\n"
                + "(2, 'Muu_Repotes', 'Reportes', 1, NULL, ''),\n"
                + "(3, 'Page_Rol', 'Rol', 1, '', NULL),\n"
                + "(4, 'Page_Area', 'Area', 1, '', NULL),\n"
                + "(5, 'Page_Area', 'Area', 1, '', NULL);");
        Query insertRol = s.createSQLQuery("INSERT INTO `rol` (`idRol`, `nombre`, `descripcion`, `estado`) VALUES\n"
                + "(1, 'Administrador', 'Administrador del sistema', 1),\n"
                + "(2, 'Entidades Externas', 'Empresas', 1),\n"
                + "(3, 'Movil', 'Usuarios de la Aplicacion Movil', 1);");
        Query insertRolPermiso = s.createSQLQuery("INSERT INTO `rolpermiso` (`idRol`, `idPermiso`) VALUES\n"
                + "(1, 1),\n"
                + "(1, 2),\n"
                + "(1, 3),\n"
                + "(1, 4),\n"
                + "(1, 5),\n"
                + "(2, 4);");
        Query insertUsuario = s.createSQLQuery("INSERT INTO `usuario` (`idUsuario`, `nombre`, `apellido`, `nickname`, `password`, `token`, `correo`, `estado`, `nroIdentidad`, `tipoIdentidad`, `idRol`, `telefono`) VALUES\n"
                + "(1, 'admin', 'admin', 'admin', '81dc9bdb52d04dc20036dbd8313ed055', '282e1e2b-a963-46f5-ac42-b9364617fcbf', NULL, 1, NULL, NULL, 1, NULL);");
        Query setAutoIncrementRol = s.createSQLQuery("ALTER TABLE `rol` AUTO_INCREMENT = 3");
        deleteUsuario.executeUpdate();
        deleteRolPermiso.executeUpdate();
        deletePermiso.executeUpdate();
        deleteRol.executeUpdate();
        insertPermiso.executeUpdate();
        insertRol.executeUpdate();
        insertRolPermiso.executeUpdate();
        insertUsuario.executeUpdate();
        setAutoIncrementRol.executeUpdate();
        tx.commit();
        s.close();
    }

    @AfterClass
    public static void tearDownClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteRolPermiso = s.createSQLQuery("DELETE FROM `rolpermiso`");
        Query deletePermiso = s.createSQLQuery("DELETE FROM `permiso`");
        Query deleteRol = s.createSQLQuery("DELETE FROM `rol`");
        Query deleteUsuario = s.createSQLQuery("DELETE FROM `usuario`");
        deleteUsuario.executeUpdate();
        deleteRolPermiso.executeUpdate();
        deletePermiso.executeUpdate();
        deleteRol.executeUpdate();
        tx.commit();
        s.close();
    }

    /**
     * Test of getRoles method, of class wsRol.
     */
    @Test
    public void testGetRoles() {
        System.out.println("* wsRolTest: testGetRoles()");
        String tokenOk = "282e1e2b-a963-46f5-ac42-b9364617fcbf";
        String formatoToken = "28wqeqw2e1e2b-a963-46f5-ac42-b9364617fcbf";
        String noexsiteToken = "282e1e2b-a963-786t-ac42-b9364617fcbf";
        String sinToken = "";

        wsRol instance = new wsRol();
        String expResult1 = "{\"msg\":\"\",\"tipo\":\"\",\"colRol\":[{\"idRol\":1,\"nombre\":\"Administrador\",\"descripcion\":\"Administrador del sistema\",\"estado\":true},"
                + "{\"idRol\":2,\"nombre\":\"Entidades Externas\",\"descripcion\":\"Empresas\",\"estado\":true},"
                + "{\"idRol\":3,\"nombre\":\"Movil\",\"descripcion\":\"Usuarios de la Aplicacion Movil\",\"estado\":true},";

        String expResult2 = "{\"msg\":\"Error no tiene autorización 12C1%\",\"tipo\":\"danger\"}";
        String expResult3 = "{\"msg\":\"Error no tiene autorización 12C1%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"Error no tiene autorización 12C1%\",\"tipo\":\"danger\"}";
        String result1 = instance.getRoles(tokenOk);
        String result2 = instance.getRoles(formatoToken);
        String result3 = instance.getRoles(noexsiteToken);
        String result4 = instance.getRoles(sinToken);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        System.out.println("OK GetRoles!");
    }

    /**
     * Test of altaRol method, of class wsRol.
     */
    @Test
    public void testAltaRol() {
        System.out.println("* wsRolTest: testAltaRol()");
        String jsonAltaRol = "{\"nombre\":\"NuevoRol\",\"descripcion\":\"De test\",\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\"}";
        String jsonRepiteNombre = "{\"nombre\":\"Entidades Externas\",\"descripcion\":\"De test1\",\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\"}";
        String jsonFaltaNombre = "{\"nombre\":\"\",\"descripcion\":\"De test1\",\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\"}";
        String jsonNombreLargo = "{\"nombre\":\"NombreLargoDeMasDe25Caracteres\",\"descripcion\":\"De test1\",\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\"}";
        wsRol instance = new wsRol();
        String expResult1 = "{\"msg\":\"Rol ingresado correctamente\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"Rol existente intente nuevamente%\",\"tipo\":\"danger\"}";
        String expResult3 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"El nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String result1 = instance.altaRol(jsonAltaRol);
        String result2 = instance.altaRol(jsonRepiteNombre);
        String result3 = instance.altaRol(jsonFaltaNombre);
        String result4 = instance.altaRol(jsonNombreLargo);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        System.out.println("OK AltaRol!");
    }

    /**
     * Test of modificarRol method, of class wsRol.
     */
    @Test
    public void testModificarRol() {
        System.out.println("* wsRolTest: testModificarRol()");
        String jsonModificarRol1 = "{\"nombre\":\"RolModificado\",\"descripcion\":\"Modificacion de rol\",\"idRol\":1,\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\"}";
        String jsonModificarRol2 = "{'nombre':'RolModificado','descripcion':'Modificacion de rol segunda vez','idRol':'1','token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonRepiteNombre = "{'nombre':'Entidades Externas','descripcion':'De test1','idRol':'3','token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaNombre = "{'nombre':'','descripcion':'De test2','idRol':'3','token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonNombreLargo = "{'nombre':'NombreLargoDeMasDe25Caracteres','descripcion':'De test3','idRol':'3','token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaRol = "{'nombre':'RolDePrueba','descripcion':'Rol de prueba','idRol':null,'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonNoExisteRol = "{'nombre':'RolDePrueba','descripcion':'Rol de prueba','idRol':'8','token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaToken = "{\"nombre\":\"RolModificado\",\"descripcion\":\"Modificacion de rol\",\"idRol\":1,\"token\":\"\"}";
        String jsonFaltaToken2 = "{\"nombre\":\"RolModificado\",\"descripcion\":\"Modificacion de rol\",\"idRol\":1}";
        wsRol instance = new wsRol();
        String expResult1 = "{\"msg\":\"Rol modificado con éxito\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"Rol modificado correctamente\",\"tipo\":\"success\"}";
        String expResult3 = "{\"msg\":\"Rol existente intente nuevamente%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult5 = "{\"msg\":\"El nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";
        String expResult7 = "{\"msg\":\"Identificador del rol requerido%\",\"tipo\":\"danger\"}";
        String expResult8 = "{\"msg\":\"Rol inexistente%\",\"tipo\":\"danger\"}";
        String expResult9 = "{\"msg\":\"Error no tiene autorización 11C1%\",\"tipo\":\"danger\"}";
        String expResult10 = "{\"msg\":\"Error no tiene autorización 11C1%\",\"tipo\":\"danger\"}";
        String result1 = instance.modificarRol(jsonModificarRol1);
        String result2 = instance.modificarRol(jsonModificarRol2);
        String result3 = instance.modificarRol(jsonRepiteNombre);
        String result4 = instance.modificarRol(jsonFaltaNombre);
        String result5 = instance.modificarRol(jsonNombreLargo);
        String result7 = instance.modificarRol(jsonFaltaRol);
        String result8 = instance.modificarRol(jsonNoExisteRol);
        String result9 = instance.modificarRol(jsonFaltaToken);
        String result10 = instance.modificarRol(jsonFaltaToken2);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult5, result5);
        assertEquals(expResult7, result7);
        assertEquals(expResult8, result8);
        assertEquals(expResult9, result9);
        assertEquals(expResult10, result10);
        System.out.println("OK Modificar Rol!");
    }

    /**
     * Test of borrarRol method, of class wsRol.
     */
    @Test
    public void testBorrarRol() {
        System.out.println("* wsRolTest: testBorrarRol()");
        String jsonBorraRol = "{\"idRol\":3,\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\"}";
        String jsonRolConUsuarios = "{\"idRol\":1,\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\"}";
        String jsonSinRol = "{\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\"}";
        String jsonRolNoExiste = "{\"idRol\":1111,\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\"}";
        wsRol instance = new wsRol();
        String expResult1 = "{\"msg\":\"Rol asociado a usuario, no se puede eliminar%\",\"tipo\":\"danger\"}";
        String expResult2 = "{\"msg\":\"Rol eliminado con éxito\",\"tipo\":\"success\"}";
        String expResult3 = "{\"msg\":\"Rol inexistente%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"Rol inexistente%\",\"tipo\":\"danger\"}";
        String result1 = instance.borrarRol(jsonBorraRol);
        String result2 = instance.borrarRol(jsonRolConUsuarios);
        String result3 = instance.borrarRol(jsonSinRol);
        String result4 = instance.borrarRol(jsonRolNoExiste);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        System.out.println("OK BorrarRol!");
    }

}
