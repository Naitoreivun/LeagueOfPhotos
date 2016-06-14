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
    group.editGroup = editGroup;
    group.getMemberStatusClass = getMemberStatusClass;
    group.getSeasons = getSeasons;
    group.getSeasonStatusClass = getSeasonStatusClass;
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
    group.seasonStatusClassMap = {
        'CLOSED': "label-danger",
        'OPEN': "label-success",
        'AVAILABLE_SOON': "label-warning"
    };
    group.users = {
        array: [],
        isCollapsed: true
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
            controllerAs: 'season',
            resolve: {
                titlePrefix: function () {
                    return 'Create new';
                },
                oldSeason: null
            }
        });

        newSeasonModal.result.then(function (newSeason) {
            newSeason.groupId = group.details.id;
            seasonsService.add(newSeason).then(getSeasons);
        });
    }

    function editGroup() {
        var newGroupModal = $uibModal.open({
            templateUrl: "app/dashboard/groups/group/new-group.html",
            controller: NewGroupController,
            controllerAs: 'group',
            resolve: {
                titlePrefix: function () {
                    return 'Edit';
                },
                oldGroup: group.details
            }
        });

        newGroupModal.result.then(function (newGroup) {
            groupsService.updateGroup(group.details.id, newGroup).then(getDetails);
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

    function getSeasonStatusClass(status) {
        return group.seasonStatusClassMap[status];
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