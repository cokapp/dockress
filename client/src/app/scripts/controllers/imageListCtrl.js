(function() {
    'use strict';

    ngApp.controller('imageListCtrl', function($scope, $filter, $websocket, ngDialog, CONF, imageSvr) {
        'ngInject';

        var wsUrl = CONF.SVR_URL.ws;

        reload();

        //删除镜像
        $scope.remove = function(image){
            if(!confirm('确定移除该镜像吗？')){
                return;
            }
            imageSvr.remove(image.Id, function(rsp){
                reload();
            });
        }

        $scope.pull = function() {
            ngDialog.open({
                template: 'app/tpls/dialogs/pullImage.tpl.html',
                scope: $scope,
                width: '60%',
                closeByDocument: true,
                closeByEscape: true,
                className: 'ngdialog-theme-plain'
            });
        }


        //===================以下私有方法=======================


        function reload() {
            imageSvr.images(function(rsp) {
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
