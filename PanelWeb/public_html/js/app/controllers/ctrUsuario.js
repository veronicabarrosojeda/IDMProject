PanelWeb.controller("ctrUsuario", ['$scope', '$http', '$q', '$location', 'serviceConfig', 'usuarioService', 'listItemService', 'msgServices', 'uiGridValidateService', 'sessionService', 'md5', 'loginService', function ($scope, $http, $q, $location, serviceConfig, usuarioService, listItemService, msgServices, uiGridValidateService, sessionService, md5, loginService) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 7)
                        rest = true;
                }
                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }
        verificarPermiso();
        $scope.titulo = "Gestion de Usuarios";
        $scope.mySelections = [];
        $scope.usuarios = [];
        $scope.myData = [];
        $scope.formUsuario = {};
        $scope.cancelarModificacion = cancelarModificacion;
        $scope.habilitarModificacion = habilitarModificacion;
        $scope.modificarUsuario = modificarUsuario;
        $scope.agregarUsuario = agregarUsuario;
        $scope.listarUsuarios = listarUsuarios;
        $scope.borrarUsuario = borrarUsuario;
        $scope.asignarEmpresas = asignarEmpresas;
        init();
        getRoles();
        function listarUsuarios()
        {
            var usr = {idUsuario: '', token: ''};
            usr.idUsuario = sessionService.get("idUsuario");
            usr.token = sessionService.get("token");
            usuarioService.getUsuarios(usr).then(function (data) {
                if (data.colUsu == null) {
                    msgServices.darMensaje(data);
                } else {
                    $scope.myData = data.colUsu;
                    $scope.gridApi.saveState.restore($scope, $scope.myData);
                }

            });
        }
        ;

        $scope.showMe = function (arr) {
            alert(arr);
        };

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
                {field: 'Borrar', width: '8%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-trash btn-danger" ng-click="grid.appScope.borrarUsuario(row)"></button></center>'},
                {field: 'Editar', width: '8%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-pencil btn-success"  ng-click="grid.appScope.habilitarModificacion(row)"></button></center>'},
                {field: 'Empresas', width: '9%', enableCellEdit: false, enableFiltering: false, cellTemplate: '<center><button class="btn btn-sm glyphicon glyphicon-briefcase btn-primary"  ng-click="grid.appScope.asignarEmpresas(row.entity)"></button></center>'},
                {field: 'idUsuario', width: '5%', enableCellEdit: false, visible: false, displayName: 'id'},
                {field: 'nombre', width: '20%'},
                {field: 'apellido', width: '20%'},
                {field: 'nickname', width: '15%', displayName: 'Nombre Usuario'},
                {field: 'correo', width: '20%'},
                {field: 'idRol', width: '5%', visible: false},
                {field: 'nombreRol', width: '15%'},
            ],
            enableGridMenu: true,
            enableSelectAll: true,
            exporterCsvFilename: 'usuarios.csv',
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
            exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
            enableRowSelection: true
        };

        function asignarEmpresas(row)
        {
            sessionService.set("idUsuario_empresas", row.idUsuario);
            $location.path("/empresausuario");
        }

        function borrarUsuario(row)
        {
            swal({
                title: "Confirmar Operación",
                text: "¿Seguro que desea eliminar el usuario?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                closeOnConfirm: true
            },
                    function () {
                        var usr = {idUsuario: '', token: ''};
                        usr.idUsuario = row.entity.idUsuario;
                        usr.token = sessionService.get("token");
                        usuarioService.borrarUsuario(usr).then(function (data) {
                            msgServices.darMensaje(data);
                            if (data.tipo == 'success')
                            {
                                $scope.listarUsuarios();

                            }
                        });
                    });
        }
        ;

        $scope.reset = function () {
            $scope.formUsuario = {};
            $("#userform div").each(function () {
                $(this).removeClass('has-error');
            });

        };

        function getRoles()
        {
            listItemService.getListItemRoles().then(function (data) {
                $scope.roles = data;
            });
        }

        function modificarUsuario()
        {
            $scope.formUsuario.mode = "U";
            $scope.formUsuario.token = sessionService.get("token");

            usuarioService.modificarUsuario($scope.formUsuario).then(function (data) {
                msgServices.darMensaje(data);
                if (data.tipo == 'success')
                {
                    $scope.listarUsuarios();
                }
            });
        }


        function habilitarModificacion(row)
        {
            $('#alta').hide();
            $('#edit').show();
            $scope.formUsuario.nombre = row.entity.nombre;
            $scope.formUsuario.apellido = row.entity.apellido;
            $scope.formUsuario.nickname = row.entity.nickname;
            $scope.formUsuario.correo = row.entity.correo;
            $scope.formUsuario.idRol = row.entity.idRol;
            $('#txtPassword').prop('disabled', true);
            $('#repassword').prop('disabled', true);
            $scope.formUsuario.idUsuario = row.entity.idUsuario;
            window.scrollTo(0, 0);
        }

        function cancelarModificacion()
        {
            $scope.formUsuario = {};
            $('#edit').hide();
            $('#alta').show();
            $('#txtPassword').prop('disabled', false);
            $('#repassword').prop('disabled', false);
        }
        ;

        function init()
        {
            $('#txtPassword').prop('disabled', false);
            $('#repassword').prop('disabled', false);
            $('#edit').hide();
            $('#alta').show();
        }
        ;

        function agregarUsuario()
        {
            var user = angular.copy($scope.formUsuario);
            user.password = md5.createHash(user.password);
            user.ritpassword = md5.createHash(user.ritpassword);

            var token = sessionService.get("token");
            user.token = token;
            user.mode = "A";
            usuarioService.altaUsuario(user).then(function (data) {
                msgServices.darMensaje(data);
                if (data.tipo == "success")
                {
                    $scope.listarUsuarios();
                    $scope.reset();
                }
            });
        }
    }]);
