"use strict";
angular
    .module('leagueOfPhotos')
    .factory('access', access);

access.$inject = ['$q', 'userProfile'];

function access($q, userProfile) {
    var access = {
        OK: 200,
        UNAUTHORIZED: 401,
        FORBIDDEN: 403,

        hasRole: hasRole,
        isAnonymous: isAnonymous,
        isAuthenticated: isAuthenticated
    };

    return access;
    
    function hasRole(role) {
        return userProfile
            .then(
                function (up) {
                    if(up.hasRole(role)) {
                        return access.OK;
                    }
                    else if (up.isAnonymous()) {
                        return $q.reject(access.UNAUTHORIZED)
                    }
                    else {
                        return $q.reject(access.FORBIDDEN);
                    }
                }
            )
    }

    function isAnonymous() {
        return userProfile
            .then(
                function (up) {
                    if(up.isAnonymous()) {
                        return access.OK;
                    } else {
                        return $q.reject(access.FORBIDDEN);
                    }
                }
            )
    }

    function isAuthenticated() {
        return userProfile
            .then(
                function (up) {
                    if(up.isAuthenticated()) {
                        return access.OK;
                    }
                    else {
                        return $q.reject(access.UNAUTHORIZED);
                    }
                }
            )
    }
}
