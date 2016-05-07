"use strict";
angular
    .module('leagueOfPhotos')
    .directive('loginForm', loginForm);

function loginForm() {
    var directive = {
        restrict: 'E',
        templateUrl: "app/home/login-form.html",
        scope: {
            formClass: '@',
            btnSize: '@'
        },
        controller: LoginFormController,
        controllerAs: 'vm',
        bindToController: true
    };

    return directive;
}

LoginFormController.$inject = ['authService', '$state', '$rootScope'];

function LoginFormController(authService, $state) {
    var vm = this;

    vm.error = false;
    vm.logIn = logIn;
    vm.loginForm = {};

    activate();

    function activate() {
        clear();
    }

    function clear() {
        vm.loginForm = {
            username: '',
            password: ''
        };
    }

    function logIn() {
        authService
            .login(vm.loginForm)
            .then(function (value) {
                if (value) {
                    vm.error = false;
                    clear();
                    $state.go('dashboard');
                }
                else {
                    vm.error = true;
                }
            });
    }
}