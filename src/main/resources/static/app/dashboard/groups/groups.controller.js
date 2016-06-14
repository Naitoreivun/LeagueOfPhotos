"use strict";
angular
    .module('leagueOfPhotos')
    .controller('GroupsController', GroupsController);

GroupsController.$inject = ['groupsService', '$uibModal'];

function GroupsController(groupsService, $uibModal) {
    var groups = this;

    groups.otherGroups = {
        array: [],
        isCollapsed: false
    };
    groups.collapse = collapse;
    groups.createGroup = createGroup;
    groups.getOther = getOther;
    groups.getTypeClass = getTypeClass;
    groups.getUserGroups = getUserGroups;
    groups.join = join;
    groups.leave = leave;
    groups.typeClassMap = {
        'PRIVATE': "label-danger",
        'PUBLIC': "label-success"
    };
    groups.userGroups = {
        array: [],
        isCollapsed: false
    };

    activate();

    function activate() {
        getOther();
        getUserGroups();
    }

    function collapse(object) {
        object.isCollapsed = !object.isCollapsed;
    }

    function createGroup() {
        var newGroupModal = $uibModal.open({
            templateUrl: "app/dashboard/groups/group/new-group.html",
            controller: NewGroupController,
            controllerAs: 'group',
            resolve: {
                titlePrefix: function () {
                    return 'Create new';
                },
                oldGroup: null
            }
        });

        newGroupModal.result.then(function (newGroup) {
            groupsService.add(newGroup).then(activate);
        });
    }

    function getOther() {
        groups.otherGroups.array = [];
        groupsService
            .getGroupsWithoutCurrentUser()
            .then(
                function (data) {
                    groups.otherGroups.array = data;
                });
    }

    function getTypeClass(type) {
        return groups.typeClassMap[type];
    }

    function getUserGroups() {
        groups.userGroups.array = [];
        groupsService
            .getCurrentUserGroups()
            .then(
                function (data) {
                    groups.userGroups.array = data;
                });
    }

    function join(groupId) {
        groupsService
            .addCurrentUserToGroup(groupId)
            .then(activate);
    }

    function leave(groupId) {
        groupsService
            .removeCurrentUserFromGroup(groupId)
            .then(activate);
    }
}