/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Logic.Controllers;

import Business.Entities.Area;
import Business.Entities.Barrio;
import Business.Entities.Empresa;
import Business.Entities.Municipio;
import Business.Entities.Rol;
import Business.Entities.Tipodetalle;
import Business.Entities.Tiposervicio;
import Common.DTO.dtoListItem;
import java.util.ArrayList;
import java.util.List;


public class CListItem {

    private static CListItem ICListItem;

    public static CListItem getNewInstanceListItem() {
        if (ICListItem == null) {
            return new CListItem();
        }

        return ICListItem;
    }

    public List<dtoListItem> rolToListItem(List<Rol> colRoles) {
        List<dtoListItem> colDtoRoles = new ArrayList<dtoListItem>();

        for (Rol r : colRoles) {
            dtoListItem dtoListItem = new dtoListItem();
            dtoListItem.clave = r.getIdRol().toString();
            dtoListItem.descripcion = r.getNombre();
            colDtoRoles.add(dtoListItem);
        }

        return colDtoRoles;
    }

    public List<dtoListItem> municipioToListItem(List<Municipio> colMunicipios) {
        List<dtoListItem> colDtoMunicipios = new ArrayList<dtoListItem>();

        for (Municipio m : colMunicipios) {
            dtoListItem dtoListItem = new dtoListItem();
            dtoListItem.clave = m.getIdMunicipio().toString();
            dtoListItem.descripcion = m.getNombre();
            colDtoMunicipios.add(dtoListItem);
        }

        return colDtoMunicipios;
    }

    public List<dtoListItem> barrioToListItem(List<Barrio> colBarrios) {
        List<dtoListItem> colDtoBarrio = new ArrayList<dtoListItem>();

        for (Barrio b : colBarrios) {
            dtoListItem dtoListItem = new dtoListItem();
            dtoListItem.clave = b.getIdBarrio().toString();
            dtoListItem.descripcion = b.getNombre();
            colDtoBarrio.add(dtoListItem);
        }

        return colDtoBarrio;
    }

    public List<dtoListItem> categoriaToListItem(List<Tiposervicio> colCategorias) {
        List<dtoListItem> conDtoCategorias = new ArrayList<dtoListItem>();

        for (Tiposervicio s : colCategorias) {
            dtoListItem dtoListItem = new dtoListItem();
            dtoListItem.clave = s.getIdTipoServicio().toString();
            dtoListItem.descripcion = s.getNombre();
            conDtoCategorias.add(dtoListItem);
        }

        return conDtoCategorias;
    }

     public List<dtoListItem> TipoServicioToListItem(List<Tiposervicio> colTipo)
    {
        List<dtoListItem> colListItem = new ArrayList<dtoListItem>();        
        for(Tiposervicio dt : colTipo)
        {
            dtoListItem dtoListItem = new dtoListItem();
            dtoListItem.clave = ""+dt.getIdTipoServicio();
            dtoListItem.descripcion = dt.getNombre();
            colListItem.add(dtoListItem);
        }
        
        return colListItem;
    }
     
      public List<dtoListItem> EmpresaToListItem(List<Empresa> colEmpresa)
    {
        List<dtoListItem> colListItem = new ArrayList<dtoListItem>();        
        for(Empresa emp : colEmpresa)
        {
            dtoListItem dtoListItem = new dtoListItem();
            dtoListItem.clave = ""+emp.getIdEmpresa();
            dtoListItem.descripcion = emp.getNombre();
            colListItem.add(dtoListItem);
        }
        
        return colListItem;
    }
    
    
    
    public List<dtoListItem> getRoles()
    {        
        List<Rol> colRoles = DataAccess.Persistencia.PListItem.getNewInstance().getRoles();
        List<dtoListItem> colDtoRoles = rolToListItem(colRoles);        
        return colDtoRoles;
    }

    public List<dtoListItem> getMunicipios() {
        List<Municipio> colMunicipios = DataAccess.Persistencia.PListItem.getNewInstance().getMunicipios();
        List<dtoListItem> colDtoMunicipios = municipioToListItem(colMunicipios);
        return colDtoMunicipios;
    }

    public List<dtoListItem> getBarriosPorMunicipio(Integer municipio) {
        List<Barrio> colBarrios = DataAccess.Persistencia.PListItem.getNewInstance().getBarriosPorMunicipio(municipio);
        List<dtoListItem> colDtoBarrios = barrioToListItem(colBarrios);
        return colDtoBarrios;
    }

    public List<dtoListItem> DetTipoToListItem(List<Tipodetalle> colTipo) {
        List<dtoListItem> colListItem = new ArrayList<dtoListItem>();

        for (Tipodetalle dt : colTipo) {
            dtoListItem dtoListItem = new dtoListItem();
            dtoListItem.clave = "" + dt.getId().getIdTipoDetalle();
            dtoListItem.descripcion = dt.getNombre();
            colListItem.add(dtoListItem);
        }

        return colListItem;
    }

    public List<dtoListItem> getDetTipoById(Integer idTipo) {
        List<Tipodetalle> colTipo = DataAccess.Persistencia.PListItem.getNewInstance().getDetTipoById(idTipo);
        List<dtoListItem> colListItem = DetTipoToListItem(colTipo);
        return colListItem;
    }

    public List<dtoListItem> getCategorias() {
        List<Tiposervicio> colCategorias = DataAccess.Persistencia.PListItem.getNewInstance().getCategorias();
        List<dtoListItem> colDtoCategorias = categoriaToListItem(colCategorias);
        return colDtoCategorias;
    }

    
    public List<dtoListItem> getTipoServicio()
    {
        List<Tiposervicio> colTipoServicio = DataAccess.Persistencia.PListItem.getNewInstance().getTipoServicio();
        List<dtoListItem> colListItem = TipoServicioToListItem(colTipoServicio);        
        return colListItem;
    }
    
    public List<dtoListItem> getEmpresa()
    {
        List<Empresa> colEmresa = DataAccess.Persistencia.PListItem.getNewInstance().getEmpresas();
        List<dtoListItem> colListItem = EmpresaToListItem(colEmresa);        
        return colListItem;
    }
    
    public List<dtoListItem> getAreas() {
        List<Area> colAreas = DataAccess.Persistencia.PListItem.getNewInstance().getAreas();
        List<dtoListItem> colDtoAreas = areaToListItem(colAreas);
        return colDtoAreas;
    }
    
    public List<dtoListItem> areaToListItem(List<Area> colAreas) {
        List<dtoListItem> colDtoAreas = new ArrayList<dtoListItem>();

        for (Area m : colAreas) {
            dtoListItem dtoListItem = new dtoListItem();
            dtoListItem.clave = m.getIdArea().toString();
            dtoListItem.descripcion = m.getNombre();
            colDtoAreas.add(dtoListItem);
        }

        return colDtoAreas;
    }
    
}
