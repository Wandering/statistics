/**
 * Created by kepeng on 15/10/8.
 */

define('static/scripts/index/userChatNum/chatUserNumChart', ['static/scripts/index/common/businessChart'], function(require, exports, module) {

    module.exports = function() {
        var moduleChart = require('static/scripts/index/common/businessChart');

        moduleChart({
            url: '/statistics/chat/queryLineChartUserChatNum',
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
                require.async(['static/scripts/index/userChatNum/chatUserNumTable'], function(realTimeDebt) {
                    //console.log(realTimeDebt);
                    realTimeDebt(param.name);
                });
            },
            legendData: ['教师', '家长'],
            data: function(bizData) {
                var xAxisData = [],
                    teacherNum = [],
                    parentNum = [];
                for (var i = 0; i < bizData.length; i++) {
                    xAxisData.push(bizData[i].dateDay);
                    teacherNum.push(bizData[i].teacherNum);
                    parentNum.push(bizData[i].parentNum);
                }

                return {
                    xAxisData: xAxisData,
                    seriesData: [{
                        name: '教师',
                        type: 'line',
                        barMaxWidth: 30,
                        data: teacherNum
                    },{
                        name: '家长',
                        type: 'line',
                        barMaxWidth: 30,
                        data: parentNum
                    }]
                };
            }
        });
    }
});

