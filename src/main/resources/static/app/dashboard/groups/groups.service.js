"use strict";
angular
    .module('leagueOfPhotos')
    .factory('groupsService', groupsService);

groupsService.$inject = ['Restangular'];

function groupsService(Restangular) {
    var groupsService = Restangular.service('groups');
    var groupsObject = Restangular.all('groups');

    var service = {
        getAll: getAll,
        getById: getById,
        getCurrentUserGroups: getCurrentUserGroups
    };

    return service;

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

    function getGroupsComplete(response) {
        var groups = [];
        _.forEach(response, function (val) {
            groups.push(val.plain());
        });
        return groups;
    }
}