"use strict";
angular
    .module('leagueOfPhotos')
    .factory('contestsService', contestsService);

contestsService.$inject = ['Restangular'];

function contestsService(Restangular) {
    var contestsService = Restangular.service('contests');
    var contestsObject = Restangular.all('contests');

    var service = {
        add: add,
        getBySeasonId: getBySeasonId,
        getById: getById
    };

    return service;

    function add(newContest) {
        contestsObject.post(newContest);
    }

    function dummyErrorsHandler(errors) {
        console.log('SEASONS ERRORS:', errors);
    }

    function getById(contestId) {
        return contestsObject
            .get(contestId)
            .then(getByIdComplete, dummyErrorsHandler);
    }

    function getByIdComplete(response) {
        return response.plain();
    }
    
    function getBySeasonId(seasonId) {
        return contestsObject
            .one('season', seasonId)
            .getList()
            .then(getBySeasonIdComplete, dummyErrorsHandler);
    }
    
    function getBySeasonIdComplete(response) {
        var contests = [];
        _.forEach(response, function (val) {
            contests.push(val.plain());
        });
        console.log(contests);
        return contests;
    }
}