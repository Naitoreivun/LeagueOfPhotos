"use strict";
angular
    .module('leagueOfPhotos')
    .factory('authService', authService);

authService.$inject = ['AuthRestangular', 'Restangular', '$q'];

function authService(AuthRestangular, Restangular, $q) {
    var token = null;
    var auth = AuthRestangular.one('/');

    return {
        login: function (loginForm) {
            var defer = $q.defer();

            auth.customPOST(loginForm, 'login')
                .then(
                    function (response) {
                        token = response.token;
                        Restangular.setDefaultHeaders({Authorization: 'Bearer ' + token});
                        defer.resolve(true);
                    },
                    function (errors) {
                        defer.resolve(false);
                    }
                );

            return defer.promise;
        },
        logout: function () {
            token = null;
            Restangular.setDefaultHeaders({Authorization: ''})
        },
        isLoggedIn: function () {
            return token !== null;
        }
    };
}
