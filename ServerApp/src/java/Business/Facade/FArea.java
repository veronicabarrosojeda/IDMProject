package Business.Facade;

import Business.Interfaces.IArea;
import Common.DTO.dtoArea;
import Common.DTO.dtoMensaje;
import java.util.List;

public abstract class FArea implements IArea {

    public dtoMensaje altaArea(dtoArea area) {
        return Business.Logic.Controllers.CArea.getNewInstanceCArea().altaArea(area);
    }
    
    public dtoMensaje modificarArea(dtoArea area) {
        return Business.Logic.Controllers.CArea.getNewInstanceCArea().modificarArea(area);
    }
    
    public dtoMensaje bajaArea(int idArea) {
        return Business.Logic.Controllers.CArea.getNewInstanceCArea().bajaArea(idArea);
    }
   
     public List<dtoArea> listarAreas()
    {
        return Business.Logic.Controllers.CArea.getNewInstanceCArea().listarAreas();
    }
    
    public List<dtoArea> listarAreaFiltro(String filtro)
    {
        return Business.Logic.Controllers.CArea.getNewInstanceCArea().listarAreaFiltro(filtro);
    }
    
    public dtoArea getArea(int idArea)
    {
        return Business.Logic.Controllers.CArea.getNewInstanceCArea().getArea(idArea);
    }

}