package ServiceLayer.WS;

import Business.Entities.Rol;
import Business.Entities.Usuario;
import DataAccess.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class wsUsuarioTest {

    public wsUsuarioTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteUsuario = s.createSQLQuery("DELETE FROM `usuario`").addEntity(Usuario.class);
        Query deleteRol = s.createSQLQuery("DELETE FROM `rolpermiso`").addEntity(Rol.class);
        Query deleteRolPermiso = s.createSQLQuery("DELETE FROM `rol`");
        Query deletePermiso = s.createSQLQuery("DELETE FROM `permiso`");
        Query insertUsuarios = s.createSQLQuery("INSERT INTO `usuario` "
                + "(`idUsuario`, `nombre`, `apellido`, `nickname`, `password`, `token`, `correo`, `estado`, `nroIdentidad`, `tipoIdentidad`, `idRol`, `telefono`) VALUES\n"
                + "(1, 'usuario1', 'usuario1', 'usuario1', '81dc9bdb52d04dc20036dbd8313ed055', '1a88613b-fd40-4933-b64a-7624bc515b2c', NULL, 1, NULL, NULL, 1, NULL),\n"
                + "(2, 'usuario2', 'usuario2', 'usuario2', '81dc9bdb52d04dc20036dbd8313ed055', '0995f00f-20ac-4c70-95a4-6355c27d5858', 'usuario2@usuario2.usuario2', 1, NULL, NULL, 2, NULL),\n"
                + "(3, 'usuario3', 'usuario3', 'usuario3', '81dc9bdb52d04dc20036dbd8313ed055', NULL, 'usuario3@usuario3.usuario3', 1, NULL, NULL, 1, NULL),\n"
                + "(4, 'usuario4', 'usuario4', 'usuario4', '81dc9bdb52d04dc20036dbd8313ed055', NULL, 'usuario4@usuario4.usuario4', 1, NULL, NULL, 2, NULL);");
        Query insertRoles = s.createSQLQuery("INSERT INTO `rol` (`idRol`, `nombre`, `descripcion`, `estado`) VALUES\n"
                + "(1, 'Administrador', 'Administrador del sistema', 1),\n"
                + "(2, 'Entidades Externas', 'Empresas', 1);");
        Query insertRolesPermisos = s.createSQLQuery("INSERT INTO `rolpermiso` (`idRol`, `idPermiso`) VALUES\n"
                + "(1, 1),(1, 2),(1, 3),(1, 4),(1, 5),(2, 1),(2, 2),(2, 3);");
        Query insertPermiso = s.createSQLQuery("INSERT INTO `permiso` (`idPermiso`, `nombreUnico`, `nombre`, `estado`, `url`, `descripcion`) VALUES\n"
                + "(1, 'Muu_Mantenimiento', 'Mantenimiento', 1, NULL, ''),\n"
                + "(2, 'Muu_Repotes', 'Reportes', 1, NULL, ''),\n"
                + "(3, 'Page_Municipio', 'Municipio', 1, '', NULL),\n"
                + "(4, 'Page_Area', 'Area', 1, '', NULL),\n"
                + "(5, 'Page_Area', 'Area', 1, '', NULL);");
        Query setAutoIncrementUsuario = s.createSQLQuery("ALTER TABLE `usuario` AUTO_INCREMENT = 1");
        Query setAutoIncrementRol = s.createSQLQuery("ALTER TABLE `rol` AUTO_INCREMENT = 1");
        Query setAutoIncrementRolPermiso = s.createSQLQuery("ALTER TABLE `rolpermiso` AUTO_INCREMENT = 1");
        deleteUsuario.executeUpdate();
        deleteRol.executeUpdate();
        deletePermiso.executeUpdate();
        deleteRolPermiso.executeUpdate();
        insertRoles.executeUpdate();
        insertPermiso.executeUpdate();
        insertRolesPermisos.executeUpdate();
        insertUsuarios.executeUpdate();
        setAutoIncrementUsuario.executeUpdate();
        setAutoIncrementRol.executeUpdate();
        setAutoIncrementRolPermiso.executeUpdate();
        tx.commit();
        s.close();
    }

    @AfterClass
    public static void tearDownClass() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Query deleteUsuario = s.createSQLQuery("DELETE FROM `usuario`").addEntity(Usuario.class);
        Query deleteRol = s.createSQLQuery("DELETE FROM `rolpermiso`").addEntity(Rol.class);
        Query deleteRolPermiso = s.createSQLQuery("DELETE FROM `rol`");
        Query deletePermiso = s.createSQLQuery("DELETE FROM `permiso`");
        deleteUsuario.executeUpdate();
        deleteRol.executeUpdate();
        deletePermiso.executeUpdate();
        deleteRolPermiso.executeUpdate();
        tx.commit();
        s.close();
    }

    /**
     * Test of getUsuarios method, of class wsUsuario.
     */
    /*@Test
    public void testGetUsuarios() {
        System.out.println("* wsUsuarioTest: testGetUsuarios()");
        String jsonListaCorrecto = "{'idUsuario': '1', 'token': '1a88613b-fd40-4933-b64a-7624bc515b2c'}";
        String jsonFaltaUsuariolog = "{'idUsuario': null, 'token': '1a88613b-fd40-4933-b64a-7624bc515b2c'}";
        String jsonFaltaUsuariolog2 = "{'token': '1a88613b-fd40-4933-b64a-7624bc515b2c'}";
        String jsonFaltaTokenDeSeguridad = "{'idUsuario': '1', 'token': ''}";
        wsUsuario instance = new wsUsuario();
        String expResult1 = ""
                + "{\"msg\":\"\",\"tipo\":\"\",\"colUsu\":[{\"idUsuario\":2,\"nombre\":\"usuario2\",\"apellido\":\"usuario2\",\"nickname\":\"usuario2\",\"estado\":\"Activo\",\"idRol\":1,\"nombreRol\":\"Administrador\"},"
                + "{\"idUsuario\":3,\"nombre\":\"usuario3\",\"apellido\":\"usuario3\",\"nickname\":\"usuario3\",\"estado\":\"Activo\",\"idRol\":1,\"nombreRol\":\"Administrador\"},"
                + "{\"idUsuario\":4,\"nombre\":\"usuario4\",\"apellido\":\"usuario4\",\"nickname\":\"usuario4\",\"estado\":\"Activo\",\"idRol\":1,\"nombreRol\":\"Administrador\"}]}";
        String expResult = "";
        String expResult3 = "";
        String expResult4 = "{\"msg\":\"Error inesperado intente nuevamente 4C1%\",\"tipo\":\"danger\"}";
        String result = instance.getUsuarios(jsonListaCorrecto);

        assertEquals(expResult1, result);
        System.out.println("OK GetUsuarios!");
    }*/

    /**
     * Test of altaUsuario method, of class wsUsuario.
     */
    @Test
    public void testAltaUsuario() {
        System.out.println("* wsUsuarioTest: testAltaUsuario()");
        String jsonAltaUsuario = "{'nombre':'usuario5,'apellido':'usuario5','nickname':'usuario5','correo':'usuario5@usuario5.usuario5','password':'81dc9bdb52d04dc20036dbd8313ed055','ritpassword':'81dc9bdb52d04dc20036dbd8313ed055','idRol':'1','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";
        String jsonAltaUsuarioSinCorreo = "{'nombre':'usuario20,'apellido':'usuario20','nickname':'usuario20','correo':'usuario20@usuario20.usuario20','password':'81dc9bdb52d04dc20036dbd8313ed055','ritpassword':'81dc9bdb52d04dc20036dbd8313ed055','idRol':'1','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";

        String jsonFaltaNombre = "{'nombre':'','apellido':'usuario6','nickname':'usuario6','correo':'usuario6@usuario6.usuario6','password':'81dc9bdb52d04dc20036dbd8313ed055','ritpassword':'81dc9bdb52d04dc20036dbd8313ed055','idRol':'1','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";
        String jsonNombreLargo = "{'nombre':'LargoMayorA25Caracteredasdndkasndasndkasdnkasnddkdasdasda','apellido':'usuario7','nickname':'usuario7','correo':'usuario7@usuario6.usuario7','password':'81dc9bdb52d04dc20036dbd8313ed055','ritpassword':'81dc9bdb52d04dc20036dbd8313ed055','idRol':'1','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";

        String jsonFaltaApellido = "{'nombre':'usuario8','apellido':'usuario8','nickname':'usuario8','correo':'usuario8@usuario8.usuario8','password':'81dc9bdb52d04dc20036dbd8313ed055','ritpassword':'81dc9bdb52d04dc20036dbd8313ed055','idRol':'1','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";
        String jsonApellidoLargo = "{'nombre':'usuario9','apellido':'NicknameLargoDeMasDe25Caracteres','nickname':'usuario9','correo':'usuario9@usuario9.usuario9','password':'81dc9bdb52d04dc20036dbd8313ed055','ritpassword':'81dc9bdb52d04dc20036dbd8313ed055','idRol':'1','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";

        String jsonFaltaNickname = "{'nombre':'usuario10','apellido':'usuario10','nickname':'','correo':'usuario10@usuario10.usuario10','password':'81dc9bdb52d04dc20036dbd8313ed055','ritpassword':'81dc9bdb52d04dc20036dbd8313ed055','idRol':'1','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";
        String jsonRepiteNickname = "{'nombre':'usuario11','apellido':'usuario11','nickname':'usuario1','correo':'usuario11@usuario10.usuario10','password':'81dc9bdb52d04dc20036dbd8313ed055','ritpassword':'81dc9bdb52d04dc20036dbd8313ed055','idRol':'1','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";
        String jsonNicknameLargo = "{'nombre':'usuario12','apellido':'usuario12','nickname':'NicknameLargoDeMasDe25Caracteres','correo':'usuario12@usuario12.usuario12','password':'81dc9bdb52d04dc20036dbd8313ed055','ritpassword':'81dc9bdb52d04dc20036dbd8313ed055','idRol':'1','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";

        String jsonFaltaPassword1 = "{'nombre':'usuario13','apellido':'usuario13','nickname':'usuario13','correo':'usuario13@usuario13.usuario13','password':'','ritpassword':'81dc9bdb52d04dc20036dbd8313ed055','idRol':'1','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";
        String jsonFaltaRitPass = "{'nombre':'usuario14','apellido':'usuario14','nickname':'usuario14','correo':'usuario14@usuario14.usuario14','password':'81dc9bdb52d04dc20036dbd8313ed055','ritpassword':'','idRol':'1','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";
        String jsonFaltanLasPass = "{'nombre':'usuario15','apellido':'usuario15','nickname':'usuario15','correo':'usuario15@usuario15.usuario15','password':'','ritpassword':'','idRol':'1','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";
        String jsonNoCoincidenLasPass = "{'nombre':'usuario16','apellido':'usuario16','nickname':'usuario16','correo':'usuario16@usuario16.usuario16','password':'81dc9bdb52d04dc20036dbd8313ed055','ritpassword':'85dc9bdb52d04dc20036dbd8313ed055','idRol':'1','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";

        String jsonFaltaRol = "{'nombre':'usuario17','apellido':'usuario17','nickname':'usuario17','correo':'usuario17@usuario17.usuario17','password':'81dc9bdb52d04dc20036dbd8313ed055','ritpassword':'81dc9bdb52d04dc20036dbd8313ed055','idRol':'','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";
        String jsonNoExisteRol = "{'nombre':'usuario18','apellido':'usuario18','nickname':'usuario18','correo':'usuario18@usuario18.usuario18','password':'81dc9bdb52d04dc20036dbd8313ed055','ritpassword':'81dc9bdb52d04dc20036dbd8313ed055','idRol':'345','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";
        String jsonFaltaTokenSeguridad = "{'nombre':'usuario19,'apellido':'usuario19','nickname':'usuario19','correo':'usuario19@usuario19.usuario19','password':'81dc9bdb52d04dc20036dbd8313ed055','ritpassword':'81dc9bdb52d04dc20036dbd8313ed055','idRol':'1','token':'','mode':'A'}";

        String jsonCorreoLargo = "{'nombre':'usuario21,'apellido':'usuario21','nickname':'usuario21','correo':'Supera los 100 caracteresssssssssssssssssssssssssssssssssssss deeeeeeeeeeeeeeeeeee comprobacionnnnnnn','password':'81dc9bdb52d04dc20036dbd8313ed055','ritpassword':'81dc9bdb52d04dc20036dbd8313ed055','idRol':'1','token':'1a88613b-fd40-4933-b64a-7624bc515b2c','mode':'A'}";
        wsUsuario instance = new wsUsuario();
        String expResult1 = "{\"msg\":\"Usuario registrado correctamente%\",\"tipo\":\"success\"}";
        String expResult16 = "{\"msg\":\"Usuario registrado correctamente%\",\"tipo\":\"success\"}";

        String expResult2 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult3 = "{\"msg\":\"Nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";

        String expResult4 = "{\"msg\":\"Apellido requerido%\",\"tipo\":\"danger\"}";
        String expResult5 = "{\"msg\":\"Apellido supera los 25 caracteres%\",\"tipo\":\"danger\"}";

        String expResult6 = "{\"msg\":\"Nombre de usuario requerido%\",\"tipo\":\"danger\"}";
        String expResult7 = "{\"msg\":\"El nombre de usuario ya fue asignado a otro usuario%\",\"tipo\":\"danger\"}";
        String expResult8 = "{\"msg\":\"Nombre de usuario supera los 15 caracteres%\",\"tipo\":\"danger\"}";

        String expResult9 = "{\"msg\":\"Contraseña requerida%\",\"tipo\":\"danger\"}";
        String expResult10 = "{\"msg\":\"Repeticion de contraseña requerida%\",\"tipo\":\"danger\"}";
        String expResult11 = "{\"msg\":\"Contraseña requerida%Repeticion de contraseña requerida%\",\"tipo\":\"danger\"}";
        String expResult12 = "{\"msg\":\"Las contraseñas no coinciden%\",\"tipo\":\"danger\"}";

        String expResult13 = "{\"msg\":\"Rol requerido%\",\"tipo\":\"danger\"}";
        String expResult14 = "{\"msg\":\"El rol no existe%\",\"tipo\":\"danger\"}";
        String expResult15 = "{\"msg\":\"Error no tiene autorización 1C2%\",\"tipo\":\"danger\"}";
        String expResult17 = "{\"msg\":\" El correo no puede superar los 100 caracteres%\",\"tipo\":\"danger\"}";

        String result1 = instance.altaUsuario(jsonAltaUsuario);
        String result2 = instance.altaUsuario(jsonFaltaNombre);
        String result3 = instance.altaUsuario(jsonNombreLargo);

        String result4 = instance.altaUsuario(jsonFaltaApellido);
        String result5 = instance.altaUsuario(jsonApellidoLargo);

        String result6 = instance.altaUsuario(jsonFaltaNickname);
        String result7 = instance.altaUsuario(jsonRepiteNickname);
        String result8 = instance.altaUsuario(jsonNicknameLargo);

        String result9 = instance.altaUsuario(jsonFaltaPassword1);
        String result10 = instance.altaUsuario(jsonFaltaRitPass);
        String result11 = instance.altaUsuario(jsonFaltanLasPass);
        String result12 = instance.altaUsuario(jsonNoCoincidenLasPass);

        String result13 = instance.altaUsuario(jsonFaltaRol);
        String result14 = instance.altaUsuario(jsonNoExisteRol);
        String result15 = instance.altaUsuario(jsonFaltaTokenSeguridad);
        String result16 = instance.altaUsuario(jsonAltaUsuarioSinCorreo);
        String result17 = instance.altaUsuario(jsonCorreoLargo);

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
        assertEquals(expResult13, result13);
        assertEquals(expResult14, result14);
        assertEquals(expResult15, result15);
        assertEquals(expResult16, result16);
        assertEquals(expResult17, result17);

        System.out.println("OK Test AltaUsuario!");
    }

    /**
     * Test of modificarUsuario method, of class wsUsuario.
     */
    @Test
    public void testModificarUsuario() {
        System.out.println("* wsUsuarioTest: testModificarUsuario()");
        String jsonModificarUsuario2A = ""
                + "{'nombre':'nuevoUsuario2','apellido':'nuevoUsuario2','nickname':'nuevoUsuario2','correo':'nuevoUsuario2@nuevoUsuario2.com','idRol':'1','idUsuario':2,'mode':'U','token':'1a88613b-fd40-4933-b64a-7624bc515b2c'}";
        String jsonModificarUsuario3 = ""
                + "{'nombre':'Usuario3MOD','apellido':'Usuario3MOD','nickname':'Usuario3MOD','correo':'','idRol':'1','idUsuario':3,'mode':'U','token':'1a88613b-fd40-4933-b64a-7624bc515b2c'}";

        String jsonFaltaNombre = ""
                + "{'nombre':'','apellido':'Usuario2modi','nickname':'Usuario2modi','correo':'Usuario2modi@Usuario2modi.com','idRol':'1','idUsuario':2,'mode':'U','token':'1a88613b-fd40-4933-b64a-7624bc515b2c'}";
        String jsonNombreLargo = ""
                + "{'nombre':'LargoMayorA25Caracteredasdndkasndasndkasdnkasnddkdasdasda','apellido':'Usuario2modi','nickname':'Usuario2modi','correo':'Usuario2modi@Usuario2modi.com','idRol':'1','idUsuario':2,'mode':'U','token':'1a88613b-fd40-4933-b64a-7624bc515b2c'}";

        String jsonFaltaApellido = ""
                + "{'nombre':'Usuario2modi','apellido':'','nickname':'Usuario2modi','correo':'Usuario2modi@Usuario2modi.com','idRol':'1','idUsuario':2,'mode':'U','token':'1a88613b-fd40-4933-b64a-7624bc515b2c'}";
        String jsonApellidoLargo = ""
                + "{'nombre':'Usuario2modi','apellido':'LargoMayorA25Caracteredasdndkasndasndkasdnkasnddkdasdasda','nickname':'Usuario2modi','correo':'Usuario2modi@Usuario2modi.com','idRol':'1','idUsuario':2,'mode':'U','token':'1a88613b-fd40-4933-b64a-7624bc515b2c'}";

        String jsonFaltaNickname = ""
                + "{'nombre':'Usuario2modi','apellido':'Usuario2modi','nickname':'','correo':'Usuario2modi@Usuario2modi.com','idRol':'1','idUsuario':2,'mode':'U','token':'1a88613b-fd40-4933-b64a-7624bc515b2c'}";
        String jsonRepiteNickname = ""
                + "{'nombre':'Usuario2modi','apellido':'Usuario2modi','nickname':'usuario1','correo':'Usuario2modi@Usuario2modi.com','idRol':'1','idUsuario':2,'mode':'U','token':'1a88613b-fd40-4933-b64a-7624bc515b2c'}";
        String jsonNicknameLargo = ""
                + "{'nombre':'Usuario2modi','apellido':'Usuario2modi','nickname':'NicknameLargoDeMasDe15CaracteresUsuario2modi','correo':'Usuario2modi@Usuario2modi.com','idRol':'1','idUsuario':2,'mode':'U','token':'1a88613b-fd40-4933-b64a-7624bc515b2c'}";

        String jsonFaltaRol = ""
                + "{'nombre':'Usuario2modi','apellido':'Usuario2modi','nickname':'Usuario2modi','correo':'Usuario2modi@Usuario2modi.com','idRol':'','idUsuario':2,'mode':'U','token':'1a88613b-fd40-4933-b64a-7624bc515b2c'}";
        String jsonNoExisteRol = ""
                + "{'nombre':'Usuario2modi','apellido':'Usuario2modi','nickname':'Usuario2modi','correo':'Usuario2modi@Usuario2modi.com','idRol':'654','idUsuario':2,'mode':'U','token':'1a88613b-fd40-4933-b64a-7624bc515b2c'}";

        String jsonNoExisteUsuario1 = ""
                + "{'nombre':'Usuario2modi','apellido':'Usuario2modi','nickname':'Usuario2modi','correo':'Usuario2modi@Usuario2modi.com','idRol':'1','idUsuario':222,'mode':'U','token':'1a88613b-fd40-4933-b64a-7624bc515b2c'}";
        String jsonNoExisteUsuarioNull = ""
                + "{'nombre':'Usuario2modi','apellido':'Usuario2modi','nickname':'Usuario2modi','correo':'Usuario2modi@Usuario2modi.com','idRol':'1','idUsuario':null,'mode':'U','token':'1a88613b-fd40-4933-b64a-7624bc515b2c'}";

        String jsonFaltaTokenSeguridad = ""
                + "{'nombre':'Usuario2modi','apellido':'Usuario2modi','nickname':'Usuario2modi','correo':'Usuario2modi@Usuario2modi.com','idRol':'1,'idUsuario':2,'mode':'U','token':''}";

        String jsonCorreoLargo = ""
                + "{'nombre':'Usuario2modi','apellido':'Usuario2modi','nickname':'Usuario2modi','correo':'Supera los 100 caracteresssssssssssssssssssssssssssssssssssss deeeeeeeeeeeeeeeeeee comprobacionnnnnnn','idRol':'1,'idUsuario':2,'mode':'U','token':'1a88613b-fd40-4933-b64a-7624bc515b2c'}";

        wsUsuario instance = new wsUsuario();
        String expResult1 = "{\"msg\":\"Usuario modificado con éxito%\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"Usuario modificado con éxito%\",\"tipo\":\"success\"}";

        String expResult3 = "{\"msg\":\"Nombre requerido%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"Nombre supera los 25 caracteres%\",\"tipo\":\"danger\"}";

        String expResult5 = "{\"msg\":\"Apellido requerido%\",\"tipo\":\"danger\"}";
        String expResult6 = "{\"msg\":\"Apellido supera los 25 caracteres%\",\"tipo\":\"danger\"}";

        String expResult7 = "{\"msg\":\"Nombre de usuario requerido%\",\"tipo\":\"danger\"}";
        String expResult8 = "{\"msg\":\"El nombre de usuario ya fue asignado a otro usuario%\",\"tipo\":\"danger\"}";
        String expResult9 = "{\"msg\":\"Nombre de usuario supera los 15 caracteres%\",\"tipo\":\"danger\"}";

        String expResult10 = "{\"msg\":\"Rol requerido%\",\"tipo\":\"danger\"}";
        String expResult11 = "{\"msg\":\"El rol no existe%\",\"tipo\":\"danger\"}";

        String expResult12 = "{\"msg\":\"Usuario inexistente 3C2%\",\"tipo\":\"danger\"}";
        String expResult13 = "{\"msg\":\"Usuario inexistente 3C2%\",\"tipo\":\"danger\"}";

        String expResult14 = "{\"msg\":\"Error no tiene autorización 3C3%\",\"tipo\":\"danger\"}";
        String expResult15 = "{\"msg\":\" El correo no puede superar los 100 caracteres%\",\"tipo\":\"danger\"}";

        String result1 = instance.modificarUsuario(jsonModificarUsuario2A);
        String result2 = instance.modificarUsuario(jsonModificarUsuario3);

        String result3 = instance.modificarUsuario(jsonFaltaNombre);
        String result4 = instance.modificarUsuario(jsonNombreLargo);

        String result5 = instance.modificarUsuario(jsonFaltaApellido);
        String result6 = instance.modificarUsuario(jsonApellidoLargo);

        String result7 = instance.modificarUsuario(jsonFaltaNickname);
        String result8 = instance.modificarUsuario(jsonRepiteNickname);
        String result9 = instance.modificarUsuario(jsonNicknameLargo);

        String result10 = instance.modificarUsuario(jsonFaltaRol);
        String result11 = instance.modificarUsuario(jsonNoExisteRol);

        String result12 = instance.modificarUsuario(jsonNoExisteUsuario1);
        String result13 = instance.modificarUsuario(jsonNoExisteUsuarioNull);

        String result14 = instance.modificarUsuario(jsonFaltaTokenSeguridad);
        String result15 = instance.modificarUsuario(jsonCorreoLargo);

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
        assertEquals(expResult13, result13);
        assertEquals(expResult14, result14);
        assertEquals(expResult15, result15);
        System.out.println("OK Test ModificarUsuario!");
    }

    /**
     * Test of borrarUsuario method, of class wsUsuario.
     */
    @Test
    public void testBorrarUsuario() {
        System.out.println("* wsUsuarioTest: testBorrarUsuario()");
        String idUsuarioExiste = "{\"idUsuario\":4,\"token\":\"'1a88613b-fd40-4933-b64a-7624bc515b2c'\"}";
        String idUsuarioNoExiste = "{\"idUsuario\":604,\"token\":\"'1a88613b-fd40-4933-b64a-7624bc515b2c'\"}";
        String idUsuarioNull = "{\"token\":\"'1a88613b-fd40-4933-b64a-7624bc515b2c'\"}";
        String faltaTokenSeguridad = "{\"idUsuario\":5,\"token\":\"\"}";
        String faltaTokenSeguridad2 = "{\"idUsuario\":5}";
        String tokenDeSeguridadIncorrecto = "{\"idUsuario\":5,\"token\":\"'1a88613b-fd40-4933-b6error4a-7624bc515b2c'\"}";

        wsUsuario instance = new wsUsuario();

        String expResult1 = "{\"msg\":\"Usuario eliminado con éxito%\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"Usuario inexistente 2C2%\",\"tipo\":\"danger\"}";
        String expResult3 = "{\"msg\":\"Usuario inexistente 2C2%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"Error no tiene autorización 2C3%\",\"tipo\":\"danger\"}";
        String expResult5 = "{\"msg\":\"Error no tiene autorización 2C3%\",\"tipo\":\"danger\"}";
        String expResult6 = "{\"msg\":\"Error no tiene autorización 2C3%\",\"tipo\":\"danger\"}";

        String result1 = instance.borrarUsuario(idUsuarioExiste);
        String result2 = instance.borrarUsuario(idUsuarioNoExiste);
        String result3 = instance.borrarUsuario(idUsuarioNull);
        String result4 = instance.borrarUsuario(faltaTokenSeguridad);
        String result5 = instance.borrarUsuario(faltaTokenSeguridad2);
        String result6 = instance.borrarUsuario(tokenDeSeguridadIncorrecto);

        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult5, result5);
        assertEquals(expResult6, result6);

        System.out.println("OK Test BorrarUsuario!");
    }

    /**
     * Test of logIn method, of class wsUsuario.
     */
    @Test/*testear con token!*/
    public void testLogIn() {
        System.out.println("* wsUsuarioTest: testLogIn()");
        String jsonLogIn = "{'nickname':'usuario1','password':'81dc9bdb52d04dc20036dbd8313ed055'}";
        String jsonFaltaNickname = "{'nickname':'','password':'81dc9bdb52d04dc20036dbd8313ed055'}";
        String jsonFaltaPassword = "{'nickname':'usuario1','password':''}";
        String jsonNoSonCorrectos1 = "{'nickname':'usuariooo1','password':'81dc9bdb52d04036dbd8313ed055'}";
        String jsonNoSonCorrectos2 = "{'nickname':'usuario1','password':'81dc9bdb52d04036dbd8313ed055'}";
        String jsonNoSonCorrectos3 = "{'nickname':'usu1','password':'81dc9bdb52d04036dbd8313fgsded055'}";
        wsUsuario instance = new wsUsuario();
  
//        String expResult1 = "{\"idUsuario\":1,\"nombre\":\"usuario1\",\"apellido\":\"usuario1\",\"nickname\":\"usuario1\","
//                + "\"token\":\"7cda242c-8b81-4943-8d61-b275be31429f\""
//                + ",\"correo\":\"undefined\",\"estado\":false,\"isLogged\":true,"
//                + "\"dtoRol\":{\"idRol\":2,\"nombre\":\"Entidades Externas\",\"descripcion\":\"Empresas\",\"estado\":true}"
//                + ",\"colPermiso\":[{\"idPermiso\":8,\"nombreUnico\":\"Muu_Seguridad\",\"nombre\":\"MenÃº Seguridad\",\"estado\":true},"
//                + "{\"idPermiso\":11,\"nombreUnico\":\"Page_CambioDeClave\",\"nombre\":\"Cambio de Clave\",\"estado\":true},"
//                + "{\"idPermiso\":12,\"nombreUnico\":\"Page_MapaSolicitudes\",\"nombre\":\"Mapa de Solicitudes\",\"estado\":true},"
//                + "{\"idPermiso\":14,\"nombreUnico\":\"Muu_Servicios\",\"nombre\":\"MenÃº Servicios\",\"estado\":true}],\"notificar\":false}";
//               
        String expResult2 = "{\"estado\":false,\"isLogged\":false,\"status\":\"danger\",\"msg\":\"Por favor ingrese su nombre de usuario\"}";
        String expResult3 = "{\"estado\":false,\"isLogged\":false,\"status\":\"danger\",\"msg\":\"Por favor ingrese su contraseña\"}";
        String expResult4 = "{\"estado\":false,\"isLogged\":false,\"status\":\"danger\",\"msg\":\"Nombre de usuario o contreseña incorrecto\"}";
        String expResult5 = "{\"estado\":false,\"isLogged\":false,\"status\":\"danger\",\"msg\":\"Nombre de usuario o contreseña incorrecto\"}";
        String expResult6 = "{\"estado\":false,\"isLogged\":false,\"status\":\"danger\",\"msg\":\"Nombre de usuario o contreseña incorrecto\"}";
        //String result1 = instance.logIn(jsonLogIn);
        String result2 = instance.logIn(jsonFaltaNickname);
        String result3 = instance.logIn(jsonFaltaPassword);
        String result4 = instance.logIn(jsonNoSonCorrectos1);
        String result5 = instance.logIn(jsonNoSonCorrectos2);
        String result6 = instance.logIn(jsonNoSonCorrectos3);
//assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult5, result5);
        assertEquals(expResult6, result6);
        System.out.println("OK LogIn!");

    }
    /**
     * Test of logOut method, of class wsUsuario.
     */
    /*    @Test
    public void testLogOut() {
        System.out.println("logOut");
        String userToken = "";
        wsUsuario instance = new wsUsuario();
        instance.logOut(userToken);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */
    /**
     * Test of isLogged method, of class wsUsuario.
     */
    /*   @Test
    public void testIsLogged() {
        System.out.println("* wsUsuarioTest: testIsLogged()");
        String userToken = "0995f00f-20ac-4c70-95a4-6355c27d5858";
        String userToken2 = "token-de-prueba-inexistente-6864376219e0";
        wsUsuario instance = new wsUsuario();
        String expResult1 = "true";
        String expResult2 = "false";
        String result1 = instance.isLogged(userToken);
        String result2 = instance.isLogged(userToken2);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        System.out.println("OK isLogged!");
    }
     */
    /**
     * Test of cambiarClave method, of class wsUsuario.
     */
    /*@Test
    public void testCambiarClave() {
        System.out.println("* wsUsuarioTest: testCambiarClave()");
        String jsonCambiarClave = "{'idUsuario':'3','passwordActual':'usuario3','passwordNueva':'123', 'passwordNuevaReiterada':'123'}";
        String jsonFaltaClaveActual = "{'idUsuario':'3','passwordActual':'','passwordNueva':'1234', 'passwordNuevaReiterada':'1234'}";
        String jsonFaltaClaveNueva = "{'idUsuario':'3','passwordActual':'123','passwordNueva':'', 'passwordNuevaReiterada':'1234'}";
        String jsonFaltaReiteracion = "{'idUsuario':'3','passwordActual':'123','passwordNueva':'1234', 'passwordNuevaReiterada':''}";
        String jsonDiferentesClaves = "{'idUsuario':'3','passwordActual':'123','passwordNueva':'1234', 'passwordNuevaReiterada':'12345'}";
        wsUsuario instance = new wsUsuario();
        String expResult1 = "{\"msg\":\"La contraseña fue modificada\",\"tipo\":\"success\"}";
        String expResult2 = "{\"msg\":\"La contraseña actual es requerida%\",\"tipo\":\"danger\"}";
        String expResult3 = "{\"msg\":\"La nueva contraseña es requerida%\",\"tipo\":\"danger\"}";
        String expResult4 = "{\"msg\":\"La reiteracion de la nueva contraseña es requerida%\",\"tipo\":\"danger\"}";
        String expResult5 = "{\"msg\":\"Las contraseñas no coinciden%\",\"tipo\":\"danger\"}";
        String result1 = instance.cambiarClave(jsonCambiarClave);
        String result2 = instance.cambiarClave(jsonFaltaClaveActual);
        String result3 = instance.cambiarClave(jsonFaltaClaveNueva);
        String result4 = instance.cambiarClave(jsonFaltaReiteracion);
        String result5 = instance.cambiarClave(jsonDiferentesClaves);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult5, result5);
        System.out.println("OK CambiarClave!");

    }
     */
    /**
     * Test of actualizarEmpresaUsuario method, of class wsUsuario.
     */
    /*
    @Test//{"idUsuario":"1","empresas":[{"idEmpresa":1,"nombre":"Ecotenco","rut":345456547,"descripcion":"Prueba","estado":true}]}
    public void testActualizarEmpresaUsuario() {
        System.out.println("actualizarEmpresaUsuario");
        String json = "";
        wsUsuario instance = new wsUsuario();
        String expResult = "";
        String result = instance.actualizarEmpresaUsuario(json);
        assertEquals(expResult, result);
         //TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }    
     */
}
