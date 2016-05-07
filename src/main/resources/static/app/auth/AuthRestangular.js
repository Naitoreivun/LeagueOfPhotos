"use strict";
angular
    .module('leagueOfPhotos')
    .factory('AuthRestangular', AuthRestangular);

AuthRestangular.$inject = ['Restangular'];

function AuthRestangular(Restangular) {
    return Restangular.withConfig(function (RestangularConfigurer) {
        RestangularConfigurer.setBaseUrl('');
    });
}