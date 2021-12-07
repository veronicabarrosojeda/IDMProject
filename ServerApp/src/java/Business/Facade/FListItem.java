package Business.Facade;

import Business.Interfaces.IListItem;
import Common.DTO.dtoListItem;
import java.util.List;

public abstract class FListItem implements IListItem {
    public List<dtoListItem> getRoles()
    {
         return Business.Logic.Controllers.CListItem.getNewInstanceListItem().getRoles();
    }
    
    public List<dtoListItem> getBarriosPorMunicipio(Integer municipio){
        return Business.Logic.Controllers.CListItem.getNewInstanceListItem().getBarriosPorMunicipio(municipio);
    }
    
    public List<dtoListItem> getDetTipoById(Integer idTipo)
    {
         return Business.Logic.Controllers.CListItem.getNewInstanceListItem().getDetTipoById(idTipo);
    }
    
    public List<dtoListItem> getCategorias()
    {
         return Business.Logic.Controllers.CListItem.getNewInstanceListItem().getCategorias();
    }
    public List<dtoListItem> getTipoServicio()
    {
        return Business.Logic.Controllers.CListItem.getNewInstanceListItem().getTipoServicio();
    }
    
     public List<dtoListItem> getEmpresas()
    {
        return Business.Logic.Controllers.CListItem.getNewInstanceListItem().getEmpresa();
    }
     public List<dtoListItem> getMunicipios()
    {
         return Business.Logic.Controllers.CListItem.getNewInstanceListItem().getMunicipios();
    }
    public List<dtoListItem> getAreas()
    {
         return Business.Logic.Controllers.CListItem.getNewInstanceListItem().getAreas();
    }
    
    
}
