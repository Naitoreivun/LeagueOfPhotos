"use strict";
angular
    .module('leagueOfPhotos')
    .factory('groupsService', groupsService);

groupsService.$inject = ['Restangular'];

function groupsService(Restangular) {
    var groupsService = Restangular.service('groups');
    var groupsObject = Restangular.all('groups');

    var service = {
        acceptRequester: acceptRequester,
        add: add,
        addCurrentUserToGroup: addCurrentUserToGroup,
        getAll: getAll,
        getById: getById,
        getCurrentUserGroups: getCurrentUserGroups,
        getGroupsWithoutCurrentUser: getGroupsWithoutCurrentUser,
        isCurrentUserGroupAdmin: isCurrentUserGroupAdmin,
        isCurrentUserGroupModerator: isCurrentUserGroupModerator,
        rejectRequester: rejectRequester,
        removeCurrentUserFromGroup: removeCurrentUserFromGroup,
        removeGroup: removeGroup,
        updateGroup: updateGroup
    };

    return service;

    function acceptRequester(groupId, requesterId) {
        return groupsObject
            .one(groupId)
            .one('requesters', requesterId)
            .customPUT({}, 'accept');
    }

    function add(newGroup) {
        return groupsService.post(newGroup);
    }

    function addCurrentUserToGroup(groupId) {
        return groupsObject
            .one('', groupId)
            .one('users', 'current')
            .post();
    }

    function dummyErrorsHandler(errors) {
        console.log('ERRORS:', errors);
    }

    function getAll() {
        return groupsService
            .getList()
            .then(getGroupsComplete, dummyErrorsHandler);
    }

    function getById(id) {
        return groupsObject
            .get(id)
            .then(getByIdComplete, dummyErrorsHandler);
    }

    function getByIdComplete(response) {
        return response.plain();
    }

    function getCurrentUserGroups() {
        return groupsObject
            .one('user', 'current')
            .getList()
            .then(getGroupsComplete, dummyErrorsHandler);
    }

    function getGroupsWithoutCurrentUser() {
        return groupsObject
            .one('withoutuser', 'current')
            .getList()
            .then(getGroupsComplete, dummyErrorsHandler);
    }

    function getGroupsComplete(response) {
        var groups = [];
        _.forEach(response, function (val) {
            groups.push(val.plain());
        });
        return groups;
    }


    function isCurrentUserGroupAdmin(groupId) {
        return groupsObject
            .one('', groupId)
            .one('users', 'current')
            .customGET('status/admin')
            .then(
                function (response) {
                    return response ? true : false;
                },
                function (errors) {
                    return false;
                }
            )
    }

    function isCurrentUserGroupModerator(groupId) {
        return groupsObject
            .one('', groupId)
            .one('users', 'current')
            .customGET('status/moderator')
            .then(
                function (response) {
                    return response ? true : false;
                },
                function (errors) {
                    return false;
                }
            )
    }

    function rejectRequester(groupId, requesterId) {
        return groupsObject
            .one(groupId)
            .one('requesters', requesterId)
            .customPUT({}, 'reject');
    }

    function removeCurrentUserFromGroup(groupId) {
        return groupsObject
            .one('', groupId)
            .one('users', 'current')
            .remove();
    }

    function removeGroup(groupId) {
        return groupsObject
            .one('', groupId)
            .remove();
    }

    function updateGroup(groupId, newGroup) {
        return groupsObject
            .customPUT(newGroup, groupId);
    }
}