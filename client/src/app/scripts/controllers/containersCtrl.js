(function() {
    'use strict';

    ngApp.controller('containersCtrl', function($scope, dockerApiSvr) {
        'ngInject';

        reload();


        var isSwitch = false;
        //切换容器状态
        $scope.switch = function($event, container) {
            console.log('clicked');


            return cancelHandler($event);

            console.log(container.State);
            if (container.State == 'running') {
                stop(container);
            } else {
                start(container);
            }
        }


        //启动容器
        function start(container) {
            dockerApiSvr.containers_start(container.Id, function(rsp) {
                container.State = 'running';
            });
        };
        //停止容器
        function stop(container) {
            dockerApiSvr.containers_stop(container.Id, function(rsp) {
                container.State = 'exited';
            });
        };



        function reload() {
            dockerApiSvr.containers(function(rsp) {
                $scope.containers = rsp.data;
            });
        }

        function cancelHandler(event) {
            var event = event || window.event; //用于IE  
            if (event.preventDefault) event.preventDefault(); //标准技术  
            if (event.returnValue) event.returnValue = false; //IE  
            return false; //用于处理使用对象属性注册的处理程序  
        }

    });

})();
