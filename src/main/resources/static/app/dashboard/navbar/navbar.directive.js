"use strict";
angular
    .module('leagueOfPhotos')
    .directive('lopNavbar', lopNavbar);

function lopNavbar() {
    var directive = {
        restrict: 'E',
        templateUrl: "app/dashboard/navbar/navbar.html",
        scope: false
    };

    return directive;
}