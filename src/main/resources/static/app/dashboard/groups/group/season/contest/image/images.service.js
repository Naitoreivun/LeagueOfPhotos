"use strict";
angular
    .module('leagueOfPhotos')
    .factory('imagesService', imagesService);

imagesService.$inject = ['Restangular'];

function imagesService(Restangular) {
    var imagesService = Restangular.service('images');
    var imagesObject = Restangular.all('images');

    var service = {
        getByContestIdAndCurrentUser: getByContestIdAndCurrentUser,
        getVotableImagesByContestId: getVotableImagesByContestId,
        vote: vote
    };

    return service;

    function dummyErrorsHandler(errors) {
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

    function getVotableImagesByContestId(contestId) {
        return imagesObject
            .one('contest', contestId)
            .customGETLIST('votable')
            .then(getImagesComplete, dummyErrorsHandler);
    }

    function getImagesComplete(response) {
        var images = [];
        _.forEach(response, function (val) {
            images.push(val.plain());
        });
        return images;
    }

    function vote(imageId, rating) {
        var ratedImage = {
            imageId: imageId,
            rating: rating
        };
        imagesObject.customPOST({}, 'vote', ratedImage);
    }

}