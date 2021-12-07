package Business.Facade;

import Business.Interfaces.IMunicipio;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoMunicipio;
import java.util.List;

public abstract class FMunicipio implements IMunicipio {

    @Override
    public dtoMensaje altaMunicipio(dtoMunicipio mun) {
        return Business.Logic.Controllers.CMunicipio.getInstanceCMunicipio().altaMunicipio(mun);
    }

    @Override
    public dtoMensaje modificarMunicipio(dtoMunicipio municipio) {
        return Business.Logic.Controllers.CMunicipio.getInstanceCMunicipio().modificarMunicipio(municipio);
    }

    @Override
    public dtoMensaje borrarMunicipio(Integer idMun) {
        return Business.Logic.Controllers.CMunicipio.getInstanceCMunicipio().borrarMunicipio(idMun);
    }

    @Override
    public List<dtoMunicipio> getMunicipios() {
        return Business.Logic.Controllers.CMunicipio.getInstanceCMunicipio().getMunicipios();
    }
}