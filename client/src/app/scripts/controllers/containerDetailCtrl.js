(function() {
    'use strict';

    ngApp.controller('containerDetailCtrl', function($scope, $stateParams, $state, $filter, $element, $websocket, ngDialog, dockerApiSvr, containerSvr) {
        'ngInject';

        var containerId = $scope.containerId = $stateParams.id;
        var statsUrl = 'ws://localhost:9000/stats/' + containerId;

        var ws = $websocket(statsUrl);

        var statsQueue = undefined;

        ws.onMessage(function(message) {
            statsQueue = containerSvr.countStats($.parseJSON(message.data), statsQueue);
            $scope.stats = statsQueue.stats;
        });

        //切换容器状态
        $scope.switch = function($event) {
                if ($scope.container.State.Status == 'running') {
                    stop();
                } else {
                    start();
                }

                $scope.container.State.Status = 'changing';

                return false;
            }
            //打开命令行
        $scope.openConsole = function() {
            ngDialog.open({
                template: 'app/tpls/dialogs/console.tpl.html',
                scope: $scope,
                width: '90%',
                closeByDocument: false,
                closeByEscape: false,
                className: 'ngdialog-theme-plain'
            });
        }


        //启动容器
        function start() {
            dockerApiSvr.containers_start($scope.container.Id, function(rsp) {
                reload();
            });
        }
        //停止容器
        function stop() {
            dockerApiSvr.containers_stop($scope.container.Id, function(rsp) {
                reload();
            });
        }

        //semantic初始化
        $element.find('.tabular.menu > .item').tab();



        reload();


        function reload() {
            dockerApiSvr.containers_inspect($scope.containerId, function(rsp) {
                $scope.container = rsp.data;
            });
        };


        $scope.$on('$destroy', function() {
            statsQueue = undefined;
            ws.close();
        });


    });

})();
