(function() {
    'use strict';

    ngApp.controller('containerCreateCtrl', function($scope, $stateParams, $element, $websocket, dockerApiSvr, containerSvr) {
        'ngInject';

        var imageId = $scope.imageId = $stateParams.imageId;

        $scope.containerCreateVo = {
            imageId: imageId,
            portBindings: [],
            envs: [],
            binds: []
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

            addPortBinding({
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

            addEnv({
                name: undefined,
                value: undefined
            });
        };
        $scope.removeEnv = function(idx) {
            $scope.containerCreateVo.envs.splice(idx, 1);
        };

        $scope.bindsValidate = function() {
            for (var i in $scope.containerCreateVo.binds) {
                var bind = $scope.containerCreateVo.binds[i];
                if (!bindValidate(bind)) {
                    return false;
                }
            }
            return true;
        };
        $scope.addBind = function() {
            if (!$scope.bindsValidate()) {
                return;
            }

            addBind({
                pathHost: undefined,
                path: undefined,
                readOnly: false             
            });
        };
        $scope.removeBind = function(idx) {
            $scope.containerCreateVo.binds.splice(idx, 1);
        };

        $scope.create = function() {
            containerSvr.create($scope.containerCreateVo, function(rsp) {
                console.log(rsp);

            });
        };


        reload();

        function reload() {
            dockerApiSvr.images_inspect($scope.imageId, function(rsp) {
                $scope.image = rsp.data;

                //初始化端口
                for(var i in $scope.image.Config.ExposedPorts){
                    var exposedPort =  i.split('/');
                    addPortBinding({
                        protocol: exposedPort[1],
                        port: parseInt(exposedPort[0]),
                        hostPort: undefined                        
                    });
                }
                //初始化卷
                for(var i in $scope.image.Config.Volumes){
                    var volume = i;

                    addBind({
                        pathHost: undefined,
                        path: i,
                        readOnly: false             
                    });
                }
                //初始化环境变量
                for(var i in $scope.image.Config.Env){
                    var env = $scope.image.Config.Env[i].split('=');

                    addEnv({
                        name: env[0],
                        value: env[1]
                    });
                }                              

                console.log($scope.image);
            });
        };    


        function addPortBinding(data){
            $scope.containerCreateVo.portBindings.push(data);
        }
        function addBind(data){
            $scope.containerCreateVo.binds.push(data);            
        }
        function addEnv(data){
            $scope.containerCreateVo.envs.push(data);            
        }

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
        if (typeof env.name != 'undefined' 
            && typeof env.value != 'undefined') {
            return true;
        }
        return false;
    }
    function bindValidate(bind) {
        if (typeof bind.hostPath != 'undefined' 
            && typeof bind.path != 'undefined'
            && typeof bind.readOnly == 'boolean') {
            return true;
        }
        return false;
    }



})();
