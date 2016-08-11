(function() {
    'use strict';

    ngApp.controller('imageListCtrl', function($scope, dockerApiSvr) {
        'ngInject';

        reload();

        function reload() {
            dockerApiSvr.images(function(rsp) {
                $scope.images = rsp.data;
            });
        }

    });

})();
