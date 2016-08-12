(function() {
    'use strict';

    ngApp.controller('imageListCtrl', function($scope, dockerApiSvr) {
        'ngInject';

console.log('imageListCtrl');


        reload();

        function reload() {
            dockerApiSvr.images(function(rsp) {
                $scope.images = rsp.data;
            });
        }

    });

})();
