package Business.Interfaces;

import Common.DTO.dtoListItem;
import java.util.List;

public interface IListItem {
    List<dtoListItem> getRoles();
    List<dtoListItem> getBarriosPorMunicipio(Integer municipio);
    List<dtoListItem> getDetTipoById(Integer idTipo);
    List<dtoListItem> getCategorias();
    List<dtoListItem> getTipoServicio();
    List<dtoListItem> getEmpresas();
    List<dtoListItem> getMunicipios();
    List<dtoListItem> getAreas();

    
    
}
