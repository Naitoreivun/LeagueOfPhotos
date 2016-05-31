"use strict";
angular
    .module('leagueOfPhotos')
    .controller('GroupController', GroupController);

GroupController.$inject = ['groupsService', '$stateParams', '$uibModal', 'seasonsService'];

function GroupController(groupsService, $stateParams, $uibModal, seasonsService) {
    var group = this;

    group.collapse = collapse;
    group.createSeason = createSeason;
    group.details = {};
    group.getDetails = getDetails;
    group.getSeasons = getSeasons;
    group.seasons = {
        array: [],
        isCollapsed: false
    };

    activate();

    function activate() {
        getDetails();
        getSeasons();
    }

    function collapse(object) {
        object.isCollapsed = !object.isCollapsed;
    }

    function createSeason() {
        var newSeasonModal = $uibModal.open({
            templateUrl: "app/dashboard/groups/group/season/new-season.html",
            controller: NewSeasonController,
            controllerAs: 'season'
        });

        newSeasonModal.result.then(function (newSeason) {
            newSeason.groupId = group.details.id;
            seasonsService.add(newSeason);
        });
    }

    function getDetails() {
        group.details = {};
        groupsService
            .getById($stateParams.id)
            .then(
                function (data) {
                    group.details = data;
                },
                function (errors) {
                    console.log('ERRORS:', errors);
                }
            )
    }

    function getSeasons() {
        group.seasons.array = [];
        seasonsService
            .getByGroupId($stateParams.id)
            .then(
                function (data) {
                    group.seasons.array = data;
                });
    }
}