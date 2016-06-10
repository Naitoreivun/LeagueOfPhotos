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
    groups.getTypeClass = getTypeClass;
    groups.getUserGroups = getUserGroups;
    groups.typeClassMap = {
        'PRIVATE': "label-danger",
        'PUBLIC': "label-success"
    };
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

    function getTypeClass(type) {
        return groups.typeClassMap[type];
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