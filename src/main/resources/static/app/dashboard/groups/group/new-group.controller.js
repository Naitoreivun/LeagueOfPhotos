"use strict";
angular
    .module('leagueOfPhotos')
    .controller('NewGroupController', NewGroupController);

NewGroupController.$inject = ['$uibModalInstance'];

function NewGroupController($uibModalInstance) {
    var group = this;

    group.cancel = cancel;
    group.create = create;
    group.new = {};
    group.types = [];

    activate();

    function activate() {
        group.types = [
            {
                name: 'Public',
                class: 'radio-success',
                value: 'PUBLIC'
            },
            {
                name: 'Private',
                class: 'radio-danger',
                value: 'PRIVATE'
            }
        ];
        group.new = {
            name: '',
            description: '',
            type: 'PUBLIC'
        };
    }

    function cancel() {
        $uibModalInstance.dismiss('cancel');
    }

    function create() {
        var newGroup = {
            name: group.new.name,
            description: group.new.description,
            type: group.new.type
        };
        $uibModalInstance.close(newGroup);
    }
}