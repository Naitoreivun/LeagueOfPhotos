"use strict";
angular
    .module('leagueOfPhotos')
    .controller('NewContestController', NewContestController);

NewContestController.$inject = ['$uibModalInstance', 'titlePrefix', 'oldContest'];

function NewContestController($uibModalInstance, titlePrefix, oldContest) {
    var contest = this;

    contest.cancel = cancel;
    contest.save = save;
    contest.dateFormat = 'dd-MM-yyyy';
    contest.startDatepicker = {
        opened: false,
        dateOptions: {
            minDate: new Date(),
            startingDay: 1
        }
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
    contest.titlePrefix = titlePrefix;

    activate();

    function activate() {
        if (oldContest) {
            contest.new = {
                name: oldContest.name,
                description: oldContest.description,
                startDate: oldContest.startDate,
                finishUploadingDate: oldContest.finishUploadingDate,
                finishVotingDate: oldContest.finishVotingDate
            };
        }
        else {
            contest.new = {
                name: '',
                description: '',
                startDate: null,
                finishUploadingDate: null,
                finishVotingDate: null
            };
        }
    }

    function cancel() {
        $uibModalInstance.dismiss('cancel');
    }

    function save() {
        var newContest = {
            seasonId: null,
            name: contest.new.name,
            description: contest.new.description,
            startDate: contest.new.startDate,
            finishUploadingDate: contest.new.finishUploadingDate,
            finishVotingDate: contest.new.finishVotingDate
        };
        $uibModalInstance.close(newContest);
    }

    function open(datepicker) {
        datepicker.opened = true;
    }
}