(function() {
    'use strict';

    ngApp.controller('containerCtrl', function($scope, $stateParams, $element, $websocket, dockerApiSvr) {
        'ngInject';

        var containerId = $scope.containerId = $stateParams.id;

        var statsUrl = 'ws://localhost:9000/stats/' + containerId;

        var ws = $websocket(statsUrl);
        ws.onMessage(function(message) {
            $scope.stats = $.parseJSON(message.data);
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
