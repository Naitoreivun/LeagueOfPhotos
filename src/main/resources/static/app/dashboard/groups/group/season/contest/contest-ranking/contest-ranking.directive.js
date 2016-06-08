"use strict";
angular
    .module('leagueOfPhotos')
    .directive('lopContestRanking', lopContestRanking);

function lopContestRanking() {
    var directive = {
        restrict: 'E',
        templateUrl: "app/dashboard/groups/group/season/contest/contest-ranking/contest-ranking.html",
        scope: {
            contestDetails: '='
        },
        controller: ContestRankingController,
        controllerAs: 'ranking',
        bindToController: true
    };

    return directive;
}

ContestRankingController.$inject = ['contestsService'];

function ContestRankingController(contestsService) {
    var ranking = this; // voting phase

    ranking.collapse = collapse;
    ranking.getPlaceClass = getPlaceClass;
    ranking.podiumClassMap = {
        1: "label-first-place",
        2: "label-second-place",
        3: "label-third-place",
        0: "label-black"
    };
    ranking.getScores = getScores;
    ranking.scores = {
        array: [],
        isCollapsed: false
    };
    
    activate();

    function activate() {
        getScores();
    }

    function collapse(object) {
        object.isCollapsed = !object.isCollapsed;
    }

    function getPlaceClass(index) {
        if(index <= 3) {
            return ranking.podiumClassMap[index];
        }
        return ranking.podiumClassMap[0];
    }
    
    function getScores() {
        ranking.scores.array = [];
        contestsService
            .getScoresByContestId(ranking.contestDetails.id)
            .then(
                function (data) {
                    ranking.scores.array = data;
                }
            );
    }
}