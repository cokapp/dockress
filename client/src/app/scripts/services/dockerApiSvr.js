(function() {
    'use strict';

    ngApp.factory('dockerApiSvr', function($http) {
        'ngInject';

        var svr = {};

        svr.info = function(cb) {
            var url = 'http://ub.home:2376/info';
            var p = $http({
                method: 'GET',
                url: url
            });
            p.success(function(rsp) {
                cb(rsp);
            });
        };

        svr.containers = function(cb) {
            var url = 'http://ub.home:2376/containers/json';
            var p = $http({
                method: 'GET',
                url: url
            });
            p.success(function(rsp) {
                cb(rsp);
            });
        };


        return svr;
    });

})();
