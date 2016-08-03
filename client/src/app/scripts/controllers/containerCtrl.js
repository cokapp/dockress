(function() {
    'use strict';

    ngApp.controller('containerCtrl', function($scope, $stateParams, $element, dockerApiSvr) {
        'ngInject';

        $scope.id = $stateParams.id;

        $scope.attachUrl = 'ws://localhost:9000/attach/' + $stateParams.id;
        $scope.attachStart = true;


        $scope.execUrl = 'ws://localhost:9000/exec/' + $stateParams.id + '/bash';
        $scope.execStart = true;

        //semantic初始化
        $element.find('.tabular.menu > .item').tab();



    });

})();
