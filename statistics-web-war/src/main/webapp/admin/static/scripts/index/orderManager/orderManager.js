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




        $.getJSON(UrlConfig.checkLogin, function (res) {
            console.log(res)
            if(res.roleType!="1"){
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
                    data: 'wechatPrice',
                    title: '微信售价'
                }, {
                    data: 'webPrice',
                    title: 'web售价'
                }, {
                    data: 'salePrice',
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
                    paging:false,
                    columns: colOverview,
                    tableContentId: 'table_contentOverview',
                    tableId: (+new Date()) + '_table_body',
                    columnDefs: columnDefsOverview,
                    sAjaxSource: UrlConfig.querySingleDepartmentIncome+'?token=' + token
                });
                TableInstanceOverview.init();
            }
        });




        $(document).on('click', '#order-tab-btn li', function () {
            $(this).addClass('active').siblings().removeClass('active');
            var curSelectedV = $('#orderType option:selected').val();
            var phoneNum = $('#phoneNum').val();
            var orderType = $(this).attr('orderType');
            if(orderType=="0"){
                willOutput(UrlConfig.queryOrderPageByConditions+"?token="+token+"&orderFrom="+curSelectedV + "&orderNoOrPhone="+phoneNum+"&handleState="+orderType);
                //// 全选
                //$('#selectall').on('click', function () {
                //    var that = this;
                //    $('.selNoOutbound[type="checkbox"]').each(function () {
                //        this.checked = that.checked;
                //    });
                //});
                //// 单选
                //window.clickChecked = function () {
                //    var selNoOutboundLength = $('.selNoOutbound[type="checkbox"]').length;
                //    var selNoOutboundCheckedLength = $('.selNoOutbound[type="checkbox"]:checked').length;
                //    if (selNoOutboundLength == selNoOutboundCheckedLength) {
                //        $('#selectall')[0].checked = true;
                //    } else {
                //        $('#selectall')[0].checked = false;
                //    }
                //};
            }else{
                willOutputAlready(UrlConfig.queryOrderPageByConditions+"?token="+token+"&orderFrom="+curSelectedV + "&orderNoOrPhone="+phoneNum+"&handleState=-1");
            }
        });
        $('#order-tab-btn li:eq(0)').click();
        $(document).on('click', '#orderSearch', function () {
            var curSelectedV = $('#orderType option:selected').val();
            var phoneNum = $('#phoneNum').val();
            var orderType = $('.nav-tabs li[class="active"]').attr('ordertype');
            if(orderType=="0"){
                willOutput(UrlConfig.queryOrderPageByConditions+"?token="+token+"&orderFrom="+curSelectedV + "&orderNoOrPhone="+phoneNum+"&handleState="+orderType);
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
                willOutputAlready(UrlConfig.queryOrderPageByConditions+"?token="+token+"&orderFrom="+curSelectedV + "&orderNoOrPhone="+phoneNum+"&handleState="+orderType);
            }
        });
        function willOutput(url) {
            var col = [{
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
            },{
                data: 'funs',
                title: '操作'
            }];

            var columnDefs = [ {
                "sClass": "center",
                "aTargets": [0]
            }, {
                "sClass": "center",
                "render": function (data, type, row) {
                    var str = '';
                    if (data == '0') {
                        str = '微信';
                    } else {
                        str = 'web';
                    }
                    return str;
                },
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
                "render": function (data, type, row) {
                    return timeFomate(data);
                },
                "aTargets": [6]
            },{
                "sClass": "center",
                "aTargets": [7],
                "render": function (data, type, row) {

                    var orderNo = row.orderNo;
                    console.log(orderNo);
                    return '<button type="button" id="'+ orderNo +'" class="btn btn-info"  onclick="settlement(this)">发货</button>';
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

             window.settlement = function(obj) {
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
                                             var phoneNum = $('#phoneNum').val();
                                             var orderType = $('.nav-tabs li[class="active"]').attr('ordertype');
                                             willOutput(UrlConfig.queryOrderPageByConditions+"?token="+token+"&orderFrom="+curSelectedV + "&orderNoOrPhone="+phoneNum+"&handleState="+orderType);
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
                "bVisible": false,
                "aTargets": [0]
            }, {
                "sClass": "center",
                "aTargets": [1]
            }, {
                "sClass": "center",
                "render": function (data, type, row) {
                    var str = '';
                    if (data == '0') {
                        str = '微信';
                    } else {
                        str = 'web';
                    }
                    return str;
                },
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
                "render": function (data, type, row) {
                    return timeFomate(data);
                },
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

        var deliveryDepartment = function (succCallback, id) {
            var selNoOutboundArr = [];
            $('.selNoOutbound[type="checkbox"]:checked').each(function () {
                selNoOutboundArr.push($(this).attr('data-id'));
            });
            $.ajax({
                type: 'post',
                url: UrlConfig.updateSendGoodsState+'?token=' + token,
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