/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Facade;

import Business.Interfaces.ISubArea;
import Business.Logic.Controllers.CSubArea;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoSubArea;
import java.util.List;

public abstract class FSubArea implements ISubArea {
    
    
    public List<dtoSubArea> getSubAreas() {
        return CSubArea.getInstanceCSubArea().getSubAreas();
    }
    public List<dtoSubArea> getSubAreasDeArea(Integer idArea){
        return CSubArea.getInstanceCSubArea().getSubAreasDeArea(idArea);
    }
    public dtoMensaje altaSubArea(dtoSubArea subArea){
        return CSubArea.getInstanceCSubArea().altaSubArea(subArea);
    }
    public dtoMensaje borrarSubArea(Integer idSubArea){
        return CSubArea.getInstanceCSubArea().borrarSubArea(idSubArea);
    }
    public dtoMensaje modificarSubArea(dtoSubArea subArea){
        return CSubArea.getInstanceCSubArea().modificarSubArea(subArea);
    }
}
