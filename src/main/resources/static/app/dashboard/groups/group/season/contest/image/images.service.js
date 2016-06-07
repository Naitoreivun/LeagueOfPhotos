"use strict";
angular
    .module('leagueOfPhotos')
    .factory('imagesService', imagesService);

imagesService.$inject = ['Restangular'];

function imagesService(Restangular) {
    var imagesService = Restangular.service('images');
    var imagesObject = Restangular.all('images');

    var service = {
        getByContestIdAndCurrentUser: getByContestIdAndCurrentUser
    };

    return service;

    function dummyErrorsHandler(errors) {
        console.log('IMAGES ERRORS:', errors);
        return null;
    }

    function getByContestIdAndCurrentUser(contestId) {
        return imagesObject
            .one('contest', contestId)
            .customGET('currentUser')
            .then(getByContestIdAndCurrentUserComplete, dummyErrorsHandler);
    }

    function getByContestIdAndCurrentUserComplete(response) {
        return response.plain();
    }
}