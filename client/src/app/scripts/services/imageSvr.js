(function() {
    'use strict';

    ngApp.factory('imageSvr', function($rootScope, $http, CONF, utilSvr, $filter) {
        'ngInject';

        var dockerUrl = CONF.SVR_URL.base;

        var svr = {};


        svr.images = function(cb) {
            var url = dockerUrl + '/images/json';
            var p = $http({
                method: 'GET',
                url: url
            });
            p.success(function(rsp) {
                cb(rsp);
            });
        };

        svr.remove = function(imageId, cb){
            var url = dockerUrl + '/images/' + imageId + '/remove';
            var p = $http({
                method: 'GET',
                url: url
            });
            p.success(function(rsp) {
                cb(rsp);
            });
        };






        //===================以下私有方法=======================
       

        return svr;
    });

})();
