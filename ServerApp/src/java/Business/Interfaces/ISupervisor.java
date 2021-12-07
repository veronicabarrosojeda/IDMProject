
package Business.Interfaces;

import Common.DTO.dtoMensaje;
import Common.DTO.dtoServicio;
import Common.DTO.dtoSupervisor;
import java.util.List;

public interface ISupervisor {
    
    dtoMensaje getSupervisores(String token);

    dtoMensaje altaSupervisor(dtoSupervisor sup);
    
    dtoMensaje borrarSupervisor(dtoSupervisor sup);
    
    dtoMensaje modificarSupervisor(dtoSupervisor sup);
    
    dtoMensaje getSupervisoresByServicio(dtoServicio serv);
    
}
