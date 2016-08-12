(function() {
    'use strict';

    ngApp.filter('FileSize', function() {
        'ngInject';

        return function(bytes, precision, rate, useSub) {
            if (isNaN(parseFloat(bytes)) || !isFinite(bytes) || bytes==0) return '-';
            if (typeof precision === 'undefined') precision = 1;
            if (typeof rate === 'undefined') rate = 1024;
            if (typeof useSub === 'undefined') useSub = false;

            var number = Math.floor(Math.log(bytes) / Math.log(rate));
            var units = ['bytes', 'kB', 'MB', 'GB', 'TB', 'PB']
            if(useSub){
                units = ['<sub>bytes</sub>', '<sub>kB</sub>', '<sub>MB</sub>', '<sub>GB</sub>', '<sub>TB</sub>', '<sub>PB</sub>'];
            };
            return (bytes / Math.pow(rate, Math.floor(number))).toFixed(precision) + ' ' + units[number];
        }

    });

})();
