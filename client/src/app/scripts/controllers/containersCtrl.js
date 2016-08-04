(function() {
    'use strict';

    ngApp.controller('containersCtrl', function($scope, dockerApiSvr) {
        'ngInject';

        reload();



        //启动容器
        $scope.start = function(containerId) {
            dockerApiSvr.containers_start(containerId, function(rsp) {
                reload();
            });
        };
        //停止容器
        $scope.stop = function(containerId) {
            dockerApiSvr.containers_stop(containerId, function(rsp) {
                reload();
            });
        };



        function reload() {
            dockerApiSvr.containers(function(rsp) {
                $scope.containers = rsp.data;
            });
        }

    });

})();
