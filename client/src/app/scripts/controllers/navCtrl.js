(function() {
    'use strict';

    ngApp.controller('navCtrl', function($scope, $location) {
        'ngInject';

        $scope.isActive = function(path) {
            return $location.path().match(path);
        }

    });

})();
