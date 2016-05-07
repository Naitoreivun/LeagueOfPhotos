"use strict";
angular
    .module('leagueOfPhotos')
    .config(restConfig);

restConfig.$inject = ['RestangularProvider', '$httpProvider'];

function restConfig(RestangularProvider) {
    RestangularProvider.setBaseUrl('api');
    // RestangularProvider.setFullResponse(true);
}