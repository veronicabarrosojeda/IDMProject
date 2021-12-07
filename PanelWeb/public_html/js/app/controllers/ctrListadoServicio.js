PanelWeb.controller("ctrListarSolicitudes", ['$scope', '$http', '$q', '$location', 'usuarioService', 'listItemService', 'msgServices', 'uiGridValidateService', 'sessionService', 'servicioService', function ($scope, $http, $q, $location, usuarioService, listItemService, msgServices, uiGridValidateService, sessionService, servicioService) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 15)
                        rest = true;
                }
                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }
        verificarPermiso();
        $scope.titulo = "Servicios";
        $scope.idPermiso = "Servicios";
        $scope.listarServicios = listarServicios;
        $scope.verSolicitantes = verSolicitantes;
        $scope.verEmpresas = verEmpresas;
        $scope.verSupervisores = verSupervisores;
        $scope.verServicio = verServicio;
        $scope.colIdEmpresa = [];
        $scope.myData = [];
        $scope.colOrigenes = [];
        $scope.colTipoUbic = [];
        $scope.idOrigen = "-1";
        $scope.idTipoUbic = "-1";
        init();

        function getOrigen()
        {
            var origen0 = {};
            origen0.clave = "-1";
            origen0.descripcion = "--------------- Todos ---------------";

            var origen = {};
            origen.clave = "1";
            origen.descripcion = "Call Center";

            var origen2 = {};
            origen2.clave = "2";
            origen2.descripcion = "App Movil";

            $scope.colOrigenes.push(origen0);
            $scope.colOrigenes.push(origen);
            $scope.colOrigenes.push(origen2);

        }

        function getTipoUbic()
        {
            var tipoUbic0 = {};
            tipoUbic0.clave = "-1";
            tipoUbic0.descripcion = "-------------- Todos ---------------";

            var tipoUbic1 = {};
            tipoUbic1.clave = "1";
            tipoUbic1.descripcion = "Punto en Mapa";

            var tipoUbic2 = {};
            tipoUbic2.clave = "2";
            tipoUbic2.descripcion = "Ubicación Manual";

            $scope.colTipoUbic.push(tipoUbic0);
            $scope.colTipoUbic.push(tipoUbic1);
            $scope.colTipoUbic.push(tipoUbic2);
        }

        function init()
        {
            getOrigen();
            getTipoUbic();
            listarServicios();
        }

        function verServicio(entity)
        {
            usuarioService.verificarPermiso(25).then(function (data) {
                if (data == true)
                {
                    sessionService.set("idServicio_verServicio", entity.idServicio);
                    $location.path("/verServicio");
                } else
                {
                    swal("No tiene permisos para ingresar a esta página");
                }
            });

        }

        function verSolicitantes(entity)
        {
            usuarioService.verificarPermiso(24).then(function (data) {
                if (data == true)
                {
                    sessionService.set("idServicio_solicitantes", entity.idServicio);
                    $location.path("/solicitantesServicio");
                } else
                {
                    swal("No tiene permisos para ingresar a esta página");
                }
            });

        }

        function verEmpresas(entity)
        {
            usuarioService.verificarPermiso(22).then(function (data) {
                if (data == true)
                {
                    sessionService.set("idServicio_empresa", entity.idServicio);
                    $location.path("/servicioEmpresas");
                } else
                {
                    swal("No tiene permisos para ingresar a esta página");
                }
            });

        }

        function verSupervisores(entity)
        {
            usuarioService.verificarPermiso(23).then(function (data) {
                if (data == true)
                {
                    sessionService.set("idServicio_supervisor", entity.idServicio);
                    $location.path("/servicioSupervisores");
                } else
                {
                    swal("No tiene permisos para ingresar a esta página");
                }
            });
        }

        function setEmpresa()
        {
            var empresas = JSON.parse(sessionService.get('empresas'));
            for (var i = 0; i < empresas.length; i++)
            {
                $scope.colIdEmpresa.push(empresas[i].idEmpresa);
            }
            if (empresas == null || empresas.length == 0)
                $scope.colIdEmpresa.push(-1);
        }

        function listarServicios()
        {
            setEmpresa();
            var dataWhere = {};
            dataWhere.colEstados = ['1', '2', '3', '4', '5'];
            dataWhere.tipoDeServicios = [];
            dataWhere.colEmpresas = $scope.colIdEmpresa;
            dataWhere.tipoUbicacion = $scope.idTipoUbic;
            var token = sessionService.get("token");
            dataWhere.token = token;
            dataWhere.origen = $scope.idOrigen;
            servicioService.getServiciosForStatus(dataWhere).then(function (data) {
                $scope.myData = data;
            });

        }
        ;
        //Configuración de la grilla
        $scope.gridOptions = {
            enableSorting: true,
            enableCellEditOnFocus: true,
            enablePinning: true,
            showGridFooter: true,
            enableFiltering: true,
            paginationPageSizes: [25, 50, 75],
            paginationPageSize: 10,
            data: 'myData',
            columnDefs: [
                {field: 'Solicitantes', width: '13%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-user btn-primary" ng-click="grid.appScope.verSolicitantes(row.entity)"></button></center>'},
                {field: 'Empresas', width: '13%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-briefcase btn-primary"  ng-click="grid.appScope.verEmpresas(row.entity)"></button></center>'},
                {field: 'Supervisores', width: '13%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-hand-right btn-primary"  ng-click="grid.appScope.verSupervisores(row.entity)"></button></center>'},
                {field: 'Ver Servicio', width: '13%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-eye-open btn-primary"  ng-click="grid.appScope.verServicio(row.entity)"></button></center>'},
                {field: 'idServicio', width: '15%', enableCellEdit: false, visible: true, displayName: 'Nro. Servicio'},
                {field: 'descripcionServicio', width: '30%', enableCellEdit: false, visible: false},
                {field: 'estado', width: '15%', enableCellEdit: false, visible: false},
                {field: 'nombre', width: '15%', enableCellEdit: false, displayName: 'Estado'},
                {field: 'idTipoServicio', width: '20%', enableCellEdit: false, visible: false},
                {field: 'nombreTipoServicio', width: '35%', enableCellEdit: false, displayName: 'Tipo de Servicio'},
                {field: 'descripcionTipoServicio', width: '20%', enableCellEdit: false, visible: false},
                {field: 'idArea', width: '20%', enableCellEdit: false, visible: false},
                {field: 'nombreArea', width: '20%', enableCellEdit: false, displayName: 'Area IDM'},
                {field: 'idBarrio', width: '20%', enableCellEdit: false, visible: false},
                {field: 'nombreBarrio', width: '20%', enableCellEdit: false, visible: false},
                {field: 'idMunicipio', width: '20%', enableCellEdit: false, visible: false},
                {field: 'nombreMunicipiom', width: '20%', enableCellEdit: false, visible: false},
                {field: 'idUbicacionServicio', width: '20%', enableCellEdit: false, visible: false},
                {field: 'calle', width: '20%', enableCellEdit: false, visible: false},
                {field: 'nroPuerta', width: '20%', enableCellEdit: false, visible: false},
                {field: 'apto', width: '20%', enableCellEdit: false, visible: false},
                {field: 'entreCalles', width: '20%', enableCellEdit: false, visible: false},
                {field: 'nroManzana', width: '20%', enableCellEdit: false, visible: false},
                {field: 'nroSolar', width: '20%', enableCellEdit: false, visible: false},
                {field: 'nroPadron', width: '20%', enableCellEdit: false, visible: false},
                {field: 'latitud', width: '20%', enableCellEdit: false, visible: false},
                {field: 'longitud', width: '20%', enableCellEdit: false, visible: false},
                {field: 'fechaIngreso', width: '20%', enableCellEdit: false},
                {field: 'fechaModificacion', width: '25%', enableCellEdit: false, displayName: 'Última Modificación'},
                {field: 'qtSolicitantes', width: '20%', enableCellEdit: false, displayName: 'Cant. Solicitantes'},
                {field: 'DiasIngreso', width: '20%', enableCellEdit: false, displayName: 'Dias Pendientes'}
            ],
            enableGridMenu: true,
            exporterCsvFilename: 'solicitudesDeServicio.csv',
            exporterPdfDefaultStyle: {fontSize: 9},
            exporterPdfTableStyle: {margin: [30, 30, 30, 30]},
            exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: true, color: 'red'},
            exporterPdfHeader: {text: "My Header", style: 'headerStyle'},
            exporterPdfFooter: function (currentPage, pageCount) {
                return {text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle'};
            },
            exporterPdfCustomFormatter: function (docDefinition) {
                docDefinition.styles.headerStyle = {fontSize: 22, bold: true};
                docDefinition.styles.footerStyle = {fontSize: 10, bold: true};
                return docDefinition;
            },
            exporterPdfOrientation: 'portrait',
            exporterPdfPageSize: 'LETTER',
            exporterPdfMaxGridWidth: 500,
            exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location"))
        };


    }]);
