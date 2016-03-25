/**
 * Created by pdeng on 16/3/24.
 */
define(function (require, exports, module) {
    //获取所需组件依赖
    require('bootstrap');
    require('cookie');
    require('dialog');
    var timeFomate = require('../common/timeFomate.js');
    var message = require('../message.js');
    var Table = require('../datatable.js');
    var UrlConfig = require('../common/urlConfig');
    var col = [{
        data: 'index',
        title: '编号'
    }, {
        data: 'cardNumber',
        title: 'VIP卡号'
    }, {
        data: 'goodsStatus',
        title: '状态'
    }, {
        data: 'inputDate',
        title: '手机号'
    }, {
        data: 'area',
        title: '所在地'
    }, {
        data: 'inputDate',
        title: '激活日期'
    }];
    var columnDefs = [{
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
        "aTargets": [3]
    },{
        "sClass": "center",
        "aTargets": [4]
    }, {
        "sClass": "center",
        "render": function (data, type, row) {
            var str = '';
            if (data == '0') {
                str = '未激活'
            } else {
                str = timeFomate(data);
            }
            return str;
        },
        "aTargets": [5]
    }];
    var TableInstance = Table({
        columns: col,
        tableContentId: 'dataMonitoredContent',
        tableId: (+new Date()) + '_table_body',
        sAjaxSource: UrlConfig.getMonitorsList,
        columnDefs: columnDefs
    });
    TableInstance.init();
});