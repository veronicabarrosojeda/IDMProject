'use strict';

/*
 * Copyright (c) 2013 Giacomo Antolini <giacomo.antolini@gmail.com>.
 * All rights reserved.
 *
 * This file is part of angular-clearable.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

angular.module('xngClearable', []).
    directive('xngClearable', function () {
        return {
            restrict: 'A',
            require: 'ngModel',
            compile: function (tElement) {

                // Add css class to input
                tElement.addClass('xng-clearable');

                // Class name for clear button
                var clearClass = 'clear_button';

                // Add clear button DOM element
                tElement.after('<a tabindex="-1" class="' + clearClass + '">&times;</a>');
                var btn = tElement.next();
                btn.css('font-size', Math.round(tElement.prop('offsetHeight') * 0.7) + 'px');
                btn.css('position', 'absolute');
                btn.css('right', '10px');
                btn.css('z-index', '2');
                btn.css('cursor', 'pointer');
                btn.css('text-decoration', 'none');
                btn.mouseenter(function(){
                    btn.addClass('text-primary');
                    btn.removeClass('text-success');
                });
                btn.mouseleave(function(){
                    btn.addClass('text-success'); 
                    btn.removeClass('text-primary');
                });

                return function (scope, iElement, iAttrs) {

                    if (iElement[0].tagName === 'INPUT') {
                        var text = angular.element(iElement[0]);

                        btn.bind('mousedown', function (e) {
                            text.val('');
                            text.triggerHandler('input');
                            text.focus();
                            e.preventDefault();
                        });

                        scope.$watch(iAttrs.ngModel, function (v) {
                            if (v && v.length > 0) {
                                btn.css('display', 'block');
                            } else {
                                btn.css('display', 'none');
                            }
                        });
                    }
                };

            }
        };
    });
