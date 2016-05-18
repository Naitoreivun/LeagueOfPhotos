"use strict";
angular
    .module('leagueOfPhotos')
    .controller('GroupsController', GroupsController);

GroupsController.$inject = ['groupsService', 'Restangular'];

function GroupsController(groupsService, Restangular) {
    var groups = this;

    groups.allGroups = {
        array: [],
        isCollapsed: false
    };
    groups.collapse = collapse;
    groups.getAll = getAll;
    groups.getUserGroups = getUserGroups;
    groups.userGroups = {
        array: [],
        isCollapsed: false
    };

    activate();

    function activate() {
        getAll();
        getUserGroups();
    }
    
    function collapse(object) {
        object.isCollapsed = !object.isCollapsed;
    }

    function getAll() {
        groups.allGroups.array = [];
        groupsService
            .getAll()
            .then(
                function (data) {
                    groups.allGroups.array = data;
                });
    }

    function getUserGroups() {
        Restangular
            .one('groups/user/', 'current')
            .getList()
            .then(
                function (response) {
                    groups.userGroups.array = [];
                    _.forEach(response, function (val) {
                        groups.userGroups.array.push({
                            id: val.id,
                            name: val.name,
                            description: val.description,
                            creationDate: new Date(val.creationDate),
                            type: val.groupType.type
                        });
                    });
                },
                function (errors) {
                    console.log('ERRORS:', errors);
                }
            );
    }
}