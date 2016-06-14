(function() {
    'use strict';

    ngApp.config(function($stateProvider, $urlRouterProvider) {
            'ngInject';
            
            $stateProvider
                .state('overview', {
                    url: '/',
                    templateUrl: 'app/tpls/pages/overview.tpl.html',
                    controller: 'overviewCtrl'
                })
                .state('containers', {
                    url: '/containers',
                    templateUrl: 'app/tpls/pages/containers.tpl.html',
                    controller: 'containersCtrl'
                })
                .state('images', {
                    url: '/images',
                    templateUrl: 'app/tpls/pages/images.tpl.html',
                    controller: 'imagesCtrl'
                })
                .state('nodes', {
                    url: '/nodes',
                    templateUrl: 'app/tpls/pages/nodes.tpl.html',
                    controller: 'nodesCtrl'
                });

            $urlRouterProvider.otherwise('/');

        });


})();