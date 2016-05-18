"use strict";
angular
    .module('leagueOfPhotos')
    .factory('groupsService', groupsService);

groupsService.$inject = ['Restangular'];

function groupsService(Restangular) {
    var groupsService = Restangular.service('groups');

    var service = {
        getAll: getAll,
        getById: getById,
        getElementById: getElementById
    };

    return service;

    function getAll() {
        return groupsService.getList()
            .then(getAllComplete, dummyErrorsHandler);
    }

    function getAllComplete(response) {
        var groups = [];
        _.forEach(response, function (val) {
            groups.push(parseGroup(val));
        });
        return groups;
    }

    function getById(id) {
        return Restangular
            .one('groups', id)
            .get()
            .then(
                function (response) {
                    return parseGroup(response);
                },
                dummyErrorsHandler
            );
    }

    function getElementById(id) {
        return Restangular.one('groups', id);
    }

    function parseGroup(object) {
        var group = {
            id: object.id,
            name: object.name,
            description: object.description,
            creationDate: new Date(object.creationDate),
            type: object.groupType.type
        };
        return group;
    }
    
    function dummyErrorsHandler(errors) {
        console.log('ERRORS:', errors);
    }
}