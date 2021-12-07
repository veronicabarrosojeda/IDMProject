/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Facade;

import Business.Interfaces.IRol;
import Business.Logic.Controllers.CRol;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoRol;
import java.util.List;


public abstract class FRol implements IRol{
   
    @Override
    public dtoMensaje getRoles(String token)
    {
        return CRol.getNewInstanceIRol().getRoles(token);
    }
    
    @Override
    public dtoMensaje altaRol(dtoRol rol)
    {
        return CRol.getNewInstanceIRol().altaRol(rol);
    }
    
    @Override
    public dtoMensaje modificarRol(dtoRol rol)
    {
        return CRol.getNewInstanceIRol().modificarRol(rol);
    }
    
    @Override
    public dtoMensaje borrarRol(dtoRol rol)
    {
        return CRol.getNewInstanceIRol().borrarRol(rol);
    }
}
