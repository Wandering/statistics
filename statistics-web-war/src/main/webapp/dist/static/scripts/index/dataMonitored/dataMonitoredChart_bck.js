/**
 * Created by kepeng on 15/9/21.
 */

define('static/scripts/index/dataMonitored/dataMonitoredChart_bck', ['static/scripts/index/common/businessChart', 'static/scripts/index/common/urlConfig'], function(require, exports, module) {

    module.exports = function() {
        var moduleChart = require('static/scripts/index/common/businessChart');
        var UrlConfig = require('static/scripts/index/common/urlConfig');

        moduleChart({
            //url: UrlConfig.getFindCard+"?errorStatus=1&errorDate=2016-04-18",
            url: UrlConfig.getFindCard,
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
                require.async(['static/scripts/index/dataMonitored/dataMonitoredList'], function(realTimeDebt) {
                    realTimeDebt(param.name);
                });
            },

            legendData: ['历史欠费统计'],
            data: function(bizData) {
                var xAxisData = [],
                    num = [];
                for (var i = 0; i < bizData.length; i++) {
                    xAxisData.push(bizData[i].date);
                    alert(xAxisData)
                    num.push(bizData[i].number);
                }
                return {
                    xAxisData: xAxisData,
                    seriesData: [{
                        name: '已激活用户总数',
                        type: 'line',
                        barMaxWidth: 30,
                        data: num
                    }]
                };
            }
        });
    }
});