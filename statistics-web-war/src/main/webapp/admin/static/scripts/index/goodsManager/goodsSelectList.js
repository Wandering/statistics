/**
 * Created by pdeng on 16/3/23.
 */
define(function (require, exports, module) {
    module.exports = function () {
        //获取所需组件依赖
        require('bootstrap');
        require('cookie');
        require('dialog');
        var timeFomate = require('../common/timeFomate.js');
        var message = require('../message.js');
        var Table = require('../datatable.js');
        var UrlConfig = require('../common/urlConfig');
        //未出库table
        var col = [{
            data: 'id',
            title: '<input type="checkbox">'
        }, {
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
            title: '入库日期'
        }];
        var columnDefs = [{
            "sClass": "center",
            "sWidth": "30px",
            "render": function (data, type, row) {
                return '<input class="" type="checkbox"  data-id="' + data + '"  />';
            },
            "aTargets": [0]
        }, {
            "sClass": "center",
            "aTargets": [1]
        }, {
            "sClass": "center",
            "aTargets": [2]
        }, {
            "sClass": "center",
            "aTargets": [3]
        }, {
            "render": function (data, type, row) {
                return timeFomate(data);
            },
            "sClass": "center",
            "aTargets": [4]
        }];
        var TableInstance = Table({
            columns: col,
            tableContentId: 'table_content',
            tableId: (+new Date()) + '_table_body',
            sAjaxSource: UrlConfig.getGoodsMange,
            columnDefs: columnDefs
        });
        TableInstance.init();

        //已出库table
        var col = [{
            data: 'cardNumber',
            title: 'VIP卡号'
        }, {
            data: 'flow',
            title: '流向地'
        }, {
            data: 'inputDate',
            title: '激活日期'
        }, {
            data: 'outputDate',
            title: '出库日期'
        }];
        var columnDefs = [ {
            "sClass": "center",
            "aTargets": [0]
        }, {
            "sClass": "center",
            "aTargets": [1]
        }, {
            "sClass": "center",
            "render": function (data, type, row) {
                var str = '';
                if (data == '0' || data == null) {
                    str = '未出库';
                } else {
                    str = timeFomate(data);
                }
                return str;
            },
            "aTargets": [2]
        }, {
            "render": function (data, type, row) {
                return timeFomate(data);
            },
            "sClass": "center",
            "aTargets": [3]
        }];
        var TableInstance = Table({
            columns: col,
            tableContentId: 'table_content2',
            tableId: (+new Date()) + '_table_body',
            sAjaxSource: UrlConfig.getGoodsMangeOut,
            columnDefs: columnDefs
        });
        TableInstance.init();
    };


});