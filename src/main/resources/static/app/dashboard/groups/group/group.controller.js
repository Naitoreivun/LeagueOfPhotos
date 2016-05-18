"use strict";
angular
    .module('leagueOfPhotos')
    .controller('GroupController', GroupController);

GroupController.$inject = ['groupsService', '$stateParams', '$uibModal', 'seasonsService'];

function GroupController(groupsService, $stateParams, $uibModal, seasonsService) {
    var group = this;

    group.createSeason = createSeason;
    group.details = {};
    group.getDetails = getDetails;
    group.seasons = [];

    activate();

    function activate() {
        getDetails();
    }

    function createSeason() {
        var newSeasonModal = $uibModal.open({
            templateUrl: "app/dashboard/groups/group/season/new-season.html",
            controller: NewSeasonController,
            controllerAs: 'season'
        });

        newSeasonModal.result.then(function (newSeason) {
            console.log(newSeason);
            seasonsService.add(group.details.id, newSeason);
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
}