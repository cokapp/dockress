(function() {
    'use strict';

    ngApp.controller('containerConsoleCtrl', function($scope, $filter, $element, dockerApiSvr, containerSvr) {
        'ngInject';

        var containerId = $scope.containerId;

        $scope.isMax = false;
        $scope.tabs = [];

        var num = 0;
        $scope.add = function() {
            if($scope.isMax){
                return;
            }
            add({
                id: 'exec-' + num,
                label: 'Exec' + num,
                content: 'Exec' + num,
                start: true,
                execUrl: 'ws://localhost:9000/exec/' + containerId + '/bash'
            });
            num++;
            if(num >= 5){
                $scope.isMax = true;
            }
        }

        function add(tab) {
            $scope.tabs.push(tab);
            $element.find('.tabular.menu > .item').tab();
        }


        $scope.attachUrl = 'ws://localhost:9000/attach/' + containerId;
        $scope.attachStart = true;

        $scope.logUrl = 'ws://localhost:9000/log/' + containerId;
        $scope.logStart = true;

        //semantic初始化
        $element.find('.tabular.menu > .item').tab();

    });

})();
