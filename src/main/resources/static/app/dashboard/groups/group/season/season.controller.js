"use strict";
angular
    .module('leagueOfPhotos')
    .controller('SeasonController', SeasonController);

SeasonController.$inject = ['$stateParams', '$uibModal', 'seasonsService', 'contestsService'];

function SeasonController($stateParams, $uibModal, seasonsService, contestsService) {
    var season = this;


    season.collapse = collapse;
    season.contests = {
        array: [],
        isCollapsed: false
    };
    season.createContest = createContest;
    season.details = {};
    season.getDetails = getDetails;
    season.getContests = getContests;
    season.groupId = $stateParams.groupId;
    season.id = $stateParams.seasonId;

    activate();

    function activate() {
        getDetails();
        getContests();
    }

    function collapse(object) {
        object.isCollapsed = !object.isCollapsed;
    }

    function createContest() {
        var newSeasonModal = $uibModal.open({
            templateUrl: "app/dashboard/groups/group/season/contest/new-contest.html",
            controller: NewContestController,
            controllerAs: 'contest'
        });

        newSeasonModal.result.then(function (newContest) {
            newContest.seasonId = season.id;
            contestsService.add(newContest);
        });
    }

    function getDetails() {
        season.details = {};
        seasonsService
            .getById(season.id)
            .then(
                function (data) {
                    season.details = data;
                },
                function (errors) {
                    console.log('ERRORS:', errors);
                }
            );
    }

    function getContests() {
        season.contests.array = [];
        contestsService
            .getBySeasonId($stateParams.seasonId) //todo zobacz czy da sie season.id
            .then(
                function (data) {
                    season.contests.array = data;
                });
    }
}