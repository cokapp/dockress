(function() {
    'use strict';

    ngApp.factory('containerSvr', function($rootScope, $http, CONF, utilSvr, $filter) {
        'ngInject';

        var dockerUrl = CONF.SVR_URL.base;

        var svr = {};

        svr.countStats = function(stats, queue) {
            if (typeof queue === 'undefined') {
                queue = svr.StatsQueue.create(5);
            }
            queue.add(stats);

            return queue.count();
        };


        svr.StatsQueue = {
            create: function(size) {
                var queue = utilSvr.Queue.create(size);

                queue.count = function() {
                    var stats = queue.stats = $.extend(true, {}, queue.last());
                    stats.overview = {};

                    //计算CPU
                    calcCpu(stats);
                    //计算RAM
                    calcRam(stats);
                    //计算NET
                    calcNet(stats, queue);

                    return queue;
                };

                return queue;
            }
        };




        //===================以下私有方法=======================

        //CPU
        function calcCpu(stats) {
            var overview = stats.overview;

            overview.cpu_use = stats.cpu_stats.cpu_usage.total_usage - stats.precpu_stats.cpu_usage.total_usage;
            overview.cpu_sys = stats.cpu_stats.system_cpu_usage - stats.precpu_stats.system_cpu_usage;
            overview.cpu_percent = 100 * overview.cpu_use / overview.cpu_sys;

            //图表
            var percent = $.extend(true, {}, CHART);
            percent.title.text = '<div class="chart-title"><strong>' + $filter('number')(overview.cpu_percent, 2) + '<sub> %</sub></strong><p>CPU使用率<span></p>';
            percent.series[0].data[0].y = overview.cpu_use;
            percent.series[0].data[1].y = overview.cpu_sys - overview.cpu_use;

            overview.cpu_percent_chart = percent;
        }
        //RAM
        function calcRam(stats) {
            var overview = stats.overview;

            overview.memory_percent = 100 * stats.memory_stats.usage / stats.memory_stats.limit;

            //图表
            var percent = $.extend(true, {}, CHART);
            percent.title.text = '<div class="chart-title"><strong>' + $filter('FileSize')(stats.memory_stats.usage, 1, 1000, true) + '</strong><p>内存使用量<span></p>';
            percent.series[0].data[0].y = stats.memory_stats.usage;
            percent.series[0].data[1].y = stats.memory_stats.limit - stats.memory_stats.usage;

            overview.memory_percent_chart = percent;
        }
        //网络状态
        function calcNet(stats, queue) {
            var overview = stats.overview;

            var firstStats = $.extend(true, {}, queue.first());
            var lastStats = $.extend(true, {}, queue.last());

            var firstNet = sumNet(firstStats);
            var lastNet = sumNet(lastStats);

            var ts = Date.parse(lastStats.read) - Date.parse(firstStats.read);
            overview.networks_up_speed = 1000 * (lastNet[0] - firstNet[0]) / ts;
            overview.networks_down_speed = 1000 * (lastNet[1] - firstNet[1]) / ts;
            overview.networks_up = lastNet[0];
            overview.networks_down = lastNet[1];
        };

        function sumNet(stats) {
            var netStats = [0, 0];
            for (var i in stats.networks) {
                var eth = stats.networks[i];
                netStats[0] += eth.tx_bytes;
                netStats[1] += eth.rx_bytes;
            };
            return netStats;
        };

        var CHART = {
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




        return svr;
    });

})();
