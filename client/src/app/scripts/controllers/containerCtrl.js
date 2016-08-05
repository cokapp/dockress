(function() {
    'use strict';

    ngApp.controller('containerCtrl', function($scope, $stateParams, $element, $websocket, dockerApiSvr) {
        'ngInject';

        var containerId = $scope.containerId = $stateParams.id;
        var statsUrl = 'ws://localhost:9000/stats/' + containerId;

        var ws = $websocket(statsUrl);

        var preStats = null;
        ws.onMessage(function(message) {
            var stats = $scope.stats = $.parseJSON(message.data);
            console.log(message.data);

            stats.overview = {};

            if(preStats == null){
                preStats = stats;
                return;
            }


            //CPU
            var cpuDelta = stats.cpu_stats.cpu_usage.total_usage - stats.precpu_stats.cpu_usage.total_usage;
            var systemDelta = stats.cpu_stats.system_cpu_usage - stats.precpu_stats.system_cpu_usage;
            stats.overview.cpu_percent = 100 * cpuDelta / systemDelta;

            //RAM
            stats.overview.memory_percent = 100 * stats.memory_stats.usage / stats.memory_stats.limit;

            //NET
            var preNetUp = 0,
                preNetDown = 0;
            for (var i in preStats.networks) {
                var eth = preStats.networks[i];
                preNetUp += eth.tx_bytes;
                preNetDown += eth.rx_bytes;
            };
            var netUp = 0,
                netDown = 0;
            for (var i in stats.networks) {
                var eth = stats.networks[i];
                netUp += eth.tx_bytes;
                netDown += eth.rx_bytes;
            };
            var ts = Date.parse(stats.read) - Date.parse(preStats.read);

            console.log(netUp);
            console.log(preNetUp);
            console.log(ts);
            stats.overview.networks_up_speed = 1000 * (netUp - preNetUp) / ts;
            stats.overview.networks_down_speed = 1000 * (netDown - preNetDown) / ts;

            stats.overview.networks_up = netUp;
            stats.overview.networks_down = netDown;

            preStats = stats;
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
