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
        svr.containers_attach = function(id, cb) {
            var url = dockerUrl + '/containers/' + id + '/attach';
            var p = $http({
                method: 'POST',
                url: url
            });
            p.success(function(rsp) {
                cb(rsp);
            });
        };
        svr.containers_exec = function(id, cb) {
            var url = dockerUrl + '/containers/' + id + '/exec';
            var p = $http({
                method: 'POST',
                url: url,
                data: {
                    'AttachStdin': true,
                    'AttachStdout': true,
                    'AttachStderr': true,
                    'DetachKeys': 'ctrl-p,ctrl-q',
                    'Tty': true,
                    'Cmd': [
                        'bash'
                    ]
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
