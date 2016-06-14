(function() {
    'use strict';

    ngApp.filter('T', function($translate) {
        'ngInject';

        return function(key) {
            if (key) {
                return $translate.instant(key) | key;
            }
        };

    });

})();
