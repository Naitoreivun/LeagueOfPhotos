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
        getById: getById,
        getScoresByContestId: getScoresByContestId
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
            .then(getListComplete, dummyErrorsHandler);
    }
    
    function getListComplete(response) {
        var array = [];
        _.forEach(response, function (val) {
            array.push(val.plain());
        });
        console.log(array);
        return array;
    }
    
    function getScoresByContestId(contestId) {
        return contestsObject
            .one("", contestId)
            .customGETLIST('scores')
            .then(getListComplete, dummyErrorsHandler);
    }
}