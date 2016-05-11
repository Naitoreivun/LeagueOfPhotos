"use strict";
angular
    .module('leagueOfPhotos')
    .directive('lopLoginForm', lopLoginForm);

function lopLoginForm() {
    var directive = {
        restrict: 'E',
        templateUrl: "app/home/login-form/login-form.html",
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

LoginFormController.$inject = ['auth', '$state'];

function LoginFormController(auth, $state) {
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
        auth.login(vm.loginForm)
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