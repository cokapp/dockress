(function() {
    'use strict';

    ngApp.controller('containerCreateCtrl', function($scope, $stateParams, $element, $websocket, dockerApiSvr) {
        'ngInject';

        var imageId = $scope.imageId = $stateParams.imageId;

        $scope.containerCreateVo = {
            portBindings: []
        };


        $scope.addPortBinding = function() {
            $scope.containerCreateVo.portBindings.push({
                exposedPort: {
                    protocol: 'tcp',
                    port: null
                },
                binding: {
                    hostPortSpec: null
                }
            });
        };
        $scope.removePortBinding = function(idx){
        	$scope.containerCreateVo.portBindings.splice(idx, 1);
        };


    });

})();
