"use strict";
angular
    .module('leagueOfPhotos')
    .factory('usersService', usersService);

usersService.$inject = ['Restangular'];

function usersService(Restangular) {
    var usersService = Restangular.service('users');
    var usersObject = Restangular.all('users');
    var groupsObject = Restangular.all('groups');

    var service = {
        create: create,
        getAcceptedUsersByGroupId: getAcceptedUsersByGroupId,
        getRequestersByGroupId: getRequestersByGroupId
    };
    
    return service;
    
    function create(signupForm) {
        return usersService
            .post(signupForm)
            .then(
                function (response) {
                    return true;
                },
                function (errors) {
                    return false;
                }
            );
    }

    function dummyErrorsHandler(errors) {
    }

    function getAcceptedUsersByGroupId(groupId) {
        return groupsObject
            .one(groupId)
            .customGETLIST('users')
            .then(getUsersComplete, dummyErrorsHandler);
    }
    
    function getRequestersByGroupId(groupId) {
        return groupsObject
            .one(groupId)
            .customGETLIST('requesters')
            .then(getUsersComplete, dummyErrorsHandler);
    }

    function getUsersComplete(response) {
        var users = [];
        _.forEach(response, function (val) {
            users.push(val.plain());
        });
        return users;
    }
}