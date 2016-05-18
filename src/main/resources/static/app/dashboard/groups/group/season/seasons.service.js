"use strict";
angular
    .module('leagueOfPhotos')
    .factory('seasonsService', seasonsService);

seasonsService.$inject = ['Restangular', 'groupsService'];

function seasonsService(Restangular, groupsService) {

    var service = {
        add: add
    };

    return service;

    function add(groupId, newSeason) {
        var seasons = groupsService.getElementById(groupId).all('seasons');
        console.log(seasons.post(newSeason));
    }

    function dummyErrorsHandler(errors) {
        console.log('SEASONS ERRORS:', errors);
    }
}