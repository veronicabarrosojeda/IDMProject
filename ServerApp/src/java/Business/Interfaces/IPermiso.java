/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Interfaces;

import Common.DTO.dtoGridPermiso;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoRol;
import Common.DTO.dtoRolPermiso;
import java.util.List;

public interface IPermiso {
    
    dtoMensaje getPermisos(dtoRol rol);
    
    dtoMensaje getPermisosByRol(dtoRol idPermiso);
    
    dtoMensaje actualizarPermisosRol(dtoRolPermiso rolpermiso);
}
