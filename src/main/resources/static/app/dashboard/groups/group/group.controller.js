"use strict";
angular
    .module('leagueOfPhotos')
    .controller('GroupController', GroupController);

GroupController.$inject = ['groupsService', '$stateParams', '$uibModal', 'seasonsService', 'usersService'];

function GroupController(groupsService, $stateParams, $uibModal, seasonsService, usersService) {
    var group = this;

    group.collapse = collapse;
    group.createSeason = createSeason;
    group.details = {};
    group.getDetails = getDetails;
    group.getMemberStatusClass = getMemberStatusClass;
    group.getSeasons = getSeasons;
    group.getUsers = getUsers;
    group.memberStatusClassMap = {
        ADMIN: "label-danger",
        MODERATOR: "label-warning",
        MEMBER: "label-success"
    };
    group.seasons = {
        array: [],
        isCollapsed: false
    };
    group.users = {
        array: [],
        isCollapsed: false
    };

    activate();

    function activate() {
        getDetails();
        getSeasons();
        getUsers();
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

    function getMemberStatusClass(user) {
        return group.memberStatusClassMap[user.memberStatus];
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

    function getUsers() {
        group.users.array = [];
        usersService
            .getAcceptedUsersByGroupId($stateParams.id)
            .then(
                function (data) {
                    group.users.array = data;
                });
    }
}