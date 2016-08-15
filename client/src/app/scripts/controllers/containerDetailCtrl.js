(function() {
    'use strict';

    ngApp.controller('containerDetailCtrl', function($scope, $stateParams, $filter, $element, $websocket, dockerApiSvr, containerSvr) {
        'ngInject';

        var containerId = $scope.containerId = $stateParams.id;
        var statsUrl = 'ws://localhost:9000/stats/' + containerId;

        var ws = $websocket(statsUrl);
        
        var statsQueue;
        ws.onMessage(function(message) {
            statsQueue = containerSvr.countStats($.parseJSON(message.data), statsQueue);
            $scope.stats = statsQueue.stats;
        });






        $scope.attachUrl = 'ws://localhost:9000/attach/' + $stateParams.id;
        $scope.attachStart = true;


        $scope.execUrl = 'ws://localhost:9000/exec/' + $stateParams.id + '/bash';
        $scope.execStart = true;

        //semantic初始化
        $element.find('.tabular.menu > .item').tab();



        reload();


        function reload() {
            dockerApiSvr.containers_inspect($scope.containerId, function(rsp) {
                $scope.container = rsp.data;
            });
        }

    });

})();
