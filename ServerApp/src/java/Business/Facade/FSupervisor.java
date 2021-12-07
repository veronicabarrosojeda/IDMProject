/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Facade;

import Common.DTO.dtoSupervisor;
import Business.Interfaces.ISupervisor;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoServicio;
import java.util.List;

public abstract class FSupervisor implements ISupervisor {
    
    public dtoMensaje getSupervisores(String token){
        return Business.Logic.Controllers.CSupervisor.getNewInstanceCSupervisor().getSupervisores(token);
    } 
    
    public dtoMensaje altaSupervisor(dtoSupervisor sup){
        return Business.Logic.Controllers.CSupervisor.getNewInstanceCSupervisor().altaSupervisor(sup);
    }
    
    public dtoMensaje borrarSupervisor(dtoSupervisor sup){
        return Business.Logic.Controllers.CSupervisor.getNewInstanceCSupervisor().borrarSupervisor(sup);
    }
    
    public dtoMensaje modificarSupervisor(dtoSupervisor sup){
        return Business.Logic.Controllers.CSupervisor.getNewInstanceCSupervisor().modificarSupervisor(sup);
    }
    
    public dtoMensaje getSupervisoresByServicio(dtoServicio serv)
    {
    return Business.Logic.Controllers.CSupervisor.getNewInstanceCSupervisor().getSupervisoresByServicio(serv);
    }
    
    
}
