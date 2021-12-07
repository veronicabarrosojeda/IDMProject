/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Interfaces;

import Common.DTO.dtoMensaje;
import Common.DTO.dtoRol;
import java.util.List;

/**
 *
 * @author Facundo
 */
public interface IRol {
    public dtoMensaje getRoles(String token);
    
    public dtoMensaje altaRol(dtoRol rol);   

    public dtoMensaje modificarRol(dtoRol rol);
    
    public dtoMensaje borrarRol(dtoRol rol);
}
