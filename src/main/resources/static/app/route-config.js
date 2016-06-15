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
            controller: 'HomeController as vm',
            resolve: {
                accessStatus: ["access", function (access) {
                    return access.isAnonymous();
                }],
                userProfile: "userProfile"
            }
        })
        .state('dashboard', {
            url: "/dashboard",
            templateUrl: "app/dashboard/dashboard.html",
            controller: 'DashboardController as vm',
            resolve: {
                accessStatus: ["access", function (access) {
                        return access.isAuthenticated();
                    }],
                userProfile: "userProfile"
            }
        })
        .state('dashboard.forbidden', {
            url: "/forbidden",
            templateUrl: "app/dashboard/forbidden/forbidden.html"
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
        })
        .state('dashboard.season', {
            url: "/groups/{groupId:[0-9]+}/seasons/{seasonId:[0-9]+}",
            templateUrl: "app/dashboard/groups/group/season/season.html",
            controller: 'SeasonController as season'
        })
        .state('dashboard.contest', {
            url: "/groups/{groupId:[0-9]+}/seasons/{seasonId:[0-9]+}/contests/{contestId:[0-9]+}",
            templateUrl: "app/dashboard/groups/group/season/contest/contest.html",
            controller: 'ContestController as contest'
        });
}