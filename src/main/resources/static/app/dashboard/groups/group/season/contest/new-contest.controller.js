"use strict";
angular
    .module('leagueOfPhotos')
    .controller('NewContestController', NewContestController);

NewContestController.$inject = ['$uibModalInstance'];

function NewContestController($uibModalInstance) {
    var contest = this;

    contest.cancel = cancel;
    contest.create = create;
    contest.dateFormat = 'dd-MM-yyyy';
    contest.startDatepicker = {
        opened: false,
        dateOptions: {
            minDate: new Date(),
            startingDay: 1
        }
    };
    contest.new = {
        name: '',
        description: '',
        startDate: null,
        startTime: null,
        finishUploadingDate: null,
        finishUploadingTime: null,
        finishVotingDate: null,
        finishVotingTime: null
    };
    contest.open = open;
    contest.finishUploadingDatepicker = {
        opened: false,
        dateOptions: {
            minDate: new Date(),
            startingDay: 1
        }
    };
    contest.finishVotingDatepicker = {
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
        var newContest = {
            seasonId: null,
            name: contest.new.name,
            description: contest.new.description,
            startDate: getFullDate(contest.new.startDate, contest.new.startTime),
            finishUploadingDate: getFullDate(contest.new.finishUploadingDate, contest.new.finishUploadingTime),
            finishVotingDate: getFullDate(contest.new.finishVotingDate, contest.new.finishVotingTime)
        };
        $uibModalInstance.close(newContest);
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