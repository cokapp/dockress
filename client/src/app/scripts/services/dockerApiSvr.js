(function() {
    'use strict';

    ngApp.factory('dockerApiSvr', function($http, CONF) {
        'ngInject';

        var dockerUrl = CONF.SVR_URL.base;


        var svr = {};

        svr.info = function(cb) {
            var url = dockerUrl + '/info';
            var p = $http({
                method: 'GET',
                url: url
            });
            p.success(function(rsp) {
                cb(rsp);
            });
        };

        svr.containers = function(cb) {
            var url = dockerUrl + '/containers/json';
            var p = $http({
                method: 'GET',
                url: url
            });
            p.success(function(rsp) {
                cb(rsp);
            });
        };
        svr.containers_start = function(id, cb) {
            var url = dockerUrl + '/containers/' + id + '/start';
            var p = $http({
                method: 'POST',
                url: url
            });
            p.success(function(rsp) {
                cb(rsp);
            });
        };
        svr.containers_stop = function(id, cb) {
            var url = dockerUrl + '/containers/' + id + '/stop';
            var p = $http({
                method: 'POST',
                url: url
            });
            p.success(function(rsp) {
                cb(rsp);
            });
        };
        svr.containers_inspect = function(id, cb) {
            var url = dockerUrl + '/containers/' + id + '/json';
            var p = $http({
                method: 'GET',
                url: url
            });
            p.success(function(rsp) {
                cb(rsp);
            });
        };
        svr.exec_start = function(id, cb) {
            var url = dockerUrl + '/exec/' + id + '/start';
            var p = $http({
                method: 'POST',
                url: url,
                headers: {
                    'Content-Type': 'application/json'
                },
                data: {
                    Detach: false,
                    Tty: true
                }
            });
            p.success(function(rsp) {
                cb(rsp);
            });
        };

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

        svr.images_search = function(param, cb) {
            var url = dockerUrl + '/images/search';
            var p = $http({
                method: 'GET',
                url: url,
                params: param
            });
            p.success(function(rsp) {
                cb(rsp);
            });
        };
        svr.images_create = function(data, cb) {
            var url = dockerUrl + '/images/create';
            var p = $http({
                method: 'POST',
                url: url,
                data: data
            });
            p.success(function(rsp) {
                cb(rsp);
            });
        };



        return svr;
    });

})();
