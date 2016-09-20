(function() {
    'use strict';

    //定义应用
    window.ngApp = angular.module('dockress', [
        'ngCookies',
        'ngResource',
        'ngSanitize',
        'ui.router',
        'toastr',
        'pascalprecht.translate',
        'ngWebSocket',
        'ngDialog',
        'highcharts-ng',
        'ngMaterial'
    ]);

    //启动
    ngApp.run(function($log) {
        'ngInject';

        $log.debug('已经启动！');
    });

})();
