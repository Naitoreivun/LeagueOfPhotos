"use strict";
angular
    .module('leagueOfPhotos')
    .factory('usersService', usersService);

usersService.$inject = ['Restangular'];

function usersService(Restangular) {
    return Restangular.service('users');
}