(function() {
    'use strict';

    ngApp.directive('ngTerm', function($websocket) {
        'ngInject';

        return {
            restrict: 'EA',
            scope: {
                url: '=',

                logs: '@',
                stdin: '@'
            },
            template: '<div class="ng-term"></div>',
            replace: false,
            link: function(scope, element, attrs) {
                var term = new Terminal({
                    cols: 100,
                    rows: 20,
                    convertEol: true,
                    useStyle: true,
                    cursorBlink: true,
                    screenKeys: true
                });

                term.open(element.find('.ng-term')[0]);

                var ws = $websocket(scope.url);

                ws.onMessage(function(message) {
                    term.write(message.data);
                });
                ws.onClose(function() {
                    term.destroy();
                });

                term.on('data', function(message) {
                    ws.send(message);
                });

                element.on('$destroy', function() {
                    term.destroy();
                });

                ws.send('\n');
            }
        };

    });

})();
