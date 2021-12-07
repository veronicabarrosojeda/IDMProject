package Business.Interfaces;

import Common.DTO.dtoMensaje;
import Common.DTO.dtoTipoServicio;
import Common.DTO.dtoTipoServicioGrid;
import java.util.List;

public interface ITipoServicio {
    
    dtoMensaje altaTipoServicio(dtoTipoServicio tipoServicio);
    dtoMensaje bajaTipoServicio(Integer idTipoServicio);
    dtoMensaje modificarTipoServicio(dtoTipoServicio tipoServicio);
    List<dtoTipoServicioGrid> listarTipoServicio();
    
}
