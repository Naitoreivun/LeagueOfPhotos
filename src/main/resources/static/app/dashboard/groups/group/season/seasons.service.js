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
        getById: getById,
        removeSeason: removeSeason,
        updateSeason: updateSeason
    };

    return service;

    function add(newSeason, groupId) {
        return seasonsObject.post(newSeason, {groupId: groupId});
    }

    function dummyErrorsHandler(errors) {
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
    
    function removeSeason(seasonId) {
        return seasonsObject
            .one('', seasonId)
            .remove();
    }
    
    function updateSeason(seasonId, newSeason) {
        return seasonsObject
            .customPUT(newSeason, seasonId);
    }
}