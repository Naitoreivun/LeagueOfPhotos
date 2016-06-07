"use strict";
angular
    .module('leagueOfPhotos')
    .directive('lopVotingPhase', lopVotingPhase);

function lopVotingPhase() {
    var directive = {
        restrict: 'E',
        templateUrl: "app/dashboard/groups/group/season/contest/voting-phase/voting-phase.html",
        scope: {
            contestDetails: '='
        },
        controller: VotingPhaseController,
        controllerAs: 'vp',
        bindToController: true
    };

    return directive;
}

VotingPhaseController.$inject = ['imagesService'];

function VotingPhaseController(imagesService) {
    var vp = this; // voting phase
    
    activate();
    
    function activate() {

    }

}