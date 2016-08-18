(function() {
    'use strict';

    ngApp.filter('trustHtml', function($sce) {
        'ngInject';

        return function(text) {
            return $sce.trustAsHtml(text);
        };

    });

})();
