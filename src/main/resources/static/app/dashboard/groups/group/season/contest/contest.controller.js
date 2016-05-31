"use strict";
angular
    .module('leagueOfPhotos')
    .controller('ContestController', ContestController);

ContestController.$inject = ['$stateParams', 'contestsService'];

function ContestController($stateParams, contestsService) {
    var contest = this;

    contest.details = {};
    contest.getDetails = getDetails;
    contest.groupId = $stateParams.groupId;
    contest.id = $stateParams.contestId;
    contest.seasonId = $stateParams.seasonId;

    activate();

    function activate() {
        getDetails();
    }

    function getDetails() {
        // group.details = {};
        // groupsService
        //     .getById($stateParams.id)
        //     .then(
        //         function (data) {
        //             group.details = data;
        //         },
        //         function (errors) {
        //             console.log('ERRORS:', errors);
        //         }
        //     )
    }
}