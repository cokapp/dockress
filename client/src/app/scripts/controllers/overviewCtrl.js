(function() {
    'use strict';

    ngApp.controller('overviewCtrl', function($scope, dockerApiSvr) {
        'ngInject';

        $scope.info = {};

        dockerApiSvr.loadInfo(function(rsp) {
            $scope.info = rsp;

            $('.overview .cpu .value').progress({
                percent: 90
            });
        });

    });

})();
