"use strict";
angular
    .module('leagueOfPhotos')
    .controller('HomeController', HomeController);

HomeController.$inject = ['usersService', 'focus'];

function HomeController(usersService, focus) {
    var vm = this;

    vm.afterSignUp = false;
    vm.createUser = createUser;
    vm.logIn = logIn;
    vm.loginForm = {};
    vm.signupForm = {};

    active();

    function active() {
        vm.loginForm = {
            username: '',
            password: ''
        };

        vm.signupForm = {
            username: '',
            email: '',
            password: '',
            confirmPassword: ''
        }
    }

    function createUser() {
        usersService
            .post(vm.signupForm)
            .then(
                function (response) {
                    console.log(response);
                    vm.loginForm.username = vm.signupForm.username;
                    vm.signupForm = getClear(vm.signupForm);
                    vm.afterSignUp = true;
                    focus('login-form-password');
                },
                function () {
                    console.log('ERROR');
                });
    }

    function getClear(object) {
        return _.mapValues(object, function () {
            return '';
        });
    }

    function logIn() {
        console.log(vm.loginForm);
        vm.loginForm = getClear(vm.loginForm);
    }
}