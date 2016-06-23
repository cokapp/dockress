(function() {
    'use strict';

    ngApp.controller('containerCtrl', function($scope, $stateParams) {
        'ngInject';

        $scope.id = $stateParams.id;
        $scope.wsUrl = 'ws://ub.home:2375/containers/' + $stateParams.id + '/attach/ws?logs=0&stream=1&stdin=1&stdout=1&stderr=1';
    });

})();
