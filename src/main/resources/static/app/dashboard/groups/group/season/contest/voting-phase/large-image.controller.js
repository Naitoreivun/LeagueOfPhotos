"use strict";
angular
    .module('leagueOfPhotos')
    .controller('LargeImageController', LargeImageController);

LargeImageController.$inject = ['$uibModalInstance', 'image'];

function LargeImageController($uibModalInstance, image) {
    var li = this;

    li.cancel = cancel;
    li.image = image;
    li.tempRating = image.rating;
    li.vote = vote;

    function cancel() {
        $uibModalInstance.dismiss('cancel');
    }

    function vote() {
        $uibModalInstance.close(li.tempRating);
    }
}