"use strict";
angular
    .module('leagueOfPhotos')
    .controller('ContestController', ContestController);

ContestController.$inject = ['$stateParams', 'contestsService', 'imagesService', 'imageUploader'];

function ContestController($stateParams, contestsService, imagesService, imageUploader) {
    var contest = this;

    contest.details = {};
    contest.getDetails = getDetails;
    contest.groupId = $stateParams.groupId;
    contest.id = $stateParams.contestId;
    contest.image = null;
    contest.loadCurrentUserImage = loadCurrentUserImage;
    contest.seasonId = $stateParams.seasonId;
    contest.uploader = {};


    activate();

    function activate() {
        getDetails();
        getUploader();
        loadCurrentUserImage();
    }

    function getDetails() {
        contest.details = {};
        contestsService
            .getById(contest.id)
            .then(
                function (data) {
                    contest.details = data;
                },
                function (errors) {
                    console.log('ERRORS:', errors);
                }
            )
    }

    function getUploader() {
        contest.uploader = imageUploader.getUploader();

        contest.uploader.onBeforeUploadItem = function (item) {
            item.formData = [{'title': 'opos', 'contestId': contest.id}];
        };

        contest.uploader.onSuccessItem = loadCurrentUserImage;
    }


    function loadCurrentUserImage() {
        contest.image = null;
        imagesService
            .getByContestIdAndCurrentUser(contest.id)
            .then(
                function (data) {
                    contest.image = data;
                }
            );
    }
}