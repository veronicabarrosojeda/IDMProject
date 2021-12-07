package Business.Interfaces;

import Common.DTO.dtoMensaje;
import Common.DTO.dtoMunicipio;
import java.util.List;

public interface IMunicipio {
    
     dtoMensaje altaMunicipio(dtoMunicipio mun);
     dtoMensaje modificarMunicipio(dtoMunicipio municipio);
     dtoMensaje borrarMunicipio(Integer idMun);
     List<dtoMunicipio> getMunicipios(); 
}