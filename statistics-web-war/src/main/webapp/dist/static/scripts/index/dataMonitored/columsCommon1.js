/**
 * Created by kepeng on 15/10/10.
 */

define('static/scripts/index/dataMonitored/columsCommon1', [], function(require, exports, module) {
    module.exports = function() {
        return {
            areaColumn: [{
                data: 'index',
                title: '编号'
            }, {
                data: 'account',
                title: '用户手机号'

            }, {
                data: 'errorStatus',
                title: '状态'
            }, {
                data: 'cardNumber',
                title: 'VIP卡号'
            }, {
                data: 'area',
                title: '用户注册地'
            }, {
                data: 'activeDate',
                title: '激活日期'
            }, {
                data: 'cardArea',
                title: 'vip卡来源地'
            }],
            columnDefs: [{
                "sClass": "center",
                "aTargets": [0]
            }, {
                "sClass": "center",
                "render": function (data, type, row) {
                    var str = '';
                    if (data == '1') {
                        str = '异常'
                    } else {
                        str = '正常'
                    }
                    return str;
                },
                "aTargets": [2]
            }, {
                "sClass": "center",
                "aTargets": [1]
            }, {
                "sClass": "center",
                "aTargets": [4]
            }, {
                "sClass": "center",
                "render": function (data, type, row) {
                    var str = '';
                    if (data == null || data == undefined || data == '') {
                        str = '未激活'
                    } else {
                        str = timeFomate(data);
                    }
                    return str;
                },
                "aTargets": [5]
            }]
        }
    }
});
