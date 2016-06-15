(function() {
    'use strict';

    ngApp.controller('overviewCtrl', function($scope, dockerApiSvr) {
        'ngInject';

        dockerApiSvr.info(function(rsp) {
            $scope.info = rsp;

            $('.overview .cpu .value').progress({
                percent: 90
            });
        });
        dockerApiSvr.containers(function(rsp) {
            $scope.containers = rsp;
        });




    });

})();
