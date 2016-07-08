"use strict";
angular
    .module('leagueOfPhotos')
    .factory('auth', auth);

auth.$inject = ['AuthRestangular', 'Restangular', 'jwtHelper', 'store', 'authConst', '$q'];

function auth(AuthRestangular, Restangular, jwtHelper, store, authConst, $q) {
    var auth = AuthRestangular.one('/');

    return {
        getProfile: getProfile,
        login: login,
        logout: logout
    };

    function login(loginForm) {
        return auth.customPOST(loginForm, 'login')
            .then(
                function (response) {
                    var token = response.token;
                    store.set(authConst.TOKEN, token);
                    Restangular.setDefaultHeaders({Authorization: 'Bearer ' + token});
                }
            );
    }

    function getProfile() {
        var deffered = $q.defer();
        var profile = {
            name: 'anonymous',
            roles: [],
            anonymous: true
        };

        var token = store.get(authConst.TOKEN);
        if (token && !jwtHelper.isTokenExpired(token)) {
            token = jwtHelper.decodeToken(token);
            profile = {
                name: token.sub,
                roles: token.roles,
                anonymous: false
            }
        }
        deffered.resolve(profile);
        return deffered.promise;
    }

    function logout() {
        store.remove(authConst.TOKEN);
        Restangular.setDefaultHeaders({Authorization: ''});
        //todo remove token from server
    }

}
