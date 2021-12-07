/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer.WS;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("ws")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ServiceLayer.WS.CrossOriginResourceSharingFilter.class);
        resources.add(ServiceLayer.WS.WsExternal.class);
        resources.add(ServiceLayer.WS.WsListItem.class);
        resources.add(ServiceLayer.WS.WsReporte.class);
        resources.add(ServiceLayer.WS.wsArea.class);
        resources.add(ServiceLayer.WS.wsBarrio.class);
        resources.add(ServiceLayer.WS.wsEmpresa.class);
        resources.add(ServiceLayer.WS.wsMunicipio.class);
        resources.add(ServiceLayer.WS.wsPermiso.class);
        resources.add(ServiceLayer.WS.wsRol.class);
        resources.add(ServiceLayer.WS.wsServicio.class);
        resources.add(ServiceLayer.WS.wsSubArea.class);
        resources.add(ServiceLayer.WS.wsSupervisor.class);
        resources.add(ServiceLayer.WS.wsTipoServicio.class);
        resources.add(ServiceLayer.WS.wsUsuario.class);
    }
    
}
