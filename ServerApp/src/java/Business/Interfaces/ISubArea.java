/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Interfaces;

import Common.DTO.dtoMensaje;
import Common.DTO.dtoSubArea;
import java.util.List;

public interface ISubArea {

    List<dtoSubArea> getSubAreas();

    List<dtoSubArea> getSubAreasDeArea(Integer idArea);

    dtoMensaje altaSubArea(dtoSubArea subArea);

    dtoMensaje borrarSubArea(Integer idSubArea);
    dtoMensaje modificarSubArea(dtoSubArea subArea);
}
