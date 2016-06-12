"use strict";
angular
    .module('leagueOfPhotos')
    .controller('HomeController', HomeController);

HomeController.$inject = ['$interval'];

function HomeController($interval) {
    var vm = this;

    vm.afterSignUp = false;
    vm.backgrounds = [];
    vm.backgroundId = 0;
    vm.changeBackground = changeBackground;
    vm.intervalPromise = null;
    vm.stop = stop;

    activate();

    function activate() {
        vm.stop();
        vm.backgrounds = ['url(img/background.jpg)', 'url(img/background2.jpg)'];
        vm.intervalPromise = $interval(vm.changeBackground, 30 * 1000);
    }

    function changeBackground() {
        vm.backgroundId++;
        vm.backgroundId = vm.backgroundId % vm.backgrounds.length;
        var home = angular.element('#home')[0];
        if (home) {
            home.style.backgroundImage = vm.backgrounds[vm.backgroundId];
        } else {
            vm.stop();
        }
    }

    function stop() {
        $interval.cancel(vm.intervalPromise);
    }
}