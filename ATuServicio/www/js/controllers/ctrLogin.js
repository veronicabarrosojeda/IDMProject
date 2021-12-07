AtuServicio.controller('ctrLogin', function ($scope, $ionicPopup, $location, msgService, loginService, sessionService) {
//    if (sessionService.get('idUsuario') != null || sessionService.get('idUsuario') != "")
//    {
//        $location.path('/solicitud');
//    }
    $scope.usuario = {nickname: '', password: '', rename: ''};
    $scope.recuperar = {nickname: '', correo: ''};
    $scope.logIn = logIn;
    $scope.logOut = logOut;
    $scope.showAlert = showAlert;
    $scope.verRecuPass = verRecuPass;
    $scope.recuperarPass = recuperarPass;
    $scope.volverLogin = volverLogin;

    volverLogin();
    function logIn()
    {
        if ($scope.usuario.nickname === "") {
            $scope.showAlert("Usuario incompleto");
        } else {
            if ($scope.usuario.password === "") {
                $scope.showAlert("Clave incompleta");
            } else {
                var user = {};
                angular.copy($scope.usuario, user);
                loginService.logIn(user, $scope).then(function (data) {
                    if (data.msg) {
                        //msgService
                        $scope.msgResult = data.msg;
                        $scope.showAlert($scope.msgResult);
                    }
                });
            }
        }

    }
    function logOut()
    {
        loginService.logOut($scope.usuario);
    }

    function showAlert(msgResult) {
        var alertPopup = $ionicPopup.alert({
            title: 'Login',
            template: msgResult
        });
    }
    function recuperarPass() {
        $scope.usuario = {};
        var userP = {};
        var men = {};
        if ($scope.recuperar.nickname === "") {
            $scope.showAlert("Usuario incompleto");
        } else {
            if ($scope.recuperar.correo === "") {
                $scope.showAlert("Clave incompleta");
            } else {
                angular.copy($scope.recuperar, userP);
                loginService.recuperarPass(userP).then(function (data) {
                    if (data.msg) {
                        $scope.recuperar = {nickname: '', correo: ''};
                        $scope.msgResult = data.msg;
                        $scope.showAlert($scope.msgResult);
                        if (data.tipo === "success")
                            volverLogin();
                    }
                });
            }
        }
    }
    function verRecuPass() {
        $scope.tituloLogin = "Recuperar Contraseña";
        $scope.verRecuperarPass = true;
        $scope.verLogin = false;

    }
    function volverLogin() {
        $scope.tituloLogin = "Complete el formulario";
        $scope.verRecuperarPass = false;
        $scope.verLogin = true;

    }

    ;
});
