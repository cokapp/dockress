(function() {
    'use strict';

    ngApp.controller('pullImageCtrl', function($scope, $filter, $element, $websocket, CONF) {
        'ngInject';

        var wsUrl = CONF.SVR_URL.ws;
        var pullManager = getPullManager();

        //拉取镜像
        $scope.pull = function(){
            pullManager.pull($scope.repository);
        }



        function getPullManager(){
            var ws = $websocket(wsUrl + '/pull');
            ws.onMessage(function(message) {
                console.log(message);
            });

            return {
                pull: function(repository){
                    console.log('pull'+repository);
                    ws.send(repository);
                }
            };
        }


    });

})();
