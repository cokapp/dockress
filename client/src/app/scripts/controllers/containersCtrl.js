(function() {
    'use strict';

    ngApp.controller('containersCtrl', function($scope, dockerApiSvr) {
        'ngInject';

        dockerApiSvr.containers(function(rsp) {
            $scope.containers = rsp;
        });


    });

})();
