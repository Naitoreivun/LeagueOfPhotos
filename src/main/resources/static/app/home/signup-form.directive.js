"use strict";
angular
    .module('leagueOfPhotos')
    .directive('signupForm', signupForm);

function signupForm() {
    var directive = {
        restrict: 'E',
        templateUrl: "app/home/signup-form.html",
        scope: {
            afterSignUp: '='
        },
        controller: SignupFormController,
        controllerAs: 'vm',
        bindToController: true
    };

    return directive;
}

SignupFormController.$inject = ['usersService', 'focus'];

function SignupFormController(usersService, focus) {
    var vm = this;

    vm.createUser = createUser;
    vm.signupForm = {
        username: '',
        email: '',
        password: '',
        confirmPassword: ''
    };

    function clear() {
        vm.loginForm = {
            username: '',
            email: '',
            password: '',
            confirmPassword: ''
        };
    }

    function createUser() {
        usersService
            .post(vm.signupForm)
            .then(
                function (response) {
                    console.log(response);
                    clear();
                    vm.afterSignUp = true;
                    focus('login-form-username');
                },
                function () {
                    console.log('SIGNUP ERROR');
                }
            );
    }
}