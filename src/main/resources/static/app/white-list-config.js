"use strict";
angular
    .module('leagueOfPhotos')
    .config(whiteListConfig);

whiteListConfig.$inject = ['$compileProvider'];

function whiteListConfig($compileProvider) {
    $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|local|data):/);
}