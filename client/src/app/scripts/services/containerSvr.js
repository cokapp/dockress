(function() {
    'use strict';

    ngApp.factory('containerSvr', function($rootScope, $http, CONF, utilSvr, $filter) {
        'ngInject';

        var dockerUrl = CONF.SVR_URL.base;

        var svr = {};

        svr.countStats = function(stats, queue) {
            if (typeof queue === 'undefined') {
                queue = StatsQueue.create(8);
            }
            queue.add(stats);
            return queue.count();
        };

        svr.create = function(containerCreateVo, cb){
            var url = dockerUrl + '/containers/create/' + containerCreateVo.imageId;
            var p = $http({
                method: 'POST',
                url: url,
                data: containerCreateVo
            });
            p.success(function(rsp) {
                cb(rsp);
            });
        };







        //===================以下私有方法=======================
       
        var StatsQueue = {
            create: function(size) {
                var queue = utilSvr.Queue.create(size);

                queue.count = function() {
                    queue.stats = queue.stats || {
                        overview: {}
                    };
                    var stats = $.extend(true, queue.stats, queue.last());

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

        //CPU
        function calcCpu(stats) {
            var overview = stats.overview;

            overview.cpu_use = stats.cpu_stats.cpu_usage.total_usage - stats.precpu_stats.cpu_usage.total_usage;
            overview.cpu_sys = stats.cpu_stats.system_cpu_usage - stats.precpu_stats.system_cpu_usage;
            overview.cpu_percent = 100 * overview.cpu_use / overview.cpu_sys;

            //图表
            var chart = $.extend(true, {}, CHART_PIE);
            chart.title.text = '<div class="chart-title"><strong>' + $filter('number')(overview.cpu_percent, 2) + '<sub> %</sub></strong><p>CPU使用率<span></p>';
            chart.series[0].data[0].y = overview.cpu_use;
            chart.series[0].data[1].y = overview.cpu_sys - overview.cpu_use;

            overview.cpu_percent_chart = chart;
        }
        //RAM
        function calcRam(stats) {
            var overview = stats.overview;

            overview.memory_percent = 100 * stats.memory_stats.usage / stats.memory_stats.limit;

            //图表
            var chart = $.extend(true, {}, CHART_PIE);
            chart.title.text = '<div class="chart-title"><strong>' + $filter('FileSize')(stats.memory_stats.usage, 1, 1000, true) + '</strong><p>内存使用量<span></p>';
            chart.series[0].data[0].y = stats.memory_stats.usage;
            chart.series[0].data[1].y = stats.memory_stats.limit - stats.memory_stats.usage;

            overview.memory_percent_chart = chart;
        }
        //网络状态
        function calcNet(stats, queue) {
            if (queue.getRealSize() <= 1) {
                return;
            }

            var overview = stats.overview;

            var firstStats = $.extend(true, {}, queue.first());
            var lastStats = $.extend(true, {}, queue.last());

            var firstNet = sumNet(firstStats);
            var lastNet = sumNet(lastStats);

            var ts = Date.parse(lastStats.read) - Date.parse(firstStats.read);
            overview.networks_read = lastStats.read;
            overview.networks_up_speed = 1000 * (lastNet[0] - firstNet[0]) / ts;
            overview.networks_down_speed = 1000 * (lastNet[1] - firstNet[1]) / ts;
            overview.networks_up = lastNet[0];
            overview.networks_down = lastNet[1];

            //图表
            var time = Date.parse(overview.networks_read);
            var xNum = 5;

            var speed_chart = $.extend(true, queue.stats.overview.networks_speed_chart, CHART_AREA);
            addNetPoint(speed_chart.series[0].data, xNum, time, overview.networks_up_speed);
            addNetPoint(speed_chart.series[1].data, xNum, time, overview.networks_down_speed);

            overview.networks_speed_chart = speed_chart;
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

        function addNetPoint(seriesData, num, time, speed) {

            while (seriesData.length < num) {
                seriesData.push({
                    x: time - (num - seriesData.length) * 1000,
                    y: 0
                });
            }

            if (seriesData.length == num) {
                seriesData.shift();
            }
            seriesData.push({
                x: time,
                y: speed
            });
        };

        var CHART_PIE = {
            options: {
                chart: {
                    type: 'pie',
                    spacing: [0, 0, 0, 0],
                    margin: [0, 0, 0, 0],
                    height: 160,
                    backgroundColor: 'transparent',
                    events: {}
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
        var CHART_AREA = {
            options: {
                chart: {
                    type: 'line',
                    animation: Highcharts.svg,
                    height: 160,
                    backgroundColor: 'transparent',
                    events: {}
                },
                title: {
                    text: null
                },
                legend: {
                    floating: true,
                    align: 'right',
                    verticalAlign: 'top',
                    labelFormatter: function() {
                        return this.name;
                    }
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
                    line: {
                        lineWidth: 1
                    }
                },
                credits: {
                    enabled: false
                },
                exporting: {
                    enabled: false
                }
            },
            xAxis: {
                type: 'datetime'
            },
            yAxis: {
                title: {
                    text: '网络带宽'
                }
            },
            series: [{
                name: '发送',
                data: []
            }, {
                name: '接收',
                data: []
            }]
        };

        return svr;
    });

})();
