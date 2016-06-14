(function() {
    'use strict';

    ngApp.factory('dockerApiSvr', function($http) {
        'ngInject';

        var svr = {};

        svr.loadInfo = function(cb) {
            var url = 'http://ub.home:2375/info';
            var p = $http({
                method: 'GET',
                url: url
            });
            p.success(function(rsp) {
                cb(rsp);
            });
        }

        return svr;
    });

})();
