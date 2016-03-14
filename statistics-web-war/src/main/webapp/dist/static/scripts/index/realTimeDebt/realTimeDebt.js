/**
 * Created by kepeng on 15/9/18.
 */
define('static/scripts/index/realTimeDebt/realTimeDebt', [], function(require, exports, module) {
    module.exports = function() {
        require.async(['static/scripts/index/common/businessTable'], function(businessTable) {
            businessTable({
                dateDay: '',
                url: {
                    areaUrl: '/statistics/billing/queryAreaBillingNum',
                    schoolUrl: '/statistics/billing/querySchoolBillingNum',
                    classUrl: '/statistics/billing/queryClassBillingNum',
                    detailUrl: '/statistics/billing/queryRealTimeBillingDetail'
                },
                tableModel: {
                    areaColumn: [{
                        data: 'areaId',
                        title: ''
                    }, {
                        data: 'areaName',
                        title: '区域'
                    }, {
                        data: 'num',
                        title: '人数'
                    }],
                    schoolColumn: [{
                        data: 'schoolId',
                        title: ''
                    }, {
                        data: 'schoolName',
                        title: '学校'
                    }, {
                        data: 'num',
                        title: '人数'
                    }],
                    classColumn: [{
                        data: 'classId',
                        title: ''
                    }, {
                        data: 'className',
                        title: '班级'
                    }, {
                        data: 'num',
                        title: '人数'
                    }],
                    columnDefs: [{
                        "bVisible": false,
                        "aTargets": [0]
                    }],
                    infoColumn: [{
                        data: 'id'
                    }, {
                        data: 'userName',
                        title: '用户名'
                    }, {
                        data: 'studentName',
                        title: '学生'
                    }, {
                        data: 'phoneNum',
                        title: '计费号码'
                    }, {
                        data: 'eMoney',
                        title: '欠费金额'
                    }, {
                        data: 'eAge',
                        title: '欠费账龄'
                    }, {
                        data: 'eLevel',
                        title: '欠费级别'
                    }]
                }
            });
        });
    };
});