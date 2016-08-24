(function() {
    'use strict';

    ngApp.controller('containerCreateCtrl', function($scope, $stateParams, $element, $websocket, dockerApiSvr, containerSvr) {
        'ngInject';

        var imageId = $scope.imageId = $stateParams.imageId;

        $scope.containerCreateVo = {
            imageId: imageId,
            portBindings: [],
            envs: []
        };

        $scope.portBindingsValidate = function() {
            for (var i in $scope.containerCreateVo.portBindings) {
                var portBinding = $scope.containerCreateVo.portBindings[i];
                if (!portBindingValidate(portBinding)) {
                    return false;
                }
            }
            return true;
        };
        $scope.addPortBinding = function() {
            if (!$scope.portBindingsValidate()) {
                return;
            }

            $scope.containerCreateVo.portBindings.push({
                protocol: 'tcp',
                port: undefined,
                hostPort: undefined
            });
        };
        $scope.removePortBinding = function(idx) {
            $scope.containerCreateVo.portBindings.splice(idx, 1);
        };


        $scope.envsValidate = function() {
            for (var i in $scope.containerCreateVo.envs) {
                var env = $scope.containerCreateVo.envs[i];
                if (!envValidate(env)) {
                    return false;
                }
            }
            return true;
        };
        $scope.addEnv = function() {
            if (!$scope.envsValidate()) {
                return;
            }

            $scope.containerCreateVo.envs.push({
                name: undefined,
                value: undefined
            });
        };
        $scope.removeEnv = function(idx) {
            $scope.containerCreateVo.envs.splice(idx, 1);
        };


        $scope.create = function() {
            containerSvr.create($scope.containerCreateVo, function(rsp) {
                console.log(rsp);
            });
        };

    });




    function portBindingValidate(portBinding) {
        var intReg = /^\d+$/;
        if (intReg.test(portBinding.port) &&
            intReg.test(portBinding.hostPort)) {
            return true;
        }
        return false;
    }

    function envValidate(env) {
        if (typeof env.name != 'undefined' && typeof env.value != 'undefined') {
            return true;
        }
        return false;
    }



})();
