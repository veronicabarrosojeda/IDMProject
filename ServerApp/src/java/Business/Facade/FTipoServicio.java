package Business.Facade;

import Business.Interfaces.ITipoServicio;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoTipoServicio;
import Common.DTO.dtoTipoServicioGrid;
import java.util.List;

public abstract class FTipoServicio implements ITipoServicio {
    
    public dtoMensaje altaTipoServicio(dtoTipoServicio tipoServicio)
    {
        return Business.Logic.Controllers.CTipoServicio.getNewInstanceCTipoServicio().altaTipoServicio(tipoServicio);
    }
    
    public dtoMensaje bajaTipoServicio(Integer idTipoServicio)
    {
        return Business.Logic.Controllers.CTipoServicio.getNewInstanceCTipoServicio().bajaTipoServicio(idTipoServicio);
    }
    
    public dtoMensaje modificarTipoServicio(dtoTipoServicio tipoServicio)
    {
        return Business.Logic.Controllers.CTipoServicio.getNewInstanceCTipoServicio().modificarTipoServicio(tipoServicio);
    }
    
    public List<dtoTipoServicioGrid> listarTipoServicio() {
        return Business.Logic.Controllers.CTipoServicio.getNewInstanceCTipoServicio().listarTipoServicio();
    }
    
}
