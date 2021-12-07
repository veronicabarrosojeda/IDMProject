'use strict';
PanelWeb.factory('listItemService', ['$http', '$q', '$window', function ($http, $q, $window) {
        return{
            getListItemRoles: getListItemRoles,
            getListItemSubAreas: getListItemSubAreas,
            getListItemMunicipios: getListItemMunicipios,
            getListItemBarrios: getListItemBarrios,
            getDetTipoById: getDetTipoById,
            getListItemCategorias: getListItemCategorias,
            getTipoServicio: getTipoServicio,
            getEmpresas: getEmpresas,
            getListItemAreas: getListItemAreas
        };
        function getListItemRoles() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsListItem/getRoles')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function getListItemMunicipios() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsListItem/getMunicipios')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function getListItemSubAreas() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsListItem/getSubAreas')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function getListItemBarrios(id) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsListItem/getBarriosPorMunicipio', id)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function getDetTipoById(id) {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsListItem/getDetTipoById', id)
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function getListItemCategorias() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsListItem/getCategorias')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });

            return promise;
        }

        function getEmpresas() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsListItem/getEmpresas')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function getTipoServicio() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsListItem/getTipoServicio')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }

        function getListItemAreas() {
            var defered = $q.defer();
            var promise = defered.promise;
            $http.post($window.ip + '/ServerApp/ws/wsListItem/getAreas')
                    .success(function (data) {
                        defered.resolve(data);
                    })
                    .error(function (error) {
                    });
            return promise;
        }}
]);
