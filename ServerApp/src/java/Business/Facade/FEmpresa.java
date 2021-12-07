/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Facade;

import Business.Interfaces.IEmpresa;
import Common.DTO.dtoEmpresa;
import Common.DTO.dtoMensaje;
import java.util.List;
/**
 *
 * @author Facundo
 */
public abstract class FEmpresa implements IEmpresa {
    
    public List<dtoEmpresa> getEmpresas()
    {
        return Business.Logic.Controllers.CEmpresa.getNewInstanceCEmpresa().getEmpresas();
    }
    
    public dtoMensaje getEmpresasByUser(Integer idUsuario)
    {
        return Business.Logic.Controllers.CEmpresa.getNewInstanceCEmpresa().getEmpresasByUser(idUsuario);
    }
    
    public dtoMensaje altaEmpresa(dtoEmpresa emp) {
        return Business.Logic.Controllers.CEmpresa.getNewInstanceCEmpresa().altaEmpresa(emp);
    }
    
    public dtoMensaje bajaEmpresa(Integer emC) {
        return Business.Logic.Controllers.CEmpresa.getNewInstanceCEmpresa().bajaEmpresa(emC);
    }
    
    public dtoMensaje modificarEmpresa(dtoEmpresa emp) {
        return Business.Logic.Controllers.CEmpresa.getNewInstanceCEmpresa().modificarEmpresa(emp);
    }
    
    public List<dtoEmpresa> getEmpresasByService(Long idServicio)
    {
        return Business.Logic.Controllers.CEmpresa.getNewInstanceCEmpresa().getEmpresasByService(idServicio);
    }
    
}
