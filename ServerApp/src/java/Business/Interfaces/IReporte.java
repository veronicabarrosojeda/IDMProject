/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Interfaces;

import Common.DTO.dtoGenerarReporte;
import java.util.List;

/**
 *
 * @author Facundo
 */
public interface IReporte {
    List<Object> getReporteById(dtoGenerarReporte data);
}
