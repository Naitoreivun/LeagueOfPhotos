"use strict";
angular
    .module('leagueOfPhotos')
    .controller('DashboardController', DashboardController);

DashboardController.$inject = ['auth', '$state'];

function DashboardController(auth, $state) {
    var vm = this;

    vm.test = function () {
        auth.isLoggedIn();
    };
    vm.text = 'DASHBOARD';
    vm.logout = logout;
    
    activate();
    
    function activate() {
        if($state.current.name === 'dashboard') {
            $state.go('dashboard.overview');
        }
    }
    
    function logout() {
        auth.logout();
        $state.go('home');
    }
}