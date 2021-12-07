PanelWeb.controller("ctrLogin", ['$scope', '$window','serviceConfig','loginService', function ($scope, $window,serviceConfig,loginService) {

        $('#msgResult').hide();
        $scope.usuario = {nickname: '', password: ''};
        $scope.logIn = logIn;
        $scope.logOut = logOut;
        $scope.msgResult;
        $scope.classResult;

        serviceConfig.getRutaServer().then(function (data) {
            $window.ip = data;
        });

        function logIn()
        {
            $('#msgResult').hide();
            var user = {};
            angular.copy($scope.usuario, user);
            loginService.logIn(user, $scope).then(function (data) {
                $('#msgResult').removeClass('hidden');
                $('#msgResult').show();
                $scope.msgResult = data.msg;
                $scope.classResult = 'text-' + data.status;
            });
        }

        function logOut()
        {
            loginService.logOut($scope.usuario);
        }


    }]);

