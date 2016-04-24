"use strict";
angular
    .module('leagueOfPhotos')
    .config(routeConfig);

function routeConfig($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise("/home");

    $stateProvider
        .state('home', {
            url: "/home",
            templateUrl: "app/home/home.html",
            controller: 'HomeController as vm'
        });
}