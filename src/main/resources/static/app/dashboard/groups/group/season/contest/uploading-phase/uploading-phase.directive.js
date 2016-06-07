"use strict";
angular
    .module('leagueOfPhotos')
    .directive('lopUploadingPhase', lopUploadingPhase);

function lopUploadingPhase() {
    var directive = {
        restrict: 'E',
        templateUrl: "app/dashboard/groups/group/season/contest/uploading-phase/uploading-phase.html",
        scope: false
    };

    return directive;
}