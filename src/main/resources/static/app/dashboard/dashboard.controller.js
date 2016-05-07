"use strict";
angular
    .module('leagueOfPhotos')
    .controller('DashboardController', DashboardController);

function DashboardController() {
    var vm = this;

    vm.text = 'DASHBOARD';
}