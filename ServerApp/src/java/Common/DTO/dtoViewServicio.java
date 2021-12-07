/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common.DTO;

import java.awt.Point;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class dtoViewServicio {

    public long idServicio;
    public String descripcionServicio;
    public short estado;
    public String imgEstado;
    public String nombre;
    public int idUsuarioFuncionario;
    public int tipoOrigen;
    public int idTipoServicio;
    public String nombreTipoServicio;
    public String descripcionTipoServicio;
    public int idArea;
    public String nombreArea;
    public int idUbicacionServicio;
    public String calle;
    public String nroPuerta;
    public String apto;
    public String entreCalles;
    public String nroManzana;
    public String nroSolar;
    public String nroPadron;
    public float latitud; 
    public float longitud;
    public int idBarrio;
    public String nombreBarrio;
    public int idMunicipio;
    public String nombreMunicipiom;
    public Date fechaIngreso;
    public Date fechaModificacion;
    public String rutaImagen;    
    public int qtSolicitantes;
    public int DiasIngreso;

}
