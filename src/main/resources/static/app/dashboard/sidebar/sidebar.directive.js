"use strict";
angular
    .module('leagueOfPhotos')
    .directive('lopSidebar', lopSidebar);

function lopSidebar() {
    var directive = {
        restrict: 'E',
        templateUrl: "app/dashboard/sidebar/sidebar.html",
        scope: false
    };

    return directive;
}