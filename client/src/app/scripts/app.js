(function() {
    'use strict';

    //定义应用
    window.ngApp = angular.module('dockress', [
        'ngCookies',
        'ngTouch',
        'ngResource',
        'ui.router',
        'toastr',
        'pascalprecht.translate',
        'ngWebSocket',
        'highcharts-ng'
    ]);

    //配置
    ngApp.config(function($provide, $logProvider, $translateProvider, toastrConfig) {
        'ngInject';

        var conf = {};
        conf.SVR_URL = {
            base: 'http://localhost:9000/docker'
        };
        $provide.value('CONF', conf);




        // Enable log
        $logProvider.debugEnabled(true);

        // Set options third-party lib
        toastrConfig.allowHtml = true;
        toastrConfig.timeOut = 3000;
        toastrConfig.positionClass = 'toast-top-right';
        toastrConfig.preventDuplicates = true;
        toastrConfig.progressBar = true;

        //国际化
        var lang = window.localStorage.lang || 'zh_CN';
		$translateProvider.preferredLanguage(lang);
		$translateProvider.useSanitizeValueStrategy('escape');
		$translateProvider.useStaticFilesLoader({
		    prefix: '/i18n/',
		    suffix: '.json'
		});

        $translateProvider.useMissingTranslationHandler('regexTranslationHandler');

    });

    //启动
    ngApp.run(function($log) {
        'ngInject';

        $log.debug('已经启动！');
    });

})();
