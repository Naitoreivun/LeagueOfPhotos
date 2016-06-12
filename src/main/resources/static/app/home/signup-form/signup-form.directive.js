"use strict";
angular
    .module('leagueOfPhotos')
    .directive('lopSignupForm', lopSignupForm);

function lopSignupForm() {
    var directive = {
        restrict: 'E',
        templateUrl: "app/home/signup-form/signup-form.html",
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

    vm.confirmPasswordOk = false;
    vm.correctPassword = correctPassword;
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

    function correctPassword() {
        console.log(vm.signupForm.password === vm.signupForm.confirmPassword);
        return vm.signupForm.password === vm.signupForm.confirmPassword;
    }

    function createUser() {
        usersService
            .create(vm.signupForm)
            .then(
                function (success) {
                    if(success) {
                        clear();
                        vm.afterSignUp = true;
                        focus('login-form-username');
                    }
                },
                function () {
                    console.log('SIGNUP ERROR');
                }
            );
    }
}