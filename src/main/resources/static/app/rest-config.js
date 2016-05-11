"use strict";
angular
    .module('leagueOfPhotos')
    .config(restConfig);

restConfig.$inject = ['RestangularProvider'];

function restConfig(RestangularProvider) {
    RestangularProvider.setBaseUrl('api');
    // RestangularProvider.setFullResponse(true);
}