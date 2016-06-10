"use strict";
angular
    .module('leagueOfPhotos')
    .directive('lopVotingPhase', lopVotingPhase);

function lopVotingPhase() {
    var directive = {
        restrict: 'E',
        templateUrl: "app/dashboard/groups/group/season/contest/voting-phase/voting-phase.html",
        scope: {
            contestDetails: '='
        },
        controller: VotingPhaseController,
        controllerAs: 'vp',
        bindToController: true
    };

    return directive;
}

VotingPhaseController.$inject = ['imagesService', '$uibModal'];

function VotingPhaseController(imagesService, $uibModal) {
    var vp = this; // voting phase

    activate();
    vp.enlargeImage = enlargeImage;
    vp.images = [];
    vp.getVotableImages = getVotableImages;

    function activate() {
        getVotableImages();
    }

    function enlargeImage(index) {
        var largeImage = $uibModal.open({
            templateUrl: "app/dashboard/groups/group/season/contest/voting-phase/large-image.html",
            controller: LargeImageController,
            controllerAs: 'li',
            // size: 'lg',
            resolve: {
                image: vp.images[index]
            }
        });

        largeImage.result.then(function (rating) {
            vp.images[index].rating = rating;
            imagesService.vote(vp.images[index].id, rating);
        });
    }

    function getVotableImages() {
        vp.images = [];
        imagesService
            .getVotableImagesByContestId(vp.contestDetails.id)
            .then(
                function (data) {
                    vp.images = data;
                    console.log(data);
                }
            );
    }

}