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
    season.contestStatusClassMap = {
        'CLOSED': "label-danger",
        'UPLOADING': "label-success",
        'VOTING': "label-primary",
        'AVAILABLE_SOON': "label-warning"
    };
    season.createContest = createContest;
    season.details = {};
    season.editSeason = editSeason;
    season.editContest = editContest;
    season.getDetails = getDetails;
    season.getContests = getContests;
    season.getContestStatusClass = getContestStatusClass;
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
            controllerAs: 'contest',
            resolve: {
                titlePrefix: function () {
                    return 'Create new';
                },
                oldContest: null
            }
        });

        newSeasonModal.result.then(function (newContest) {
            newContest.seasonId = season.id;
            contestsService.add(newContest).then(getContests);
        });
    }

    function editSeason() {
        var newSeasonModal = $uibModal.open({
            templateUrl: "app/dashboard/groups/group/season/new-season.html",
            controller: NewSeasonController,
            controllerAs: 'season',
            resolve: {
                titlePrefix: function () {
                    return 'Edit';
                },
                oldSeason: season.details
            }
        });

        newSeasonModal.result.then(function (newSeason) {
            seasonsService.updateSeason(season.details.id, newSeason).then(getDetails);
        });
    }

    function editContest(oldContest) {
        var newSeasonModal = $uibModal.open({
            templateUrl: "app/dashboard/groups/group/season/contest/new-contest.html",
            controller: NewContestController,
            controllerAs: 'contest',
            resolve: {
                titlePrefix: function () {
                    return 'Edit';
                },
                oldContest: oldContest
            }
        });

        newSeasonModal.result.then(function (newContest) {
            contestsService.updateContest(oldContest.id, newContest).then(getContests);
        });
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

    function getContestStatusClass(status) {
        return season.contestStatusClassMap[status];
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
}