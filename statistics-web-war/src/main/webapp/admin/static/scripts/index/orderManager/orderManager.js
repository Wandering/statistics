/**
 * Created by pdeng on 16/3/23.
 */
define(function (require, exports, module) {
    module.exports = function () {
        //获取所需组件依赖
        require('bootstrap');
        require('cookie');
        var Tool = require('../tools');
        var timeFomate = require('../common/timeFomate.js');
        var message = require('../message.js');
        var Table = require('../datatable.js');

        var UrlConfig = require('../common/urlConfig');
        var token = $.cookie('bizData');
        var cookieJson = JSON.parse($.cookie('userInfo'));

        require('datetimepicker');
        require('datetimepickerCN');
        $('#start_date').datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            weekStart: 1,
            autoclose: true,
            startView: 2,
            minView: 2,
            forceParse: 0
        }).on('changeDate', function (evl) {
            var startDate = $('#start_date').val();
            $('#end_date').datetimepicker('setStartDate', startDate);
            setTimeout(function () {
                var endDate = Tool.timeFormat(new Date(+new Date(startDate) + 365 * 24 * 60 * 60 * 1000), 'yyyy-MM-dd');
                $('#end_date').datetimepicker('setEndDate', endDate);
            }, 100);
        });
        $('#end_date').datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            weekStart: 1,
            autoclose: true,
            startView: 2,
            minView: 2,
            forceParse: 0
        }).on('changeDate', function (evl) {
            var endDate = $('#end_date').val();
            $('#start_date').datetimepicker('setEndDate', endDate);
            setTimeout(function () {
                var startDate = Tool.timeFormat(new Date(+new Date(endDate) - 365 * 24 * 60 * 60 * 1000), 'yyyy-MM-dd');
                $('#start_date').datetimepicker('setStartDate', startDate);
            }, 100);
        });


        $.getJSON(UrlConfig.checkLogin, function (res) {
            console.log(res.roleType)
            if (res.roleType != "1") {
                // 总览
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
                    data: 'productName',
                    title: '种类'
                }, {
                    data: 'salePrice',
                    title: '售价'
                }, {
                    data: 'pickupPrice',
                    title: '出厂价'
                }, {
                    data: 'wechatSaleCount',
                    title: '微信销量'
                }, {
                    data: 'webSaleCount',
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
                    "aTargets": [0],
                    "sClass": "center line35"
                }, {
                    "aTargets": [1],
                    "sClass": "center line35"
                }, {
                    "aTargets": [2],
                    "sClass": "center line35"
                }, {
                    "sClass": "center",
                    "aTargets": [3],
                    "render": function (data, type, row) {
                        var listHtml = '';
                        for (var i = 0; i < row.productSales.length; i++) {
                            listHtml += '<div>' + row.productSales[i].productName + '</div>';
                        }
                        return listHtml;
                    }

                }, {
                    "sClass": "center",
                    "aTargets": [4],
                    "render": function (data, type, row) {
                        var listHtml = '';
                        for (var i = 0; i < row.productSales.length; i++) {
                            listHtml += '<div>' + row.productSales[i].salePrice + '</div>';
                        }
                        return listHtml;
                    }
                }, {
                    "sClass": "center",
                    "aTargets": [5],
                    "render": function (data, type, row) {
                        var listHtml = '';
                        for (var i = 0; i < row.productSales.length; i++) {
                            listHtml += '<div>' + row.productSales[i].pickupPrice + '</div>';
                        }
                        return listHtml;
                    }
                }, {
                    "sClass": "center",
                    "aTargets": [6],
                    "render": function (data, type, row) {
                        var listHtml = '';
                        for (var i = 0; i < row.productSales.length; i++) {
                            listHtml += '<div>' + row.productSales[i].wechatSaleCount + '</div>';
                        }
                        return listHtml;
                    }
                }, {
                    "sClass": "center",
                    "aTargets": [7],
                    "render": function (data, type, row) {
                        var listHtml = '';
                        for (var i = 0; i < row.productSales.length; i++) {
                            listHtml += '<div>' + row.productSales[i].webSaleCount + '</div>';
                        }
                        return listHtml;
                    }
                }, {
                    "sClass": "center line35",
                    "aTargets": [8]
                }, {
                    "sClass": "center line35",
                    "aTargets": [9]
                }, {
                    "sClass": "center line35",
                    "aTargets": [10]
                }];
                var TableInstanceOverview = Table({
                    paging: false,
                    columns: colOverview,
                    tableContentId: 'table_contentOverview',
                    tableId: (+new Date()) + '_table_body',
                    columnDefs: columnDefsOverview,
                    sAjaxSource: UrlConfig.querySingleDepartmentIncome + '?token=' + token
                });
                TableInstanceOverview.init();
            }
        });


        $(document).on('click', '#order-tab-btn li', function () {
            $(this).addClass('active').siblings().removeClass('active');
            var orderType = $(this).attr('orderType');
            var curSelectedV = $('#orderType option:selected').val();
            var orderTypePriceV = $('#orderTypePrice option:selected').val();
            var phoneNum = $('#phoneNum').val();
            var start_date = $('#start_date').val();
            var end_date = $('#end_date').val();
            var timesStartDate = "";
            if (start_date != "") {
                timesStartDate = Date.parse(new Date(start_date));
            } else {
                timesStartDate = "-1";
            }
            var timesEndDate = "";
            if (start_date != "") {
                timesEndDate = Date.parse(new Date(end_date));
            } else {
                timesEndDate = "-1";
            }
            if (orderType == "0") {
                willOutput(UrlConfig.queryOrderPageByConditions + "?token=" + token + "&orderFrom=" + curSelectedV + "&orderNoOrPhone=" + phoneNum + "&handleState=" + orderType + "&startDate=" + timesStartDate + "&endDate=" + timesEndDate + "&productType=" + orderTypePriceV);
            } else {
                willOutputAlready(UrlConfig.queryOrderPageByConditions + "?token=" + token + "&orderFrom=" + curSelectedV + "&orderNoOrPhone=" + phoneNum + "&handleState=" + orderType + "&startDate=" + timesStartDate + "&endDate=" + timesEndDate + "&productType=" + orderTypePriceV);
            }
        });
        willOutput(UrlConfig.queryOrderPageByConditions + "?token=" + token + "&orderFrom=-1&orderNoOrPhone=&handleState=0&startDate=-1&endDate=-1&productType=-1");
        $(document).on('click', '#orderSearch', function () {
            var curSelectedV = $('#orderType option:selected').val();
            var orderTypePriceV = $('#orderTypePrice option:selected').val();
            var phoneNum = $('#phoneNum').val();
            var start_date = $('#start_date').val();
            var end_date = $('#end_date').val();
            var orderType = $('.nav-tabs li[class="active"]').attr('ordertype');
            var timesStartDate = "";
            if (start_date != "") {
                timesStartDate = Date.parse(new Date(start_date));
            } else {
                timesStartDate = "-1";
            }
            var timesEndDate = "";
            if (start_date != "") {
                timesEndDate = Date.parse(new Date(end_date));
            } else {
                timesEndDate = "-1";
            }
            if (orderType == "0") {
                willOutput(UrlConfig.queryOrderPageByConditions + "?token=" + token + "&orderFrom=" + curSelectedV + "&orderNoOrPhone=" + phoneNum + "&handleState=" + orderType + "&startDate=" + timesStartDate + "&endDate=" + timesEndDate + "&productType=" + orderTypePriceV);
            } else {
                willOutputAlready(UrlConfig.queryOrderPageByConditions + "?token=" + token + "&orderFrom=" + curSelectedV + "&orderNoOrPhone=" + phoneNum + "&handleState=" + orderType + "&startDate=" + timesStartDate + "&endDate=" + timesEndDate + "&productType=" + orderTypePriceV);
            }
        });

        function willOutput(url) {
            var col = [
                {
                    data: 'index',
                    title: '序号'
                }, {
                    data: 'orderNo',
                    title: '订单编号'
                }, {
                    data: 'productType',
                    title: '种类'
                }, {
                    data: 'handleState',
                    title: '状态'
                }, {
                    data: 'channle',
                    title: '来源'
                }, {
                    data: 'userName',
                    title: '用户名称'
                }, {
                    data: 'phoneNum',
                    title: '联系电话'
                }, {
                    data: 'registAddress',
                    title: '收货地址'
                }, {
                    data: 'goodsCount',
                    title: '数量'
                }, {
                    data: 'createDate',
                    title: '订单时间'
                }, {
                    data: 'funs',
                    title: '操作'
                }];

            var columnDefs = [
                {
                    "sClass": "center",
                    "aTargets": [0]
                }, {
                    "sClass": "center",
                    "aTargets": [1]
                }, {
                    "sClass": "center",
                    "aTargets": [2],
                    "render": function (data, type, row) {
                        var str = '';
                        var productType = row.productType;
                        switch (productType) {
                            case 1 :
                                str = '金榜登科';
                                break;
                            case 2 :
                                str = '状元及第';
                                break;
                            case 3 :
                                str = '金榜题名';
                                break;
                            default:
                        }
                        return str;
                    }
                }, {
                    "sClass": "center",
                    "aTargets": [3],
                    "render": function (data, type, row) {
                        var str = '';
                        if (row.handleState == '0') {
                            str = '未发货';
                        } else {
                            str = '已发货';
                        }
                        return str;
                    }
                }, {
                    "sClass": "center",
                    "aTargets": [4],
                    "render": function (data, type, row) {
                        var str = '';
                        if (row.channle == '0') {
                            str = '微信';
                        } else {
                            str = 'web';
                        }
                        return str;
                    }
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
                    "aTargets": [9],
                    "render": function (data, type, row) {
                        return timeFomate(data);
                    }
                }, {
                    "sClass": "center",
                    "aTargets": [10],
                    "render": function (data, type, row) {
                        var orderNo = row.orderNo;
                        return '<button type="button" id="' + orderNo + '" class="btn btn-info"  onclick="settlement(this)">发货</button>';
                    }
                }];
            var TableInstance = Table({
                columns: col,
                tableContentId: 'table_content',
                tableId: (+new Date()) + '_table_body',
                columnDefs: columnDefs,
                sAjaxSource: url
            });
            TableInstance.init();

            window.settlement = function (obj) {
                console.log(obj.getAttribute('id'))
                var id = obj.getAttribute('id');
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
                            text: "确定",
                            'class': "btn btn-primary",
                            click: function () {

                                $.ajax({
                                    type: 'POST',
                                    url: UrlConfig.updateSendGoodsState,
                                    contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                                    data: {
                                        orderNo: id
                                    },
                                    dataType: 'json',
                                    success: function (data) {
                                        console.log(data)
                                        if (data.rtnCode == "0000000") {
                                            var curSelectedV = $('#orderType option:selected').val();
                                            var orderTypePriceV = $('#orderTypePrice option:selected').val();
                                            var phoneNum = $('#phoneNum').val();
                                            var start_date = $('#start_date').val();
                                            var end_date = $('#end_date').val();
                                            var orderType = $('.nav-tabs li[class="active"]').attr('ordertype');
                                            var timesStartDate = "";
                                            if (start_date != "") {
                                                timesStartDate = Date.parse(new Date(start_date));
                                            } else {
                                                timesStartDate = "-1";
                                            }
                                            var timesEndDate = "";
                                            if (start_date != "") {
                                                timesEndDate = Date.parse(new Date(end_date));
                                            } else {
                                                timesEndDate = "-1";
                                            }
                                            willOutput(UrlConfig.queryOrderPageByConditions + "?token=" + token + "&orderFrom=" + curSelectedV + "&orderNoOrPhone=" + phoneNum + "&handleState=" + orderType + "&startDate=" + timesStartDate + "&endDate=" + timesEndDate + "&productType=" + orderTypePriceV);
                                            $("#order_dialog").dialog("destroy");
                                        }
                                    },
                                    beforeSend: function (xhr) {
                                    },
                                    error: function (data) {
                                        alert(data.msg)
                                    }
                                });
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
            };
        }


        function willOutputAlready(url) {
            var col = [
                {
                    data: 'index',
                    title: '序号'
                }, {
                    data: 'orderNo',
                    title: '订单编号'
                }, {
                    data: 'productType',
                    title: '种类'
                }, {
                    data: 'handleState',
                    title: '状态'
                }, {
                    data: 'channle',
                    title: '来源'
                }, {
                    data: 'userName',
                    title: '用户名称'
                }, {
                    data: 'phoneNum',
                    title: '联系电话'
                }, {
                    data: 'registAddress',
                    title: '收货地址'
                }, {
                    data: 'goodsCount',
                    title: '数量'
                }, {
                    data: 'createDate',
                    title: '订单时间'
                }];
            var columnDefs = [
                {
                    "sClass": "center",
                    "aTargets": [0]
                }, {
                    "sClass": "center",
                    "aTargets": [1]
                }, {
                    "sClass": "center",
                    "aTargets": [2],
                    "render": function (data, type, row) {
                        var str = '';
                        var productType = row.productType;
                        switch (productType) {
                            case 1 :
                                str = '金榜登科';
                                break;
                            case 2 :
                                str = '状元及第';
                                break;
                            case 3 :
                                str = '金榜题名';
                                break;
                            default:
                        }
                        return str;
                    }
                }, {
                    "sClass": "center",
                    "aTargets": [3],
                    "render": function (data, type, row) {
                        var str = '';
                        if (row.handleState == '0') {
                            str = '未发货';
                        } else {
                            str = '已发货';
                        }
                        return str;
                    }
                }, {
                    "sClass": "center",
                    "aTargets": [4],
                    "render": function (data, type, row) {
                        var str = '';
                        if (row.channle == '0') {
                            str = '微信';
                        } else {
                            str = 'web';
                        }
                        return str;
                    }
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
                    "aTargets": [9],
                    "render": function (data, type, row) {
                        return timeFomate(data);
                    }
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

        var deliveryDepartment = function (succCallback, id) {
            var selNoOutboundArr = [];
            $('.selNoOutbound[type="checkbox"]:checked').each(function () {
                selNoOutboundArr.push($(this).attr('data-id'));
            });
            $.ajax({
                type: 'post',
                url: UrlConfig.updateSendGoodsState + '?token=' + token,
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                data: {
                    orderNo: selNoOutboundArr.join(","),
                },
                dataType: 'json',
                success: function (data) {
                    console.log(data);
                    succCallback(data);
                },
                beforeSend: function (xhr) {
                },
                error: function (data) {

                }
            });
        };
    }
});