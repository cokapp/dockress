(function() {
    'use strict';

    ngApp.controller('registrysCtrl', function($scope, dockerApiSvr) {
        'ngInject';


        $scope.images = [{
            "star_count": 1,
            "is_official": false,
            "name": "cokappcharlie/aria2",
            "is_trusted": true,
            "is_automated": true,
            "description": "aria2 in docker"
        }, {
            "star_count": 1,
            "is_official": false,
            "name": "cokapp/cokwiki",
            "is_trusted": false,
            "is_automated": false,
            "description": "CokWiki是运行于Node平台的类Wiki程序"
        }];


        $scope.search = function() {
            var param = {
                limit: 50
            }
            if ($scope.term) {
                param.term = $scope.term;
            } else {
                param.filters = {
                    'is-official': true
                }
            }

            dockerApiSvr.images_search(param, function(rsp) {
                $scope.images = rsp;
            })
        }
        $scope.create = function(name) {
        	var data = {};
        	data.fromImage = name; 
            dockerApiSvr.images_create(data, function(rsp){
            	console.log(rsp);
            });
        }

    });

})();
