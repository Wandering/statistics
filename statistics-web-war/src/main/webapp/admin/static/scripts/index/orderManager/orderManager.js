/**
 * Created by pdeng on 16/3/23.
 */
define(function (require, exports, module) {
    module.exports = function () {
        //获取所需组件依赖
        require('bootstrap');
        require('cookie');
        var timeFomate = require('../common/timeFomate.js');
        var message = require('../message.js');
        var Table = require('../datatable.js');

        var UrlConfig = require('../common/urlConfig');
        var token = $.cookie('bizData');
        var cookieJson = JSON.parse($.cookie('userInfo'));




        var colOverview = [{
            data: 'id',
            title: ''
        }, {
            data: 'departmentName',
            title: '代理商'
        }, {
            data: 'departmentLevel',
            title: '代理商层级'
        }, {
            data: 'wechatPrice',
            title: '微信售价'
        }, {
            data: 'webPrice',
            title: 'web售价'
        }, {
            data: 'salePrice',
            title: '出厂价'
        }, {
            data: 'wechatVolume',
            title: '微信销量'
        }, {
            data: 'webVolume',
            title: 'web销量'
        }, {
            data: 'netIncome',
            title: '网上收益'
        }, {
            data: 'notSettled',
            title: '未结算'
        }, {
            data: 'settled',
            title: '已结算'
        }];
        var columnDefsOverview = [{
            "bVisible": false,
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
            "sClass": "center",
            "aTargets": [4]
        }, {
            "sClass": "center",
            "aTargets": [5]
        }, {
            "sClass": "center",
            "aTargets": [6]
        }, {
            "sClass": "center",
            "aTargets": [7]
        }, {
            "sClass": "center",
            "aTargets": [8]
        }, {
            "sClass": "center",
            "aTargets": [9]
        }, {
            "sClass": "center",
            "aTargets": [10]
        }];
        var TableInstanceOverview = Table({
            columns: colOverview,
            tableContentId: 'table_contentOverview',
            tableId: (+new Date()) + '_table_body',
            columnDefs: columnDefsOverview,
            sAjaxSource: UrlConfig.querySingleDepartmentIncome
        });
        TableInstanceOverview.init();


        $(document).on('click', '#order-tab-btn li', function () {
            $(this).addClass('active').siblings().removeClass('active');
            var curSelectedV = $('#orderType option:selected').val();
            var phoneNum = $('#phoneNum').val();
            var orderType = $(this).attr('orderType');
            if(orderType=="0"){
                //willOutput(UrlConfig.queryOrderPageByConditions+"?token="+token+"&orderFrom="+curSelectedV + "&orderNoOrPhone="+phoneNum+"&handleState="+orderType);
                willOutput(UrlConfig.queryOrderPageByConditions+"?token="+token+"&orderFrom="+curSelectedV + "&orderNoOrPhone="+phoneNum+"&handleState=-1");
                // 全选
                $('#selectall').on('click', function () {
                    var that = this;
                    $('.selNoOutbound[type="checkbox"]').each(function () {
                        this.checked = that.checked;
                    });
                });
                // 单选
                window.clickChecked = function () {
                    var selNoOutboundLength = $('.selNoOutbound[type="checkbox"]').length;
                    var selNoOutboundCheckedLength = $('.selNoOutbound[type="checkbox"]:checked').length;
                    if (selNoOutboundLength == selNoOutboundCheckedLength) {
                        $('#selectall')[0].checked = true;
                    } else {
                        $('#selectall')[0].checked = false;
                    }
                };
            }else{
                willOutputAlready(UrlConfig.queryOrderPageByConditions+"?token="+token+"&orderFrom="+curSelectedV + "&orderNoOrPhone="+phoneNum+"&handleState=-1");
            }



        });
        $('#order-tab-btn li:eq(0)').click();
        $(document).on('click', '#orderSearch', function () {
            var curSelectedV = $('#orderType option:selected').val();
            var phoneNum = $('#phoneNum').val();
            var orderType = $('.nav-tabs li[class="active"]').attr('ordertype');
            willOutput(UrlConfig.queryOrderPageByConditions+"?token="+token+"&orderFrom="+curSelectedV + "&orderNoOrPhone="+phoneNum+"&handleState="+orderType);
        });
        function willOutput(url) {
            var col = [{
                data: 'id',
                title: '<input type="checkbox" id="selectall">'
            }, {
                data: 'orderNo',
                title: '订单编号'
            }, {
                data: 'channle',
                title: '订单来源'
            }, {
                data: 'userName',
                title: '用户名称'
            }, {
                data: 'phoneNum',
                title: '联系电话'
            }, {
                data: 'registAddress',
                title: '注册地址'
            }, {
                data: 'goodsCount',
                title: '数量'
            }, {
                data: 'createDate',
                title: '订单时间'
            }];
            var columnDefs = [{
                "sClass": "center",
                "sWidth": "30px",
                "render": function (data, type, row) {
                    return '<input class="selNoOutbound" onclick="clickChecked()" type="checkbox"  data-id="' + data + '"  />';
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
                "sClass": "center",
                "aTargets": [4]
            }, {
                "sClass": "center",
                "aTargets": [5]
            }, {
                "sClass": "center",
                "aTargets": [6]
            }, {
                "sClass": "center",
                "aTargets": [7]
            }];

            var TableInstance = Table({
                columns: col,
                tableContentId: 'table_content',
                tableId: (+new Date()) + '_table_body',
                columnDefs: columnDefs,
                sAjaxSource: url
            });
            TableInstance.init();
        }











        function willOutputAlready(url) {
            var col = [{
                data: 'id',
                title: ''
            }, {
                data: 'orderNo',
                title: '订单编号'
            }, {
                data: 'channle',
                title: '订单来源'
            }, {
                data: 'userName',
                title: '用户名称'
            }, {
                data: 'phoneNum',
                title: '联系电话'
            }, {
                data: 'registAddress',
                title: '注册地址'
            }, {
                data: 'goodsCount',
                title: '数量'
            }, {
                data: 'createDate',
                title: '订单时间'
            }];
            var columnDefs = [{
                "sClass": "center",
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
                "sClass": "center",
                "aTargets": [4]
            }, {
                "sClass": "center",
                "aTargets": [5]
            }, {
                "sClass": "center",
                "aTargets": [6]
            }, {
                "sClass": "center",
                "aTargets": [7]
            }];

            var TableInstance = Table({
                columns: col,
                tableContentId: 'table_content',
                tableId: (+new Date()) + '_table_body',
                columnDefs: columnDefs,
                sAjaxSource: url
            });
            TableInstance.init();
        }






        var ButtonEvent = {
            delivery: function (elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function (e) {
                    if ($('.selNoOutbound[type="checkbox"]:checked').length == 0) {
                        message({
                            title: '温馨提示',
                            msg: '请至少选择一个发货项',
                            type: 'alert'
                        });
                        return false;
                    }
                    $.get('../tmpl/orderTmpl/order.html', function (tmpl) {
                        require('dialog');
                        $("#order_dialog").dialog({
                            title: "发货",
                            tmpl: tmpl,
                            onClose: function () {
                                $("#order_dialog").dialog("destroy");
                            },
                            render: function () {

                            },
                            buttons: [{
                                text: "确定发货",
                                'class': "btn btn-primary",
                                click: function () {

                                }
                            }, {
                                text: "取消",
                                'class': "btn btn-primary",
                                click: function () {
                                    $("#order_dialog").dialog("destroy");
                                }
                            }]
                        });
                    })
                });
            }
        };


        require.async('../renderResource', function (resource) {
            resource(ButtonEvent, token);
        });





    }


});