package Business.Entities;
// Generated 18/04/2017 09:37:33 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Tiposervicio generated by hbm2java
 */
public class Tiposervicio  implements java.io.Serializable {


     private Integer idTipoServicio;
     private Area area;
     private String nombre;
     private String descripcion;
     private boolean estado;
     private Integer idEmpresa;
     private Set servicios = new HashSet(0);

    public Tiposervicio() {
    }

	
    public Tiposervicio(String nombre, boolean estado) {
        this.nombre = nombre;
        this.estado = estado;
    }
    public Tiposervicio(Area area, String nombre, String descripcion, boolean estado, Integer idEmpresa, Set servicios) {
       this.area = area;
       this.nombre = nombre;
       this.descripcion = descripcion;
       this.estado = estado;
       this.idEmpresa = idEmpresa;
       this.servicios = servicios;
    }
   
    public Integer getIdTipoServicio() {
        return this.idTipoServicio;
    }
    
    public void setIdTipoServicio(Integer idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }
    public Area getArea() {
        return this.area;
    }
    
    public void setArea(Area area) {
        this.area = area;
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
    public Integer getIdEmpresa() {
        return this.idEmpresa;
    }
    
    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    public Set getServicios() {
        return this.servicios;
    }
    
    public void setServicios(Set servicios) {
        this.servicios = servicios;
    }




}

