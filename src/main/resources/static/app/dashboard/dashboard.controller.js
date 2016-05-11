"use strict";
angular
    .module('leagueOfPhotos')
    .controller('DashboardController', DashboardController);

DashboardController.$inject = ['groupsService', 'auth', '$state'];

function DashboardController(groupsService, auth, $state) {
    var vm = this;

    vm.test = function () {
        auth.isLoggedIn();
    };
    vm.text = 'DASHBOARD';
    vm.groups = [];
    vm.getGroups = getGroups;
    vm.logout = logout;
    
    activate();
    
    function activate() {
        getGroups();
    }
    
    function getGroups() {
        groupsService
            .getList()
            .then(
                function (response) {
                    vm.groups = [];
                    _.forEach(response, function (val) {
                        vm.groups.push({
                            id: val.id,
                            name: val.name,
                            description: val.description,
                            creationDate: new Date(val.creationDate),
                            type: val.groupType.type
                        });
                    });

                    console.log(vm.groups);
                },
                function (errors) {
                    console.log('ERRORS:');
                    console.log(errors);
                }
            );
    }

    function logout() {
        auth.logout();
        $state.go('home');
    }
}