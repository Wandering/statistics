/**
 * Created by kepeng on 15/10/8.
 */

define(function(require, exports, module) {
    var UrlConfig = require('../common/urlConfig');
    module.exports = function() {
        var moduleChart = require('../common/businessChart');
        moduleChart({
            url: UrlConfig.getErrorChart,
            handle: function(param) {
                alert(23)
            },

            legendData: ['发送公告用户数'],
            data: function(bizData) {
                console.log(bizData)
                var xAxisData = [],
                    num = [];
                for (var i = 0; i < bizData.length; i++) {
                    xAxisData.push(bizData[i].dateDay);
                    num.push(bizData[i].num);
                }

                return {
                    xAxisData: xAxisData,
                    seriesData: [{
                        name: '发送公告用户数',
                        type: 'line',
                        barMaxWidth: 30,
                        data: num
                    }]
                };
            }
        });
    }
});

