/**
 * Created by kepeng on 15/9/21.
 */

define(function(require, exports, module) {

    module.exports = function() {
        var moduleChart = require('../common/businessChart');

        moduleChart({
            url: '/statistics/billing/queryLineChartHistoryBilling',
            handle: function(param) {
                if ($('.grid-area')[0]) {
                    $('.grid-area').remove();
                }
                if ($('.grid-school')[0]) {
                    $('.grid-school').remove();
                }
                if ($('.grid-class')[0]) {
                    $('.grid-class').remove();
                }
                if ($('.grid-info')[0]) {
                    $('.grid-info').remove();
                }
                require.async('./pastDebtTable', function(realTimeDebt) {
                    realTimeDebt(param.name);
                });
            },
            legendData: ['历史欠费统计'],
            data: function(bizData) {
                var xAxisData = [],
                    num = [];
                for (var i = 0; i < bizData.length; i++) {
                    xAxisData.push(bizData[i].dateDay);
                    num.push(bizData[i].num);
                }

                return {
                    xAxisData: xAxisData,
                    seriesData: [{
                        name: '历史欠费统计',
                        type: 'line',
                        barMaxWidth: 30,
                        data: num
                    }]
                };
            }
        });
    }
});