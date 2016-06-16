"use strict";
angular
    .module('leagueOfPhotos')
    .controller('SeasonController', SeasonController);

SeasonController.$inject = ['$stateParams', '$uibModal', 'seasonsService',
    'contestsService', 'groupsService', '$state', 'modalService'];

function SeasonController($stateParams, $uibModal, seasonsService, contestsService,
                          groupsService, $state, modalService) {
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
    season.isCurrentUserGroupModerator = false;
    season.removeSeason = removeSeason;
    season.removeContest = removeContest;

    activate();

    function activate() {
        getDetails();
        getContests();
        isCurrentUserGroupModerator();
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
            contestsService.add(newContest, season.id).then(getContests);
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
            .getBySeasonId(season.id)
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

    function isCurrentUserGroupModerator() {
        groupsService
            .isCurrentUserGroupModerator(season.groupId)
            .then(
                function (data) {
                    season.isCurrentUserGroupModerator = data;
                }
            );
    }

    function removeSeason() {
        var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Season',
            headerText: 'Delete ' + season.details.name + '?',
            bodyText: 'Are you sure you want to delete this season?'
        };

        modalService
            .showModal({}, modalOptions)
            .then(
                function (result) {
                    seasonsService
                        .removeSeason(season.id)
                        .then($state.go('dashboard.groups'));
                });
    }

    function removeContest(index) {
        var contest = season.contests.array[index];

        var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Contest',
            headerText: 'Delete ' + contest.name + '?',
            bodyText: 'Are you sure you want to delete this contest?'
        };

        modalService
            .showModal({}, modalOptions)
            .then(
                function (result) {
                    contestsService
                        .removeContest(contest.id)
                        .then(season.contests.array.splice(index, 1));
                });
    }
}