"use strict";
angular
    .module('leagueOfPhotos')
    .controller('DashboardController', DashboardController);

DashboardController.$inject = ['auth', '$state', 'userProfile'];

function DashboardController(auth, $state, userProfile) {
    var vm = this;

    vm.logout = logout;
    vm.getActiveClass = getActiveClass;
    vm.username = userProfile.name;

    activate();

    function activate() {
        if ($state.current.name === 'dashboard') {
            $state.go('dashboard.overview');
        }
    }

    function getActiveClass(stateName) {
        if ($state.current.name === stateName) {
            return 'active';
        }
        return '';
    }

    function logout() {
        auth.logout();
        userProfile
            .refresh()
            .then($state.go('home'));
    }
}