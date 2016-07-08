"use strict";
angular
    .module('leagueOfPhotos')
    .run(runBlock);

runBlock.$inject = ['$rootScope', 'store', 'Restangular', 'authConst', 'access', '$state'];

function runBlock($rootScope, store, Restangular, authConst, access, $state) {
    // This events gets triggered on refresh or URL change
    $rootScope.$on('$locationChangeStart', locationChangeStartHandler);

    function locationChangeStartHandler() {
        var token = store.get(authConst.TOKEN);
        if (token) {
            Restangular.setDefaultHeaders({Authorization: 'Bearer ' + token});
        }
    }

    $rootScope.$on("$stateChangeError", stateChangeErrorHandler);

    function stateChangeErrorHandler(event, toState, toParams, fromState, fromParams, error) {
        if (error == access.UNAUTHORIZED) {
            $state.go("home");
        } else if (error == access.FORBIDDEN) {
            if (toState.name == 'home') {
                $state.go("dashboard.overview");
            }
            else {
                $state.go("dashboard.forbidden");
            }
        }
    }
}