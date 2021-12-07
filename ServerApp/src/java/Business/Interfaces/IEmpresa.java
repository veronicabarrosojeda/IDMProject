/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Interfaces;

import Common.DTO.dtoEmpresa;
import Common.DTO.dtoMensaje;
import java.util.List;

/**
 *
 * @author Facundo
 */
public interface IEmpresa {
    dtoMensaje altaEmpresa(dtoEmpresa emp);
    dtoMensaje bajaEmpresa(Integer emC);
    dtoMensaje modificarEmpresa(dtoEmpresa emp);
    List<dtoEmpresa> getEmpresas();
    dtoMensaje getEmpresasByUser(Integer idUsuario);
    List<dtoEmpresa> getEmpresasByService(Long idServicio);
}
