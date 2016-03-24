/**
 * Created by pdeng on 16/3/23.
 */
define('static/scripts/index/goodsManager/goodsSelectList', ['sea-modules/bootstrap/bootstrap', 'sea-modules/jquery/cookie/jquery.cookie', 'sea-modules/jquery/dialog/jquery.dialog', 'static/scripts/index/common/timeFomate', 'static/scripts/index/message', 'static/scripts/index/datatable', 'static/scripts/index/common/urlConfig'], function (require, exports, module) {
    module.exports = function () {
        //获取所需组件依赖
        require('sea-modules/bootstrap/bootstrap');
        require('sea-modules/jquery/cookie/jquery.cookie');
        require('sea-modules/jquery/dialog/jquery.dialog');
        var timeFomate = require('static/scripts/index/common/timeFomate');
        var message = require('static/scripts/index/message');
        var Table = require('static/scripts/index/datatable');
        var UrlConfig = require('static/scripts/index/common/urlConfig');
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
                    str = '已激活';
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