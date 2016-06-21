(function() {
    'use strict';

    ngApp.controller('containerCtrl', function($scope, $stateParams) {
        'ngInject';

        $scope.id = $stateParams.id;

    });

})();
