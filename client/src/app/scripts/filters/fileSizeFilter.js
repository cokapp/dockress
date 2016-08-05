(function() {
    'use strict';

    ngApp.filter('FileSize', function() {
        'ngInject';

        return function(bytes, precision, rate) {
            if (isNaN(parseFloat(bytes)) || !isFinite(bytes) || bytes==0) return '-';
            if (typeof precision === 'undefined') precision = 1;
            if (typeof rate === 'undefined') rate = 1024;
            var units = ['bytes', 'kB', 'MB', 'GB', 'TB', 'PB'],
                number = Math.floor(Math.log(bytes) / Math.log(rate));
            return (bytes / Math.pow(rate, Math.floor(number))).toFixed(precision) + ' ' + units[number];
        }

    });

})();
