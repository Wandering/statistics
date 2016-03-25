/**
 * Created by shixin on 15/12/21.
 */
define('static/scripts/index/basicStatus/basicStatusChart', ['static/scripts/index/common/businessChart'], function(require, exports, module) {

    module.exports = function() {
        var moduleChart = require('static/scripts/index/common/businessChart');

        moduleChart({
            url: '/statistics/increaseUser/queryIncreaseUser',
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
                if ($('.grid-parent')[0]) {
                    $('.grid-parent').remove();
                }
                if ($('.grid-teacher')[0]) {
                    $('.grid-teacher').remove();
                }
                require.async(['static/scripts/index/basicStatus/basicStatusTable'], function(realTimeDebt) {
                    realTimeDebt(param.name);
                });
            },
            legendData: ['教师-APP', '家长-APP'],
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
                        name: '教师-APP',
                        type: 'line',
                        barMaxWidth: 30,
                        data: teacherNum
                    },{
                        name: '家长-APP',
                        type: 'line',
                        barMaxWidth: 30,
                        data: parentNum
                    }]
                };
            }
        });
    }
});
