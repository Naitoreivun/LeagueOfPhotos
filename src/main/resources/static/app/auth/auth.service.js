"use strict";
angular
    .module('leagueOfPhotos')
    .factory('auth', auth);

auth.$inject = ['AuthRestangular', 'Restangular', '$q', 'jwtHelper', 'store', 'authConst'];

function auth(AuthRestangular, Restangular, $q, jwtHelper, store, authConst) {
    var auth = AuthRestangular.one('/');

    return {
        login: function (loginForm) {
            var defer = $q.defer();

            auth.customPOST(loginForm, 'login')
                .then(
                    function (response) {
                        var token = response.token;
                        store.set(authConst.TOKEN, token);
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
            store.remove(authConst.TOKEN);
            Restangular.setDefaultHeaders({Authorization: ''});
            //todo remove token from server
        },
        isLoggedIn: function () {
            var token = store.get(authConst.TOKEN);
            console.log('token: ', token);
            if(token) {
                console.log(jwtHelper.decodeToken(token));
                console.log(jwtHelper.getTokenExpirationDate(token));
                console.log(jwtHelper.isTokenExpired(token));
            }
            return token !== null;
        }
    };
}
