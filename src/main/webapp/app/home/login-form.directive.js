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

function LoginFormController() {
    var vm = this;

    vm.logIn = logIn;
    vm.loginForm = {
        username: '',
        password: ''
    };

    function clear() {
        vm.loginForm = {
            username: '',
            password: ''
        };
    }

    function logIn() {
        console.log(vm.loginForm);
        clear();
    }

}