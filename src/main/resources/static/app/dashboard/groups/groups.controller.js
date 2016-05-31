"use strict";
angular
    .module('leagueOfPhotos')
    .controller('GroupsController', GroupsController);

GroupsController.$inject = ['groupsService'];

function GroupsController(groupsService) {
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
        groups.userGroups.array = [];
        groupsService
            .getCurrentUserGroups()
            .then(
                function (data) {
                    groups.userGroups.array = data;
                });
    }
}