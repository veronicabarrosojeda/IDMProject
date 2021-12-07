package Business.Interfaces;

import Common.DTO.dtoBarrio;
import Common.DTO.dtoBarrioGrid;
import Common.DTO.dtoMensaje;
import java.util.List;

public interface IBarrio {

    dtoMensaje altaBarrio(dtoBarrio barrio);
    dtoMensaje bajaBarrio(Integer idBarrio);
    dtoMensaje modificarBarrio(dtoBarrio barrio);
    List<dtoBarrioGrid> listarBarrios();
    
}