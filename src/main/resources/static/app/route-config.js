"use strict";
angular
    .module('leagueOfPhotos')
    .config(routeConfig);

function routeConfig($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise("/");

    $stateProvider
        .state('home', {
            url: "/",
            templateUrl: "app/home/home.html",
            controller: 'HomeController as vm'
        })
        .state('dashboard', {
            url: "/dashboard",
            templateUrl: "app/dashboard/dashboard.html",
            controller: 'DashboardController as vm'
        });
}