/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Facade;

import Business.Interfaces.IReporte;
import Business.Logic.Controllers.CReporte;
import Common.DTO.dtoGenerarReporte;
import java.util.List;

/**
 *
 * @author Facundo
 */
public abstract class FReporte implements IReporte {
    
    public List<Object> getReporteById(dtoGenerarReporte data)
    {
        return CReporte.getNewInstanceReporte().reporteByTipo(data);        
    }
    
}
