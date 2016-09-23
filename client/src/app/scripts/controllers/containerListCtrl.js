(function() {
    'use strict';

    ngApp.controller('containerListCtrl', function($scope, $element, dockerApiSvr, containerSvr) {
        'ngInject';

        reload();



        //切换容器状态
        $scope.switch = function($event, container) {
            if (container.State == 'running') {
                stop(container);
            } else {
                start(container);
            }
            container.State = 'changing';
            return false;
        }
        //删除容器
        $scope.remove = function(container){
            if(!confirm('确定移除该容器吗？')){
                return;
            }
            containerSvr.remove(container.Id, function(rsp){
                reload();
            });
        }


        //启动容器
        function start(container) {
            dockerApiSvr.containers_start(container.Id, function(rsp) {
                reload();
            });
        }
        //停止容器
        function stop(container) {
            dockerApiSvr.containers_stop(container.Id, function(rsp) {
                reload();
            });
        }



        function reload() {
            dockerApiSvr.containers(function(rsp) {
                $scope.containers = rsp.data;
            });
        }

    });

})();
