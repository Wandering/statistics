/**
 * Created by kepeng on 15/10/8.
 */
define('static/scripts/index/userAction/userActionFindTutorChart', ['static/scripts/index/common/businessChart'], function(require, exports, module) {

    module.exports = function() {
        var moduleChart = require('static/scripts/index/common/businessChart');

        moduleChart({
            url: '/statistics/userAction/queryFindTutorActionNum',
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
              /*  require.async('./userActiveClassTable', function(realTimeDebt) {
                    realTimeDebt(param.name);
                })*/;
            },
            legendData: ['PV', 'UV'],
            data: function(bizData) {
                var xAxisData = [],
                    pvNum = [],
                    uvNum = [];
                for (var i = 0; i < bizData.length; i++) {
                    xAxisData.push(bizData[i].dateDay);
                    pvNum.push(bizData[i].pvNum);
                    uvNum.push(bizData[i].uvNum);
                }

                return {
                    xAxisData: xAxisData,
                    seriesData: [{
                        name: 'PV',
                        type: 'line',
                        barMaxWidth: 30,
                        data: pvNum
                    },{
                        name: 'UV',
                        type: 'line',
                        barMaxWidth: 30,
                        data: uvNum
                    }]
                };
            }
        });
    }

});
