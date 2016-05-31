"use strict";
angular
    .module('leagueOfPhotos')
    .factory('seasonsService', seasonsService);

seasonsService.$inject = ['Restangular'];

function seasonsService(Restangular) {
    var seasonsService = Restangular.service('seasons');
    var seasonsObject = Restangular.all('seasons');

    var service = {
        add: add,
        getByGroupId: getByGroupId,
        getById: getById
    };

    return service;

    function add(newSeason) {
        seasonsObject.post(newSeason);
    }

    function dummyErrorsHandler(errors) {
        console.log('SEASONS ERRORS:', errors);
    }

    function getByGroupId(groupId) {
        return seasonsObject
            .one('group', groupId)
            .getList()
            .then(getByGroupIdComplete, dummyErrorsHandler);
    }

    function getByGroupIdComplete(response) {
        var seasons = [];
        _.forEach(response, function (val) {
            seasons.push(val.plain());
        });
        console.log(seasons);
        return seasons;
    }

    function getById(seasonId) {
        return seasonsObject
            .get(seasonId)
            .then(getByIdComplete, dummyErrorsHandler);
    }

    function getByIdComplete(response) {
        return response.plain();
    }
}