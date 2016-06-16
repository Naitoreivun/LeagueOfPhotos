"use strict";
angular
    .module('leagueOfPhotos')
    .controller('GroupController', GroupController);

GroupController.$inject = ['groupsService', '$stateParams', '$uibModal',
    'seasonsService', 'usersService', '$state', 'modalService'];

function GroupController(groupsService, $stateParams, $uibModal, seasonsService,
                         usersService, $state, modalService) {
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
    group.id = $stateParams.id;
    group.isCurrentUserGroupAdmin = false;
    group.isCurrentUserGroupModerator = false;
    group.memberStatusClassMap = {
        ADMIN: "label-danger",
        MODERATOR: "label-warning",
        MEMBER: "label-success"
    };
    group.removeGroup = removeGroup;
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
        isCurrentUserGroupAdmin();
        isCurrentUserGroupModerator();
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
            seasonsService.add(newSeason, group.id).then(getSeasons);
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
            groupsService.updateGroup(group.id, newGroup).then(getDetails);
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
            .getByGroupId(group.id)
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
            .getAcceptedUsersByGroupId(group.id)
            .then(
                function (data) {
                    group.users.array = data;
                });
    }

    function isCurrentUserGroupAdmin() {
        groupsService
            .isCurrentUserGroupAdmin(group.id)
            .then(
                function (data) {
                    group.isCurrentUserGroupAdmin = data;
                }
            );
    }

    function isCurrentUserGroupModerator() {
        groupsService
            .isCurrentUserGroupModerator(group.id)
            .then(
                function (data) {
                    group.isCurrentUserGroupModerator = data;
                }
            );
    }

    function removeGroup() {
        var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Group',
            headerText: 'Delete ' + group.details.name + '?',
            bodyText: 'Are you sure you want to delete this group?'
        };

        modalService
            .showModal({}, modalOptions)
            .then(
                function (result) {
                    groupsService
                        .removeGroup(group.id)
                        .then($state.go('dashboard.overview'));
                });
    }
}