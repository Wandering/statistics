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
            willOutput(UrlConfig.queryOrderPageByConditions+"?token="+token+"&orderFrom="+curSelectedV + "&orderNoOrPhone="+phoneNum+"&handleState="+orderType);
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
            }, {
                data: 'handleState',
                title: '发货状态'
            }];
            var columnDefs = [{
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
                "aTargets": [8],
                "render": function (data, type, row) {
                    return '<button type="button">发货</button>';
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

        function alreadyOutput(url) {
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
                sAjaxSource: url,
                columnDefs: columnDefs
            });
            TableInstance.init();
        };




        var ButtonEvent = {
            production: function (elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function (e) {
                    if ($('.selNoOutbound[type="checkbox"]:checked').length == 0) {
                        message({
                            title: '温馨提示',
                            msg: '请至少选择一个出库货物',
                            type: 'alert'
                        });
                        return false;
                    }
                    //$.get('../tmpl/outbound/outbound.html', function (tmpl) {
                    //    require('dialog');
                    //    $("#outbound_dialog").dialog({
                    //        title: "出库",
                    //        tmpl: tmpl,
                    //        onClose: function () {
                    //            $("#outbound_dialog").dialog("destroy");
                    //        },
                    //        render: function () {
                    //            $.getJSON('/admin/getCurrUserNextArea?token=' + token, function (res) {
                    //                console.log(res.bizData[0].name)
                    //                for (var i = 0; i < res.bizData.length; i++) {
                    //                    $('#dep_provinces').append('<option simpleCode="' + res.bizData[i].simpleCode + '" value="' + res.bizData[i].id + '">' + res.bizData[i].name + '</option>')
                    //                }
                    //            });
                    //        },
                    //        buttons: [{
                    //            text: "出库",
                    //            'class': "btn btn-primary",
                    //            click: function () {
                    //                var vali = require('./outbound_from.js');
                    //                vali.validate(function (formArry) {
                    //                    productionDepartment(formArry, function (ret) {
                    //                        if ('0000000' === ret.rtnCode) {
                    //                            willOutput(UrlConfig.getGoodsMange);
                    //                            $("#outbound_dialog").dialog("destroy");
                    //                        } else {
                    //                            $("#outbound_dialog").dialog("destroy");
                    //                            message({
                    //                                title: '温馨提示',
                    //                                msg: ret.msg,
                    //                                type: 'alert',
                    //                                clickHandle: function () {
                    //                                    window.location.href = 'login.html';
                    //                                }
                    //                            });
                    //                        }
                    //                    });
                    //                });
                    //            }
                    //        }, {
                    //            text: "取消",
                    //            'class': "btn btn-primary",
                    //            click: function () {
                    //                $("#outbound_dialog").dialog("destroy");
                    //            }
                    //        }]
                    //    });
                    //})
                });
            },

            batchOutbound: function (elementId) {

                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function (e) {
                    //$.get('../tmpl/outbound/outbound_batch.html', function (tmpl) {
                    //    require('dialog');
                    //    $("#dep_provinces_batch").dialog({
                    //        title: "批量出库",
                    //        tmpl: tmpl,
                    //        onClose: function () {
                    //            $("#dep_provinces_batch").dialog("destroy");
                    //        },
                    //        render: function () {
                    //            $.getJSON('/admin/getCurrUserNextArea?token=' + token, function (res) {
                    //                console.log(res.bizData[0].name)
                    //                for (var i = 0; i < res.bizData.length; i++) {
                    //                    $('#dep_provinces_batch').append('<option simpleCode="' + res.bizData[i].simpleCode + '" value="' + res.bizData[i].id + '">' + res.bizData[i].name + '</option>')
                    //                }
                    //            });
                    //            $('body').on('click', '#card-area-btn', function () {
                    //                var outboundBatchNum = $.trim($('#outbound_batch_num').val());
                    //                if (outboundBatchNum == '' || outboundBatchNum == '0') {
                    //                    tip($('#dep_provinces_batch').parent().parent(), '请输入出库数量');
                    //                    return;
                    //                }
                    //                $.getJSON('/admin/outPutCardNumber?rows=' + outboundBatchNum, function (res) {
                    //                    console.log(res)
                    //
                    //
                    //                    if (res.rtnCode == "0000000") {
                    //                        $('#card-interval').show();
                    //                        var cardStart = res.bizData.start,
                    //                            cardEnd = res.bizData.end;
                    //                        $('#card-start').text(cardStart);
                    //                        $('#card-end').text(cardEnd);
                    //                    }
                    //
                    //                })
                    //
                    //
                    //            })
                    //
                    //        },
                    //        buttons: [{
                    //            text: "批量出库",
                    //            'class': "btn btn-primary",
                    //            click: function () {
                    //                var vali = require('./outbound_batch_from.js');
                    //                vali.validate(function (formArry) {
                    //                    productionBatchDepartment(formArry, function (ret) {
                    //                        console.log(ret)
                    //                        if ('0000000' === ret.rtnCode) {
                    //                            willOutput(UrlConfig.getGoodsMange);
                    //                            $("#dep_provinces_batch").dialog("destroy");
                    //                        } else {
                    //                            $("#dep_provinces_batch").dialog("destroy");
                    //                            message({
                    //                                title: '温馨提示',
                    //                                msg: ret.msg,
                    //                                type: 'alert',
                    //                                clickHandle: function () {
                    //                                    window.location.href = 'login.html';
                    //                                }
                    //                            });
                    //                        }
                    //                    });
                    //                });
                    //
                    //            }
                    //        }, {
                    //            text: "取消",
                    //            'class': "btn btn-primary",
                    //            click: function () {
                    //                $("#dep_provinces_batch").dialog("destroy");
                    //            }
                    //        }]
                    //    });
                    //})
                });
            },

            flowarea: function (elementId) {
                $('#' + elementId).hide();
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function (e) {
                    //$.get('../tmpl/outbound/flow_area.html', function (tmpl) {
                    //    require('dialog');
                    //    $("#flow_area_dialog").dialog({
                    //        title: "流向地查询",
                    //        tmpl: tmpl,
                    //        onClose: function () {
                    //            $("#flow_area_dialog").dialog("destroy");
                    //        },
                    //        render: function () {
                    //            $.getJSON('/admin/getCurrUserNextArea?token=' + token, function (res) {
                    //                console.log(res)
                    //                for (var i = 0; i < res.bizData.length; i++) {
                    //                    $('#flow-area-list').append('<li><label simpleCode="' + res.bizData[i].simpleCode + '" id="' + res.bizData[i].id + '"> <input type="checkbox" >' + res.bizData[i].name + '</label></li>')
                    //                }
                    //            });
                    //        },
                    //        buttons: [{
                    //            text: "确定",
                    //            'class': "btn btn-primary",
                    //            click: function () {
                    //                var vali = require('./outbound_flow_area.js');
                    //                vali.validate(function (formArry) {
                    //                    flowAreaDepartment(formArry, function (ret) {
                    //                        console.log(ret)
                    //                        if ('0000000' === ret.rtnCode) {
                    //                            var flowAreaArr = [];
                    //                            console.log($('#flow-area-list [type="checkbox"]:checked').parent().attr('simpleCode'))
                    //                            $('#flow-area-list [type="checkbox"]:checked').each(function () {
                    //                                flowAreaArr.push($(this).parent().attr('simpleCode'));
                    //                            });
                    //                            console.log(flowAreaArr)
                    //                            alreadyOutput('/admin/agents?token=' + token + '&isOutput=true&area=' + flowAreaArr.join(','));
                    //                            $("#flow_area_dialog").dialog("destroy");
                    //                        } else {
                    //                            $("#flow_area_dialog").dialog("destroy");
                    //                            message({
                    //                                title: '温馨提示',
                    //                                msg: ret.msg,
                    //                                type: 'alert',
                    //                                clickHandle: function () {
                    //                                    window.location.href = 'login.html';
                    //                                }
                    //                            });
                    //                        }
                    //                    });
                    //                });
                    //            }
                    //
                    //        }, {
                    //            text: "取消",
                    //            'class': "btn btn-primary",
                    //            click: function () {
                    //                $("#flow_area_dialog").dialog("destroy");
                    //            }
                    //        }]
                    //    });
                    //})
                });
            }


        };


        require.async('../renderResource', function (resource) {
            resource(ButtonEvent, token);
        });


        function tip(ele, str) {
            var errorLable = ele.find('p');
            errorLable.html(str);
            errorLable.show(500);
            setTimeout(function () {
                errorLable.hide(500);
            }, 2000)
        }


    }


});