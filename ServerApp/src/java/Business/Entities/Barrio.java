package Business.Entities;
// Generated 18/04/2017 09:37:33 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Barrio generated by hbm2java
 */
public class Barrio  implements java.io.Serializable {


     private Integer idBarrio;
     private Municipio municipio;
     private String nombre;
     private String descripcion;
     private boolean estado;
     private Set ubicacionservicios = new HashSet(0);

    public Barrio() {
    }

	
    public Barrio(Municipio municipio, String nombre, boolean estado) {
        this.municipio = municipio;
        this.nombre = nombre;
        this.estado = estado;
    }
    public Barrio(Municipio municipio, String nombre, String descripcion, boolean estado, Set ubicacionservicios) {
       this.municipio = municipio;
       this.nombre = nombre;
       this.descripcion = descripcion;
       this.estado = estado;
       this.ubicacionservicios = ubicacionservicios;
    }
   
    public Integer getIdBarrio() {
        return this.idBarrio;
    }
    
    public void setIdBarrio(Integer idBarrio) {
        this.idBarrio = idBarrio;
    }
    public Municipio getMunicipio() {
        return this.municipio;
    }
    
    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public boolean isEstado() {
        return this.estado;
    }
    
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    public Set getUbicacionservicios() {
        return this.ubicacionservicios;
    }
    
    public void setUbicacionservicios(Set ubicacionservicios) {
        this.ubicacionservicios = ubicacionservicios;
    }




}

