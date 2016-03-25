/**
 * Created by kepeng on 15/10/8.
 */
define('static/scripts/index/webuserLoginDayNum/webuserLoginDayNumTable', [], function(require, exports, module) {
    module.exports = function(dateDay, dateStart, dateEnd) {
        require.async(['static/scripts/index/common/businessTable'], function(businessTable) {
            var urlParams = '?dateDay=' + dateDay + '&start=' + dateStart + '&end=' + dateEnd;
            businessTable({
                dateDay: '',
                method: function() {
                    return '在' + dateDay + '中，登录天数' + dateStart + '至' + dateEnd + '天的人数';
                },
                url: {
                    areaUrl: '/statistics/webUserLogin/queryAreaUserLoginDateNum' + urlParams,
                    schoolUrl: '/statistics/webUserLogin/querySchoolUserLoginDateNum' + urlParams,
                    classUrl: '/statistics/webUserLogin/queryClassUserLoginDateNum' + urlParams
                },
                tableModel: {
                    areaColumn: [{
                        data: 'areaId',
                        title: ''
                    }, {
                        data: 'areaName',
                        title: '区域'
                    }, {
                        data: 'teacherAppNum',
                        title: '教师'
                    }, {
                        data: 'parentAppNum',
                        title: '家长'
                    }],
                    schoolColumn: [{
                        data: 'schoolId',
                        title: ''
                    }, {
                        data: 'schoolName',
                        title: '学校'
                    }, {
                        data: 'teacherAppNum',
                        title: '教师'
                    }, {
                        data: 'parentAppNum',
                        title: '家长'
                    }],
                    classColumn: [{
                        data: 'classId',
                        title: ''
                    }, {
                        data: 'className',
                        title: '班级'
                    }, {
                        data: 'teacherAppNum',
                        title: '教师'
                    }, {
                        data: 'parentAppNum',
                        title: '家长'
                    }],
                    columnDefs: [{
                        "bVisible": false,
                        "aTargets": [0]
                    }]
                }
            });
        });
    };
});