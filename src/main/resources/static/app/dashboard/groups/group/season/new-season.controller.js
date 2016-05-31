"use strict";
angular
    .module('leagueOfPhotos')
    .controller('NewSeasonController', NewSeasonController);

NewSeasonController.$inject = ['$uibModalInstance'];

function NewSeasonController($uibModalInstance) {
    var season = this;

    season.cancel = cancel;
    season.create = create;
    season.dateFormat = 'dd-MM-yyyy';
    season.fromPopup = {
        opened: false,
        dateOptions: {
            minDate: new Date(),
            startingDay: 1
        }
    };
    season.new = {
        name: '',
        description: '',
        startDate: null,
        startTime: null,
        finishDate: null,
        finishTime: null
    };
    season.open = open;
    season.toPopup = {
        opened: false,
        dateOptions: {
            minDate: new Date(),
            startingDay: 1
        }
    };

    function cancel() {
        $uibModalInstance.dismiss('cancel');
    }

    function create() {
        var newSeason = {
            groupId: null,
            name: season.new.name,
            description: season.new.description,
            startDate: getFullDate(season.new.startDate, season.new.startTime),
            finishDate: getFullDate(season.new.finishDate, season.new.finishTime)
        };
        $uibModalInstance.close(newSeason);
    }

    function getFullDate(date, time) {
        var mTime = moment(time);
        var mDate = moment(date);
        mDate.hour(mTime.hour());
        mDate.minute(mTime.minute());
        return mDate.toDate();
    }

    function open(datepicker) {
        datepicker.opened = true;
    }
}