(function() {
    'use strict';

    ngApp.controller('containerCtrl', function($scope, $stateParams, $element, dockerApiSvr) {
        'ngInject';

        $scope.id = $stateParams.id;

        $scope.attachUrl = 'ws://ct.home:2375/containers/' + $stateParams.id + '/attach/ws?logs=0&stream=1&stdin=1&stdout=1&stderr=1';
        $scope.attachStart = true;

        $scope.execStart = false;
        dockerApiSvr.containers_exec($scope.id, function(rsp) {
            var execId = rsp.Id;

            //var url = 'http://ct.home:2375/exec/'+execId+'/start';


            dockerApiSvr.exec_start(execId, function(rsp2) {
                console.log('rsp2-------------------------------');
                console.log(rsp2);



                //$scope.execUrl = 'ws://ct.home:2375/exec/' + execId + '/start';
                //$scope.execStart = true;
            });
        });

        //semantic初始化
        $element.find('.tabular.menu > .item').tab();



    });

})();
