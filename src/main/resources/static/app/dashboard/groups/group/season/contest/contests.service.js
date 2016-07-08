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
        getScoresByContestId: getScoresByContestId,
        removeContest: removeContest,
        updateContest: updateContest
    };

    return service;

    function add(newContest, seasonId) {
        return contestsObject.post(newContest, {seasonId: seasonId});
    }

    function dummyErrorsHandler(errors) {
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
        return array;
    }
    
    function getScoresByContestId(contestId) {
        return contestsObject
            .one("", contestId)
            .customGETLIST('scores')
            .then(getListComplete, dummyErrorsHandler);
    }


    function removeContest(contestId) {
        return contestsObject
            .one('', contestId)
            .remove();
    }

    function updateContest(contestId, newContest) {
        return contestsObject
            .customPUT(newContest, contestId);
    }
}