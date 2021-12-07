/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Logic.Controllers;

import Common.DTO.dtoGenerarReporte;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Facundo
 */
public class CReporte {

    private static CReporte ICReporte;

    public static CReporte getNewInstanceReporte() {
        if (ICReporte == null) {
            return new CReporte();
        }

        return ICReporte;
    }

    public List<Object> reporteByTipo(dtoGenerarReporte data) {

        List<Object> colDataReporte = new ArrayList<Object>();
        try {
            colDataReporte = DataAccess.Persistencia.PReporte.newInstancePReporte().reporteByTipo(data);
        } catch (Exception ex) {
            return null;
        }
        return colDataReporte;
    }

}
