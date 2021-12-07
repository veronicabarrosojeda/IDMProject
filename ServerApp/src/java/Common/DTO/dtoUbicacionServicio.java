/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common.DTO;

import Business.Entities.Barrio;
import java.io.Serializable;

public class dtoUbicacionServicio {

    public Integer idUbicacion;
    public Barrio barrio;
    public String calle;
    public String nroPuerta;
    public String apto;
    public String entreCalles;
    public String nroManzana;
    public String nroSolar;
    public String nroPadron;
    // public Serializable coordenadas;
    public Float latitud;
    public Float longitud;
    public Boolean coordenadas;
    public Boolean ubicacionManual;

}
