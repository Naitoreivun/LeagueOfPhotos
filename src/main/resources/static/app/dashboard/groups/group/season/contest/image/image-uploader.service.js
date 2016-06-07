"use strict";
angular
    .module('leagueOfPhotos')
    .factory('imageUploader', imageUploader);

imageUploader.$inject = ['FileUploader', 'store', 'authConst'];

function imageUploader(FileUploader, store, authConst) {

    var service = {
        getUploader: getUploader
    };

    return service;
    
    function getUploader() {
        var uploader = new FileUploader({
            url: 'api/images',
            method: 'POST',
            queueLimit: 2
        });

        var token = store.get(authConst.TOKEN);
        if (token) {
            uploader.headers = {Authorization: 'Bearer ' + token};
        }

        uploader.onAfterAddingFile = function (item) {
            if(uploader.queue.length === 2) {
                uploader.clearQueue();
                uploader.queue[0] = item;
            }
        };
        
        //https://github.com/nervgh/angular-file-upload/blob/master/examples/image-preview/controllers.js
        uploader.filters.push({
            name: 'imageFilter',
            fn: function(item /*{File|FileLikeObject}*/, options) {
                var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
            }
        });

        return uploader;
    }
}