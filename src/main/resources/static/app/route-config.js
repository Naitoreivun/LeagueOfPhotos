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
        })
        .state('dashboard.overview', {
            url: "/overview",
            templateUrl: "app/dashboard/overview/overview.html"
            // controller: 'OverviewController as overview'
        })
        .state('dashboard.groups', {
            url: "/groups",
            templateUrl: "app/dashboard/groups/groups.html",
            controller: 'GroupsController as groups'
        })
        .state('dashboard.group', {
            url: "/groups/{id:[0-9]+}",
            templateUrl: "app/dashboard/groups/group/group.html",
            controller: 'GroupController as group'
        });
}