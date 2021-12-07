
var PanelWeb = angular.module('PanelWeb', [
    'ngRoute', 'angular-md5', 'ui.grid', 'ngConfirm', 'uiGmapgoogle-maps',
    'ui.grid.edit', 'ui.grid.pagination', 'ui.grid.saveState',
    'ui.grid.selection', 'ui.grid.exporter', 'ui.grid.pinning',
    'ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.moveColumns'
    ,'angularjs-dropdown-multiselect', 'chart.js', 'ui.bootstrap'
]).directive('showErrors', function () {
    return {
        restrict: 'A',
        require: '^form',
        link: function (scope, el, attrs, formCtrl) {
            // find the text box element, which has the 'name' attribute
            var inputEl = el[0].querySelector("[name]");
            // convert the native text box element to an angular element
            var inputNgEl = angular.element(inputEl);
            // get the name on the text box so we know the property to check
            // on the form controller
            var inputName = inputNgEl.attr('name');

            // only apply the has-error class after the user leaves the text box
            inputNgEl.bind('blur', function () {
                el.toggleClass('has-error', formCtrl[inputName].$invalid);
            })
        }
    }
}).directive('pwCheck', [function () {
        return {
            require: 'ngModel',
            link: function (scope, elem, attrs, ctrl) {
                var firstPassword = '#' + attrs.pwCheck;
                elem.add(firstPassword).on('keyup', function () {
                    scope.$apply(function () {
                        var v = elem.val() === $(firstPassword).val();
                        ctrl.$setValidity('pwmatch', v);
                    });
                });
            }
        }
    }]).config(function (uiGmapGoogleMapApiProvider) {
    uiGmapGoogleMapApiProvider.configure({
        //    key: 'your api key',
        v: '3.20', //defaults to latest 3.X anyhow
        libraries: 'weather,geometry,visualization'
    });
});
PanelWeb.config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {
        $routeProvider.when('/login', {
            templateUrl: 'login.html',
            controller: 'ctrLogin'
        }).when('/menu', {
            templateUrl: 'menu.html',
            controller: 'ctrMenu'
        }).when('/inicio', {
            templateUrl: 'pages/inicio.html',
            controller: ''
        }).when('/municipio', {
            templateUrl: 'pages/municipio.html',
            controller: 'ctrMunicipio'
        }).when('/usuario', {
            templateUrl: 'pages/usuario.html',
            controller: 'ctrUsuario'
        }).when('/listadoArea', {
            templateUrl: 'pages/listadoArea.html',
            controller: 'ctrListarArea'
        }).when('/area', {
            templateUrl: 'pages/area.html',
            controller: 'ctrArea'
        }).when('/modificarArea', {
            templateUrl: 'pages/modificarArea.html',
            controller: 'ctrModificarArea'
        }).when('/barrio', {
            templateUrl: 'pages/barrio.html',
            controller: 'ctrBarrio'
        }).when('/altaSubArea', {
            templateUrl: 'pages/subArea.html',
            controller: 'ctrSubArea'
        }).when('/subArea', {
            templateUrl: 'pages/subArea.html',
            controller: 'ctrSubArea'
        }).when('/permiso', {
            templateUrl: 'pages/permiso.html',
            controller: 'ctrPermiso'
        }).when('/rol', {
            templateUrl: 'pages/rol.html',
            controller: 'ctrRol'
        }).when('/cambioclave', {
            templateUrl: 'pages/cambioclave.html',
            controller: 'ctrClave'
        }).when('/empresausuario', {
            templateUrl: 'pages/empresausuario.html',
            controller: 'ctrEmpresaUsuario'
        }).when('/mapaSolicitudes', {
            templateUrl: 'pages/mapaSolicitudes.html',
            controller: 'ctrMapaSolicitudes'
        }).when('/supervisor', {
            templateUrl: 'pages/supervisor.html',
            controller: 'ctrSupervisor'
        }).when('/altaSolicitudServicio', {
            templateUrl: 'pages/altaSolicitudServicio.html',
            controller: 'ctrAltaServicio'
        }).when('/empresa', {
            templateUrl: 'pages/empresa.html',
            controller: 'ctrEmpresa'
        }).when('/listarSolicitudes', {
            templateUrl: 'pages/listadoServicios.html',
            controller: 'ctrListarSolicitudes'
        }).when('/solicitantesServicio', {
            templateUrl: 'pages/solicitantes.html',
            controller: 'ctrSolicitantes'
        }).when('/servicioEmpresas', {
            templateUrl: 'pages/empresasServicio.html',
            controller: 'ctrServicioEmpresa'
        }).when('/servicioSupervisores', {
            templateUrl: 'pages/supervisoresServicio.html',
            controller: 'ctrServicioSupervisor'
        }).when('/reporteCantidadSol', {
            templateUrl: 'pages/reporteCantidadSol.html',
            controller: 'ctrRerporteCantidadSolicitudes'
        }).when('/reporteCantidadSolEstado', {
            templateUrl: 'pages/reportePorEstado.html',
            controller: 'ctrRerporteCantidadSolicitudes'
        }).when('/tipoServicio', {
            templateUrl: 'pages/tipoServicio.html',
            controller: 'ctrTipoServicio'
        }).when('/verServicio', {
            templateUrl: 'pages/verServicio.html',
            controller: 'ctrVerServicio'
        }).when('/solicitudesSupervisor', {
            templateUrl: 'pages/serviciosPorSupervisor.html',
            controller: 'ctrListarSolicitudesSupervisor'
        }).when('/reporteCobertura', {
            templateUrl: 'pages/reporteCobertura.html',
            controller: 'ctrReporteCobertura'
        }).when('/reporteTipoOrigen', {
            templateUrl: 'pages/reporteOrigen.html',
            controller: 'ctrReporteOrigen'
        }).when('/reporteTipoServicio', {
            templateUrl: 'pages/reporteTipoServicio.html',
            controller: 'ctrReporteTipoServicio'
        }).when('/serviciosPorEmpresa', {
            templateUrl: 'pages/serviciosPorEmpresa.html',
            controller: 'ctrListarSolicitudesEmpresa'
        }).otherwise({redirectTo: '/'});
    }]);
