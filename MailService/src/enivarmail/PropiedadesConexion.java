
package enivarmail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropiedadesConexion {

    private String basedatos;
    private String usuario;
    private String clave;
    private String iphost;
    private String puerto;

    public PropiedadesConexion() {

        Properties propiedades = new Properties();
        InputStream entrada = null;

        try {
            entrada = ConexionSql.class.getResourceAsStream("propiedades.properties");
            propiedades.load(entrada);
            this.basedatos = propiedades.getProperty("basedatos");
            this.usuario = propiedades.getProperty("usuario");
            this.clave = propiedades.getProperty("clave");
            this.iphost = propiedades.getProperty("iphost");
            this.puerto = propiedades.getProperty("puerto");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (entrada != null) {
                try {
                    entrada.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getBasedatos() {
        return basedatos;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }

    public String getIphost() {
        return iphost;
    }

    public String getPuerto() {
        return puerto;
    }

}
