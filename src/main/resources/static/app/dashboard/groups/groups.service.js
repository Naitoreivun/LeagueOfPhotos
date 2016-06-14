"use strict";
angular
    .module('leagueOfPhotos')
    .factory('groupsService', groupsService);

groupsService.$inject = ['Restangular'];

function groupsService(Restangular) {
    var groupsService = Restangular.service('groups');
    var groupsObject = Restangular.all('groups');

    var service = {
        add: add,
        addCurrentUserToGroup: addCurrentUserToGroup,
        getAll: getAll,
        getById: getById,
        getCurrentUserGroups: getCurrentUserGroups,
        getGroupsWithoutCurrentUser: getGroupsWithoutCurrentUser,
        removeCurrentUserFromGroup: removeCurrentUserFromGroup,
        updateGroup: updateGroup
    };

    return service;

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

    function removeCurrentUserFromGroup(groupId) {
        return groupsObject
            .one('', groupId)
            .one('users', 'current')
            .remove();
    }
    
    function updateGroup(groupId, newGroup) {
        return groupsObject
            .customPUT(newGroup, groupId);
    }
}