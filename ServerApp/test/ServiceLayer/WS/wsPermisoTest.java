/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer.WS;

import DataAccess.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class wsPermisoTest {

    public wsPermisoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteRolPermiso = s.createSQLQuery("DELETE FROM `rolpermiso`");
        Query deletePermiso = s.createSQLQuery("DELETE FROM `permiso`");
        Query deleteRol = s.createSQLQuery("DELETE FROM `rol`");
        Query deleteUsuario = s.createSQLQuery("DELETE FROM `usuario`");
        Query deleteEmpresa = s.createSQLQuery("DELETE FROM `empresa`");
        Query deleteUsuarioEmpresa = s.createSQLQuery("DELETE FROM `usuarioempresa`");
        Query insertPermiso = s.createSQLQuery("INSERT INTO `permiso` (`idPermiso`, `nombreUnico`, `nombre`, `estado`, `url`, `descripcion`) VALUES\n"
                + "(1, 'Muu_Mantenimiento', 'Mantenimiento', 1, NULL, ''),\n"
                + "(2, 'Muu_Repotes', 'Reportes', 1, NULL, ''),\n"
                + "(3, 'Page_Municipio', 'Municipio', 1, '', NULL),\n"
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
        Query insertUsuarioEmpresa = s.createSQLQuery("INSERT INTO `usuarioempresa` (`idUsuario`, `idEmpresa`) VALUES\n"
                + "(1, 1);");
        Query insertEmpresa = s.createSQLQuery("INSERT INTO `empresa` (`idEmpresa`, `nombre`, `rut`, `descripcion`, `estado`) VALUES\n"
                + "(1, 'Ecotenco', 345456547, 'Empresa encargada de la gestion de contenedores', 1), \n"
                + "(2, 'Barrido S.A.', 435456976, 'Empresa de barrido', 1);");
        Query setAutoIncrementPermiso = s.createSQLQuery("ALTER TABLE `permiso` AUTO_INCREMENT = 3");
        deleteUsuarioEmpresa.executeUpdate();
        deleteEmpresa.executeUpdate();
        deleteUsuario.executeUpdate();
        deleteRolPermiso.executeUpdate();
        deletePermiso.executeUpdate();
        deleteRol.executeUpdate();
        insertPermiso.executeUpdate();
        insertRol.executeUpdate();
        insertRolPermiso.executeUpdate();
        insertUsuario.executeUpdate();
        insertEmpresa.executeUpdate();
        insertUsuarioEmpresa.executeUpdate();
        setAutoIncrementPermiso.executeUpdate();
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
        Query deleteUsuarioEmpresa = s.createSQLQuery("DELETE FROM `usuarioempresa`");
        deleteUsuarioEmpresa.executeUpdate();
        deleteUsuario.executeUpdate();
        deleteRolPermiso.executeUpdate();
        deletePermiso.executeUpdate();
        deleteRol.executeUpdate();
        tx.commit();
        s.close();
    }

    /**
     * Test of actualizarRolPermiso method, of class wsPermiso.
     */
    @Test
    public void testActualizarRolPermiso() {
        System.out.println("* wsPermisoTest: testActualizarRolPermiso()");
        String jsonActualizarRolPermiso = "{'idRol':1,'permisos':["
                + "{'idPermiso':2,'nombre':'Reportes','nombreUnico':'Muu_Repotes'},"
                + "{'idPermiso':3,'nombre':'Municipio','nombreUnico':'Page_Municipio'}],"
                + "'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaIdRol = "{'permisos':["
                + "{'idPermiso':1,'nombre':'Mantenimiento','nombreUnico':'Muu_Mantenimiento'}],"
                + "'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonIdRolNulo = "{'idRol':null,'permisos':["
                + "{'idPermiso':1,'nombre':'Mantenimiento','nombreUnico':'Muu_Mantenimiento'}],"
                + "'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonNoExisteRol = "{'idRol':55,'permisos':["
                + "{'idPermiso':1,'nombre':'Mantenimiento','nombreUnico':'Muu_Mantenimiento'}],"
                + "'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaIdPermiso = "{'idRol':1,'permisos':["
                + "{'nombre':'Mantenimiento','nombreUnico':'Muu_Mantenimiento'}],"
                + "'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonIdPermisoNulo = "{'idRol':1,'permisos':["
                + "{'idPermiso':null,'nombre':'Mantenimiento','nombreUnico':'Muu_Mantenimiento'}],"
                + "'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaNombre = "{'idRol':1,'permisos':["
                + "{'idPermiso':1,'nombre':'','nombreUnico':'Muu_Mantenimiento'}],"
                + "'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonNombreNulo = "{'idRol':1,'permisos':["
                + "{'idPermiso':1,'nombre':null,'nombreUnico':'Muu_Mantenimiento'}],"
                + "'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaNombreUnico = "{'idRol':1,'permisos':["
                + "nu{'idPermiso':1,'nombre':'Mantenimiento','nombreUnico':''}],"
                + "'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaToken = "{'idRol':1,'permisos':["
                + "{'idPermiso':null,'nombre':'Mantenimiento','nombreUnico':'Muu_Mantenimiento'}],"
                + "'token':''}";
        String jsonFormatoToken = "{'idRol':1,'permisos':["
                + "{'idPermiso':null,'nombre':'Mantenimiento','nombreUnico':'Muu_Mantenimiento'}],"
                + "'token':'282e1e2b-b9364617fcbf'}";
        String jsonFaltanPermisos = "{'idRol':1,"
                + "'token':'282e1e2b-b9364617fcbf'}";
        wsPermiso instance = new wsPermiso();
        String expResult1 = "{\"msg\":\"Permisos modificados con éxito\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"Identificador del rol requerido%\",\"tipo\":\"danger\"}";
        String expResult3 = "{\"msg\":\"Identificador del rol requerido%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"Rol inexistente%\",\"tipo\":\"danger\"";

        String expResult5 = "{\"msg\":\"Identificador del permiso requerido%\",\"tipo\":\"danger\"}";
        String expResult6 = "{\"msg\":\"Identificador del permiso requerido%\",\"tipo\":\"danger\"}";
        String expResult7 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult8 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult9 = "{\"msg\":\"Nombre unico requerido%\",\"tipo\":\"danger\"}";
        String expResult10 = "{\"msg\":\"Error no tiene autorización 15C1%\",\"tipo\":\"danger\"}";
        String expResult11 = "{\"msg\":\"Error no tiene autorización 15C1%\",\"tipo\":\"danger\"}";
        String expResult12 = "{\"msg\":\"Permisos inexistentes intente nuevamente%\",\"tipo\":\"danger\"}";
        
        String result1 = instance.actualizarRolPermiso(jsonActualizarRolPermiso);
        String result2 = instance.actualizarRolPermiso(jsonFaltaIdRol);
        String result3 = instance.actualizarRolPermiso(jsonIdRolNulo);
        String result4 = instance.actualizarRolPermiso(jsonNoExisteRol);
        String result5 = instance.actualizarRolPermiso(jsonFaltaIdPermiso);
        String result6 = instance.actualizarRolPermiso(jsonIdPermisoNulo);
        String result7 = instance.actualizarRolPermiso(jsonFaltaNombre);
        String result8 = instance.actualizarRolPermiso(jsonNombreNulo);
        String result9 = instance.actualizarRolPermiso(jsonFaltaNombreUnico);
        String result10 = instance.actualizarRolPermiso(jsonFaltaToken);
        String result11 = instance.actualizarRolPermiso(jsonFormatoToken);
        String result12 = instance.actualizarRolPermiso(jsonFaltanPermisos);

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
        assertEquals(expResult12, result12);
        
        System.out.println("OK ActualizarRolPermiso!");
    }

    /**
     * Test of getPermisos method, of class wsPermiso.
     */
    @Test
    public void testGetPermisos() {
        System.out.println("* wsPermisoTest: testGetPermisos()");
        String jsonListadoBien = "{'token':'282e1e2b-a963-46f5-ac42-b9364617fcbf'}";
        String jsonFaltaToken = "{'token':''}";
        String jsonTokenFormato = "{'token':'282e1e2b-b9364617fcbf'}";
        wsPermiso instance = new wsPermiso();
        String expResult1 = "{\"msg\":\"\",\"tipo\":\"\",\"colGridPer\":["
                + "{\"idPermiso\":1,\"nombre\":\"Muu_Mantenimiento\",\"estado\":true,\"nombreUnico\":\"Muu_Mantenimiento\"},"
                + "{\"idPermiso\":2,\"nombre\":\"Muu_Reportes\",\"estado\":true,\"nombreUnico\":\"Muu_Repotes\"},"
                + "{\"idPermiso\":3,\"nombre\":\"Page_Municipio\",\"estado\":true,\"nombreUnico\":\"Page_Municipio\"},"
                + "{\"idPermiso\":4,\"nombre\":\"Page_Area\",\"estado\":true,\"nombreUnico\":\"Page_Area\"},"
                + "{\"idPermiso\":5,\"nombre\":\"Page_Area\",\"estado\":true,\"nombreUnico\":\"Page_Area\"}]}";
        String expResult2 = "{\"msg\":\"Error no tiene autorización 13C1%\",\"tipo\":\"danger\"}";
        String expResult3 = "{\"msg\":\"Error no tiene autorización 13C1%\",\"tipo\":\"danger\"}";
        String result1 = instance.getPermisos(jsonListadoBien);
        String result2 = instance.getPermisos(jsonFaltaToken);
        String result3 = instance.getPermisos(jsonTokenFormato);

        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("OK GetPermisos!");
    }

    /**
     * Test of getPermisosByRol method, of class wsPermiso.
     */
    @Test
    public void testGetPermisosByRol() {
        System.out.println("* wsPermisoTest: testGetPermisosByRol()");
        String jsonRolyTokenExiste = "{\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\",\"idRol\":\"1\"}";
        String jsonRolNoExiste = "{\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\",\"idRol\":\"1222\"}";
        String jsonFaltaRol = "{\"token\":\"282e1e2b-a963-46f5-ac42-b9364617fcbf\"}";
        String jsonFaltaToken = "{\"token\":\"\",\"idRol\":\"1\"}";
        String jsonTokenFormato = "{\"token\":\"282e1e2b-b9364617fcbf\",\"idRol\":\"12\"}";
        wsPermiso instance = new wsPermiso();
        String expResult1 = "{\"msg\":\"\",\"tipo\":\"\",\"colGridPer\":["
                + "{\"idPermiso\":1,\"nombre\":\"Muu_Mantenimiento\",\"estado\":true,\"nombreUnico\":\"Muu_Mantenimiento\"},"
                + "{\"idPermiso\":2,\"nombre\":\"Muu_Reportes\",\"estado\":true,\"nombreUnico\":\"Muu_Repotes\"},"
                + "{\"idPermiso\":3,\"nombre\":\"Page_Municipio\",\"estado\":true,\"nombreUnico\":\"Page_Municipio\"},"
                + "{\"idPermiso\":4,\"nombre\":\"Page_Area\",\"estado\":true,\"nombreUnico\":\"Page_Area\"},"
                + "{\"idPermiso\":5,\"nombre\":\"Page_Area\",\"estado\":true,\"nombreUnico\":\"Page_Area\"}]}";
        String expResult2 = "{\"msg\":\"Rol inexistente%\",\"tipo\":\"danger\"}";
        String expResult3 = "{\"msg\":\"Rol inexistente%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"Error no tiene autorización 14C1%\",\"tipo\":\"danger\"}";
        String expResult5 = "{\"msg\":\"Error no tiene autorización 14C1%\",\"tipo\":\"danger\"}";

        String result1 = instance.getPermisosByRol(jsonRolyTokenExiste);
        String result2 = instance.getPermisosByRol(jsonRolNoExiste);
        String result3 = instance.getPermisosByRol(jsonFaltaRol);
        String result4 = instance.getPermisosByRol(jsonFaltaToken);
        String result5 = instance.getPermisosByRol(jsonTokenFormato);

        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult5, result5);
        System.out.println("OK GetPermisosByRol!");
    }

}
