(function() {
    'use strict';

    ngApp.controller('containerConsoleCtrl', function($scope, $filter, $element, CONF, dockerApiSvr, containerSvr) {
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
                execUrl: CONF.SVR_URL.ws + '/exec/' + containerId + '/bash'
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


        $scope.attachUrl = CONF.SVR_URL.ws + '/attach/' + containerId;
        $scope.attachStart = true;

        $scope.logUrl = CONF.SVR_URL.ws + '/log/' + containerId;
        $scope.logStart = true;

        //semantic初始化
        $element.find('.tabular.menu > .item').tab();

    });

})();
