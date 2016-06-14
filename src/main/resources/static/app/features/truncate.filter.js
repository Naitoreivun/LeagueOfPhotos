angular
    .module('leagueOfPhotos')
    .filter('truncate', truncate);

function truncate() {
    return function (input, limit) {
        return input.length > limit ? input.substr(0, limit) + '...' : input;
    }
}