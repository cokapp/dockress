(function() {
    'use strict';

    ngApp.factory('utilSvr', function($rootScope, $http, CONF) {
        'ngInject';

        var svr = {};


        //简易队列
        svr.Queue = {

            create: function(size) {
                var ds = new Array();
                var idx = 0;

                var queue = {};
                queue.add = function(obj) {
                    if (idx >= size) {
                        queue.remove();
                    }
                    ds[idx] = obj;
                    idx++;
                };
                queue.remove = function() {
                    if (ds.length > 0) {
                        idx--;
                        return ds.shift();
                    }
                };
                queue.first = function() {
                    if (ds.length > 0) {
                        return ds[0];
                    }
                };
                queue.last = function() {
                    return ds[idx - 1];
                }
                queue.getAll = function() {
                    return ds;
                };
                queue.getSize = function(){
                    return size;
                };
                queue.getRealSize = function(){
                    return idx;
                };

                return queue;
            }
        };



        return svr;
    });

})();
