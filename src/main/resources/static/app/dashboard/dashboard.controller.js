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
    vm.getActiveClass = getActiveClass;
    
    activate();
    
    function activate() {
        if($state.current.name === 'dashboard') {
            $state.go('dashboard.overview');
        }
    }
    
    function getActiveClass(stateName) {
        if($state.current.name === stateName) {
            return 'active';
        }
        return '';
    }
    
    function logout() {
        auth.logout();
        $state.go('home');
    }
}