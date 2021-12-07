package Business.Interfaces;

import Common.DTO.dtoArea;
import Common.DTO.dtoMensaje;
import java.util.List;

public interface IArea {
    dtoMensaje altaArea(dtoArea are);
    
    dtoMensaje modificarArea(dtoArea area);
    
    List<dtoArea> listarAreas();
    
    List<dtoArea> listarAreaFiltro(String filtro);
    
    dtoArea getArea(int idArea);
    
    dtoMensaje bajaArea(int idArea);
}