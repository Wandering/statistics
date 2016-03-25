/**
 * Created by kepeng on 15/10/8.
 */

define(function(require, exports, module) {

    module.exports = function() {
        var moduleChart = require('../common/businessChart');

        moduleChart({
            url: '/statistics/notice/queryLineChartSendNoticeUserNum',
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
                require.async('./sendNoticeUserNumTable', function(realTimeDebt) {
                    realTimeDebt(param.name);
                });
            },
            legendData: ['发送公告用户数'],
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

