/**
 * Created by pdeng on 16/3/23.
 */
define('static/scripts/index/goodsManager/goodsSelectList', ['sea-modules/bootstrap/bootstrap', 'sea-modules/jquery/cookie/jquery.cookie', 'static/scripts/index/common/timeFomate', 'static/scripts/index/message', 'static/scripts/index/datatable', 'static/scripts/index/common/urlConfig', 'sea-modules/jquery/dialog/jquery.dialog'], function (require, exports, module) {
    module.exports = function () {
        //获取所需组件依赖
        require('sea-modules/bootstrap/bootstrap');
        require('sea-modules/jquery/cookie/jquery.cookie');
        var timeFomate = require('static/scripts/index/common/timeFomate');
        var message = require('static/scripts/index/message');
        var Table = require('static/scripts/index/datatable');
        var UrlConfig = require('static/scripts/index/common/urlConfig');
        var token = $.cookie('bizData');
        $(document).on('click', '#tab-btn li', function () {
            $(this).addClass('active').siblings().removeClass('active');
            var n = $(this).index();
            console.log($('.table-responsive').html())
            $('.table-responsive').hide().eq(n).fadeIn(100);
            var placeTip = ['未出库vip卡号查询', '已出库vip卡号查询'];
            $('#vip-card').attr({'data-type': n, 'placeholder': placeTip[n]});
            $('#search-btn').attr('data-type', n);
        });
        $(document).on('click', '#search-btn', function () {
            var cardNumber = $.trim($('#vip-card').val());
            if (cardNumber.length > 10) {
                $('.form-error').text('您输入的卡号不正确').fadeIn(1000).fadeOut(1000);
                return false
            }
            var foo = $(this).attr('data-type');
            foo == '1' ? alreadyOutput('/admin/agents?token=' + token + '&isOutput=true&cardNumber=' + cardNumber) : willOutput('/admin/agents?token=' + token + '&isOutput=false&cardNumber=' + cardNumber);
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
        };
        var ButtonEvent = {
            production: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    $.get('../tmpl/outbound/outbound.html', function(tmpl) {
                        require('sea-modules/jquery/dialog/jquery.dialog');
                        $("#outbound_dialog").dialog({
                            title: "出库",
                            tmpl: tmpl,
                            onClose: function() {
                                $("#outbound_dialog").dialog("destroy");
                            },
                            render: function() {
                                $.getJSON('/admin/getCurrUserNextArea?token=' + token,function(res){
                                    for(var i=0;i<res.bizData.length;i++){
                                        $('#dep_provinces').append('<option value="'+ res.bizData[i].id +'">'+ res.bizData[i].name +'</option>')
                                    }
                                });

                            },
                            buttons: [{
                                text: "出库",
                                'class': "btn btn-primary",
                                click: function() {



                                }
                            }, {
                                text: "取消",
                                'class': "btn btn-primary",
                                click: function() {
                                    $("#outbound_dialog").dialog("destroy");
                                }
                            }]
                        });
                    })
                });
            },

            batchOutbound: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    if (!tableObj) {
                        return;
                    }

                    $.get('../tmpl/outbound/outbound.html', function(tmpl) {
                        require('sea-modules/jquery/dialog/jquery.dialog');
                        $("#outbound_dialog").dialog({
                            title: "批量出库",
                            tmpl: tmpl,
                            onClose: function() {
                                $("#outbound_dialog").dialog("destroy");
                            },
                            render: function() {


                            },
                            buttons: [{
                                text: "批量出库",
                                'class': "btn btn-primary",
                                click: function() {



                                }
                            }, {
                                text: "取消",
                                'class': "btn btn-primary",
                                click: function() {
                                    $("#outbound_dialog").dialog("destroy");
                                }
                            }]
                        });
                    })
                });
            }
        };
        require.async(['static/scripts/index/renderResource'], function(resource) {
            resource(ButtonEvent, token);
        });









    }


});