package enivarmail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class ConexionSql {
    public PropiedadesConexion prop = new PropiedadesConexion();
    public String url = "jdbc:mysql://"+prop.getIphost()+":"+prop.getPuerto()+"/"+prop.getBasedatos();
    
    public ResultSet consultar(Connection conn,String sql) {
        ResultSet resultado;
        try {
            
            Statement sentencia = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultado = sentencia.executeQuery(sql);
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }        
        return resultado;
    }
    
    public boolean ejecutar(Connection conn, String sql) {
        try {
            Statement sentencia = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            sentencia.executeUpdate(sql);         
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }        return true;
    }
    
    public Connection Conectar(){

       Connection link = null;

       try{
           Class.forName("org.gjt.mm.mysql.Driver");
           link = DriverManager.getConnection(this.url, this.prop.getUsuario(), this.prop.getClave());
       }catch(Exception ex){

           JOptionPane.showMessageDialog(null, ex);

       }
       return link;

   }
}
