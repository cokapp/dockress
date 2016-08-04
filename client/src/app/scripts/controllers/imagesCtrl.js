(function() {
    'use strict';

    ngApp.controller('imagesCtrl', function($scope, dockerApiSvr) {
        'ngInject';

        reload();

        function reload() {
            dockerApiSvr.images(function(rsp) {
                $scope.images = rsp.data;
            });
        }

    });

})();
