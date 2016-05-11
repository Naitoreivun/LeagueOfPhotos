"use strict";
angular
    .module('leagueOfPhotos')
    .run(runBlock);

runBlock.$inject = ['$rootScope', 'store', 'Restangular', 'authConst'];

function runBlock($rootScope, store, Restangular, authConst) {
    // This events gets triggered on refresh or URL change
    $rootScope.$on('$locationChangeStart', function() {
        var token = store.get(authConst.TOKEN);
        if (token) {
            Restangular.setDefaultHeaders({Authorization: 'Bearer ' + token});
        }

    });
}