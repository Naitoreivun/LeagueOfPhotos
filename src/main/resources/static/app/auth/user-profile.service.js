"use strict";
angular
    .module('leagueOfPhotos')
    .factory('userProfile', userProfile);

userProfile.$inject = ['auth'];

function userProfile(auth) {
    var userProfile = {};

    var fetchUserProfile = function () {
        return auth
            .getProfile()
            .then(
                function (profileData) {
                    for (var prop in userProfile) {
                        if (userProfile.hasOwnProperty(prop)) {
                            delete userProfile[prop];
                        }
                    }

                    return angular.extend(userProfile, profileData, {

                        refresh: fetchUserProfile,

                        hasRole: function (role) {
                            return userProfile.roles.indexOf(role) >= 0;
                        },

                        isAnonymous: function () {
                            return userProfile.anonymous;
                        },

                        isAuthenticated: function () {
                            return !userProfile.anonymous;
                        }

                    });

                });
    };


    return fetchUserProfile();
}
