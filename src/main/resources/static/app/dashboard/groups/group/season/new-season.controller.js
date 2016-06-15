"use strict";
angular
    .module('leagueOfPhotos')
    .controller('NewSeasonController', NewSeasonController);

NewSeasonController.$inject = ['$uibModalInstance', 'titlePrefix', 'oldSeason'];

function NewSeasonController($uibModalInstance, titlePrefix, oldSeason) {
    var season = this;

    season.cancel = cancel;
    season.save = create;
    season.dateFormat = 'dd-MM-yyyy';
    season.fromPopup = {
        opened: false,
        dateOptions: {
            minDate: new Date(),
            startingDay: 1
        }
    };
    season.new = {};
    season.open = open;
    season.titlePrefix = titlePrefix;
    season.toPopup = {
        opened: false,
        dateOptions: {
            minDate: new Date(),
            startingDay: 1
        }
    };

    activate();

    function activate() {
        if(oldSeason) {
            season.new = {
                name: oldSeason.name,
                description: oldSeason.description,
                startDate: oldSeason.startDate,
                finishDate: oldSeason.finishDate
            };
        }
        else {
            season.new = {
                name: '',
                description: '',
                startDate: null,
                finishDate: null
            };
        }
    }

    function cancel() {
        $uibModalInstance.dismiss('cancel');
    }

    function create() {
        var newSeason = {
            name: season.new.name,
            description: season.new.description,
            startDate: season.new.startDate,
            finishDate: season.new.finishDate
        };
        $uibModalInstance.close(newSeason);
    }

    function open(datepicker) {
        datepicker.opened = true;
    }
}