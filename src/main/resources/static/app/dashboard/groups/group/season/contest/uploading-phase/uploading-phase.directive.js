"use strict";
angular
    .module('leagueOfPhotos')
    .directive('lopUploadingPhase', lopUploadingPhase);

function lopUploadingPhase() {
    var directive = {
        restrict: 'E',
        templateUrl: "app/dashboard/groups/group/season/contest/uploading-phase/uploading-phase.html",
        scope: {
            contestDetails: '='
        },
        controller: UploadingPhaseController,
        controllerAs: 'up',
        bindToController: true
    };

    return directive;
}

UploadingPhaseController.$inject = ['imagesService', 'imageUploader'];

function UploadingPhaseController(imagesService, imageUploader) {
    var up = this; // uploading phase

    up.image = null;
    up.uploader = {};
    up.newImageTtile = "";

    activate();
    
    function activate() {
        getUploader();
        loadCurrentUserImage();
    }

    function getUploader() {
        up.uploader = imageUploader.getUploader();

        up.uploader.onBeforeUploadItem = function (item) {
            item.formData = [{'title': up.newImageTtile, 'contestId': up.contestDetails.id}];
        };

        up.uploader.onSuccessItem = loadCurrentUserImage;
    }

    function loadCurrentUserImage() {
        up.image = null;
        imagesService
            .getByContestIdAndCurrentUser(up.contestDetails.id)
            .then(
                function (data) {
                    up.image = data;
                }
            );
    }
}