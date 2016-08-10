(function() {
    'use strict';

    ngApp.directive('ngSwitch', function($websocket) {
        'ngInject';

        return {
            restrict: 'EA',
            replace: true,
            transclude: true,
            scope: {

                ngModel: '=',
                ngData: '=',

                disabled: '=',

                ngTrueValue: '=',
                ngFalseValue: '=',


                ngBeforeChange: '&',
                ngAfterChange: '&'
            },
            template: function(element, attrs) {
                var html = '<span class="switch" ng-click="onClick($event)" ng-class="{checked: checked, disabled: disabled}"><small></small><input type="checkbox" ng-model="' + attrs.ngModel + '" style="display:none" /></span>';

                return html;
            },
            link: function(scope, element, attrs) {
                
                scope.onClick = function($event) {

                    if(scope.disabled){
                        return;
                    }

                    if (!scope.ngBeforeChange($event, scope.ngData)) {
                        return;
                    };

                    calc();
                    refresh();

                    scope.ngAfterChange($event, scope.ngData);
                };

                scope.$watch('ngModel', function() {
                    refresh();
                })



                var calc = function() {
                    //计算model
                    scope.ngModel = scope.ngModel == scope.ngTrueValue ? scope.ngFalseValue : scope.ngTrueValue;
                }
                var refresh = function() {
                    //计算选中状态
                    scope.checked = scope.ngModel == scope.ngTrueValue ? true : false;
                };

                refresh();
            }
        };

    });

})();
