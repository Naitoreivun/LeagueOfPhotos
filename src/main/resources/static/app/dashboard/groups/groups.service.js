"use strict";
angular
    .module('leagueOfPhotos')
    .factory('groupsService', groupsService);

groupsService.$inject = ['Restangular'];

function groupsService(Restangular) {
    return Restangular.service('groups');
}