(function() {
    'use strict';

    ngApp.controller('headerCtrl', function($scope, $location) {
        'ngInject';

        $scope.isActive = function(path) {
            return $location.path().match(path);
        }

    });

})();
