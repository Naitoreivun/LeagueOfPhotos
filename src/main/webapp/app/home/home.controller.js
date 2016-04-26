"use strict";
angular
    .module('leagueOfPhotos')
    .controller('HomeController', HomeController);

function HomeController() {
    var vm = this;

    vm.afterSignUp = false;
}