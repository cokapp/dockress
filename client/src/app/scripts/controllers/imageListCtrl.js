(function() {
    'use strict';

    ngApp.controller('imageListCtrl', function($scope, $filter, dockerApiSvr) {
        'ngInject';

        reload();

        function reload() {
            dockerApiSvr.images(function(rsp) {
                //处理id
                for(var i in rsp.data){
                    var image = rsp.data[i];
                    image.Id = $filter('limitTo')(image.Id, 12, 7);
                }

                $scope.images = rsp.data;
            });
        }

    });

})();
