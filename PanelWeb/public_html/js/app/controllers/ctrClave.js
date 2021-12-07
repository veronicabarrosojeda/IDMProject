PanelWeb.controller("ctrClave", ['$scope', '$http', '$q', '$location', 'usuarioService', 'listItemService', 'msgServices', 'uiGridValidateService', 'sessionService', function ($scope, $http, $q, $location, usuarioService, listItemService, msgServices, uiGridValidateService, sessionService) {

        function verificarPermiso()
        {
            var arr = JSON.parse(sessionService.get('permisos'));
            if (arr != null)
            {
                var rest = false;
                for (var i = 0; i < arr.length; i++)
                {
                    if (arr[i].idPermiso == 11)
                        rest = true;
                }

                if (!rest)
                {
                    $location.path('inicio');
                }
            }
        }

        verificarPermiso();
        $scope.titulo = "Cambio de contraseña";
        $scope.formUser = {};
        $scope.formPass = {idUsuario: '', passwordActual: '', passwordNueva: '', passwordNuevaReiterada: ''};
        cargarDatos();
        $scope.reset = reset;
        $scope.cargarDatos = cargarDatos;
        $scope.confirmarCambio = confirmarCambio;

        function cargarDatos() {
            $scope.formUser.nombre = sessionService.get("nombre");
            $scope.formUser.apellido = sessionService.get("apellido");
            $scope.formUser.nickname = sessionService.get("nickname");
            $scope.formUser.correo = sessionService.get("correo");
        }

        function reset()
        {
            $scope.formPass = {idUsuario: '', passwordActual: '', passwordNueva: '', passwordNuevaReiterada: ''};
            $("#userForm div").each(function () {
                $(this).removeClass('has-error');
            });
        }

        function confirmarCambio()
        {

            swal({
                title: "Sus datos seran modificados",
                text: "Ingrese su contraseña para la operacion",
                type: "input",
                showCancelButton: true,
                closeOnConfirm: true,
                inputPlaceholder: "Contraseña...Password",
                inputType: "password"
            }, function (inputValue) {
                if (inputValue === false)
                    return false;
                if (inputValue === "") {
                    swal.showInputError("Contraseña requerida!");
                    return false
                }
                var token = sessionService.get("token");
                $scope.formUser.mode = "U";
                $scope.formUser.token = token;
                var cambioDatos = angular.copy($scope.formUser);
                var cambioPass = angular.copy($scope.formPass);
                cambioDatos.idUsuario = sessionService.get("idUsuario");
                cambioDatos.idRol = sessionService.get("rol");

                if (cambioPass.passwordNueva != "" & cambioPass.passwordNuevaReiterada != "") {

                    cambioPass.idUsuario = sessionService.get("idUsuario");
                    cambioPass.passwordActual = inputValue;
                    usuarioService.cambioClave(cambioPass).then(function (data) {
                        msgServices.darMensaje(data);
                        if (data.tipo == 'success')
                        {
                            rest();
                        }
                    });
                }
                usuarioService.modificarUsuario(cambioDatos).then(function (data) {

                    if (data.tipo == 'success')
                    {
                        sessionService.set("nombre", cambioDatos.nombre);
                        sessionService.set("apellido", cambioDatos.apellido);
                        sessionService.set("nickname", cambioDatos.nickname);
                        sessionService.set("correo", cambioDatos.correo);
                    }
                    msgServices.darMensaje(data);
                });

            });
        }
    }]);
