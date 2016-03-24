define(function (require, exports, module) {
    module.exports = function () {
        require('bootstrap');
        require('cookie');
        require('dialog');
        var token = $.cookie('bizData');
        var message = require('../message.js');
        var Table = require('../datatable.js');
        var UrlConfig = require('../common/urlConfig');
        var col = [{
            data: 'id',
            title: '<input type="checkbox">'
        }, {
            data: 'id',
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
                return '<input type="checkbox"  data-id="' + data + '"  />';
            },
            "aTargets": [0]
        }];

        var TableInstance = Table({
            columns: col,
            tableContentId: 'table_content',
            tableId: (+new Date()) + '_table_body',
            sAjaxSource: UrlConfig.getGoodsMange,
            columnDefs: columnDefs
        });
        TableInstance.init();
    }
});