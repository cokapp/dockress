(function() {
    'use strict';

    ngApp.controller('imagesCtrl', function($scope, dockerApiSvr) {
        'ngInject';

        dockerApiSvr.images(function(rsp) {
            $scope.images = rsp;
        });
    });

})();
