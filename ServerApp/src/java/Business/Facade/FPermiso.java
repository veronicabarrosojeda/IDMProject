package Business.Facade;

import Business.Interfaces.IPermiso;
import Business.Logic.Controllers.CPermiso;
import Common.DTO.dtoGridPermiso;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoRol;
import Common.DTO.dtoRolPermiso;
import java.util.List;

public abstract class FPermiso implements IPermiso{
    
    @Override
    public  dtoMensaje getPermisos(dtoRol rol) {
       return CPermiso.getNewInstanceCPermiso().getPermisos(rol);
    }
    
    @Override
    public  dtoMensaje getPermisosByRol(dtoRol rol) {
       return CPermiso.getNewInstanceCPermiso().getPermisosByRol(rol);
    }
    @Override
    public dtoMensaje actualizarPermisosRol(dtoRolPermiso rolpermiso)
    {
        return CPermiso.getNewInstanceCPermiso().actualizarPermisosRol(rolpermiso);
    }

}
