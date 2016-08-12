(function() {
    'use strict';

    ngApp.config(function($locationProvider, $stateProvider, $urlRouterProvider) {
            'ngInject';
            //$locationProvider.html5Mode(true);

            $stateProvider
                .state('overview', {
                    url: '/',
                    templateUrl: 'app/tpls/pages/overview.tpl.html',
                    controller: 'overviewCtrl'
                })
                .state('image', {
                    url: '/image',
                    templateUrl: 'app/tpls/pages/image/list.tpl.html',
                    controller: 'imageListCtrl'
                })
                .state('registrys', {
                    url: '/registrys',
                    templateUrl: 'app/tpls/pages/registrys.tpl.html',
                    controller: 'registrysCtrl'
                })
                .state('nodes', {
                    url: '/nodes',
                    templateUrl: 'app/tpls/pages/nodes.tpl.html',
                    controller: 'nodesCtrl'
                })
                .state('container', {
                    url: '/container',
                    templateUrl: 'app/tpls/pages/container/list.tpl.html',
                    controller: 'containerListCtrl'
                })
                .state('containerDetail', {
                    url: '/container/{id}',
                    templateUrl: 'app/tpls/pages/container/detail.tpl.html',
                    controller: 'containerDetailCtrl'
                })
                .state('containerCreate', {
                    url: '/container/create/{imageId}',
                    templateUrl: 'app/tpls/pages/container/create.tpl.html',
                    controller: 'containerCreateCtrl'
                });

            $urlRouterProvider.otherwise('/');

        });


})();