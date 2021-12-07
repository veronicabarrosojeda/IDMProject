package Business.Facade;

import Business.Interfaces.IBarrio;
import Common.DTO.dtoBarrio;
import Common.DTO.dtoBarrioGrid;
import Common.DTO.dtoMensaje;
import java.util.List;

public abstract class FBarrio implements  IBarrio{
    
    public dtoMensaje altaBarrio(dtoBarrio barrio)
    {
        return Business.Logic.Controllers.CBarrio.getNewInstanceCBarrio().altaBarrio(barrio);
    }
    
    public dtoMensaje bajaBarrio(Integer idBarrio)
    {
        return Business.Logic.Controllers.CBarrio.getNewInstanceCBarrio().bajaBarrio(idBarrio);
    }
    
    public dtoMensaje modificarBarrio(dtoBarrio barrio)
    {
        return Business.Logic.Controllers.CBarrio.getNewInstanceCBarrio().modificarBarrio(barrio);
    }
    
    public List<dtoBarrioGrid> listarBarrios() {
        return Business.Logic.Controllers.CBarrio.getNewInstanceCBarrio().listarBarrios();
    }
    
}