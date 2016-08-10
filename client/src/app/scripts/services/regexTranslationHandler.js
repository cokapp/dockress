(function() {
    'use strict';

    ngApp.factory('regexTranslationHandler', function($translate) {
        'ngInject';



        return function(translationID) {
            var result = translationID;

            var translationTable = $translate.getTranslationTable();

            //key: up on (.*) hours, next (.*) days
            //translationID: up on 5 hours, next 6 days
            //result: 超过 {{1}} 小时，等待 {{2}} 天
            for (var key in translationTable) {
                var regex = new RegExp(key).exec(translationID);
                if (regex == null) {
                    continue;
                }
                result = translationTable[key];

                for (var i = 1; i < regex.length; i++) {
                    result = result.replace('{{' + i + '}}', regex[i]);
                }
            }
            return result;
        }

    });

})();
