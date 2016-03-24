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
        var token = $.cookie('bizData');
        console.info(token);
        $(document).on('click', '#tab-btn li', function () {
            $(this).addClass('active').siblings().removeClass('active');
            var n = $(this).index();
            $('.table-responsive').hide().eq(n).fadeIn(500);
            var placeTip = ['未出库vip卡号查询', '已出库vip卡号查询'];
            $('#vip-card').attr({'data-type': n, 'placeholder': placeTip[n]});
            $('#search-btn').attr('data-type', n);
        });
        $(document).on('click', '#search-btn', function () {
            var cardNumber = $.trim($('#vip-card').val());
            if (cardNumber == '') {
                $('.form-error').text('请输入卡号').fadeIn(300).fadeOut(300);
                return false
            }
            console.info(cardNumber);
            var foo = $(this).attr('data-type');
            foo == '0' ? alreadyOutput('/admin/agents?token=' + token + '&isOutput=false&cardNumber=' + cardNumber) : willOutput('/admin/agents?token=' + token + '&isOutput=true&cardNumber=' + cardNumber);
        });
        //未出库table
        willOutput(UrlConfig.getGoodsMange);
        //已出库table
        alreadyOutput(UrlConfig.getGoodsMangeOut);
        function willOutput(url) {
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
                sAjaxSource: url,
                columnDefs: columnDefs
            });
            TableInstance.init();
        }

        function alreadyOutput(url) {
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
            var columnDefs = [{
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
                        str = '未激活';
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
                sAjaxSource: url,
                columnDefs: columnDefs
            });
            TableInstance.init();
        }
    }


});