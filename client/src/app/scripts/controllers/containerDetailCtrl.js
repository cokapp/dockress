(function() {
    'use strict';

    var chartConfig = {
        options: {
            chart: {
                type: 'pie',
                spacing: [0, 0, 0, 0],
                margin: [0, 0, 0, 0],
                height: 160,
                backgroundColor: 'transparent'
            },
            tooltip: {
                enabled: false
            },
            plotOptions: {
                series: {
                    animation: false,
                    dataLabels: {
                        enabled: false
                    },
                    enableMouseTracking: false
                },
                pie: {
                    borderWidth: 0
                }
            },
            credits: {
                enabled: false
            },
            exporting: {
                enabled: false
            }
        },
        title: {
            useHTML: true,
            text: '',
            align: 'center',
            verticalAlign: 'middle'
        },
        series: [{
            type: 'pie',
            name: 'datas',
            innerSize: '90%',
            data: [{
                name: 'data1',
                y: 0,
                color: '#5EA3F8'
            }, {
                name: 'data2',
                y: 1,
                color: '#CCC'
            }]
        }]
    };




    ngApp.controller('containerDetailCtrl', function($scope, $stateParams, $filter, $element, $websocket, dockerApiSvr) {
        'ngInject';

        var containerId = $scope.containerId = $stateParams.id;
        var statsUrl = 'ws://localhost:9000/stats/' + containerId;

        var ws = $websocket(statsUrl);

        var preStats = null;
        ws.onMessage(function(message) {
            var stats = $scope.stats = $.parseJSON(message.data);
            console.log(message.data);

            stats.overview = {};

            if (preStats == null) {
                preStats = stats;
                return;
            }


            //CPU
            var cpuDelta = stats.cpu_stats.cpu_usage.total_usage - stats.precpu_stats.cpu_usage.total_usage;
            var systemDelta = stats.cpu_stats.system_cpu_usage - stats.precpu_stats.system_cpu_usage;
            stats.overview.cpu_percent = 100 * cpuDelta / systemDelta;

            var cpu_percent_chart = $.extend(true, {}, chartConfig);
            cpu_percent_chart.title.text = '<div class="chart-title"><strong>' + $filter('number')(stats.overview.cpu_percent, 2) + '<sub> %</sub></strong><p>CPU使用率<span></p>';
            cpu_percent_chart.series[0].data[0].y = cpuDelta;
            cpu_percent_chart.series[0].data[1].y = systemDelta - cpuDelta;
            stats.overview.cpu_percent_chart = cpu_percent_chart;

            //RAM
            stats.overview.memory_percent = 100 * stats.memory_stats.usage / stats.memory_stats.limit;

            var memory_percent_chart = $.extend(true, {}, chartConfig);
            memory_percent_chart.title.text = '<div class="chart-title"><strong>' + $filter('FileSize')(stats.memory_stats.usage, 1, 1000, true) + '</strong><p>内存使用量<span></p>';
            memory_percent_chart.series[0].data[0].y = stats.memory_stats.usage;
            memory_percent_chart.series[0].data[1].y = stats.memory_stats.limit - stats.memory_stats.usage;
            stats.overview.memory_percent_chart = memory_percent_chart;

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
