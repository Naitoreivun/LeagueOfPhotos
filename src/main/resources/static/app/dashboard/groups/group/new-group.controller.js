"use strict";
angular
    .module('leagueOfPhotos')
    .controller('NewGroupController', NewGroupController);

NewGroupController.$inject = ['$uibModalInstance', 'titlePrefix', 'oldGroup'];

function NewGroupController($uibModalInstance, titlePrefix, oldGroup) {
    var group = this;

    group.cancel = cancel;
    group.save = save;
    group.new = {};
    group.titlePrefix = titlePrefix;
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
        if(oldGroup) {
            group.new = {
                name: oldGroup.name,
                description: oldGroup.description,
                type: oldGroup.type
            };
        }
        else {
            group.new = {
                name: '',
                description: '',
                type: 'PUBLIC'
            };
        }
    }

    function cancel() {
        $uibModalInstance.dismiss('cancel');
    }

    function save() {
        var newGroup = {
            name: group.new.name,
            description: group.new.description,
            type: group.new.type
        };
        $uibModalInstance.close(newGroup);
    }
}