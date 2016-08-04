(function() {
    'use strict';

    ngApp.directive('includeReplace', function() {
        'ngInject';

        return {
            require: 'ngInclude',
            restrict: 'A',
            link: function(scope, el, attrs) {
                el.replaceWith(el.children());
            }
        };

    });

})();
