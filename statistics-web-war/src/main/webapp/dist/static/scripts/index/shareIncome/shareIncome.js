define('static/scripts/index/shareIncome/shareIncome', ['sea-modules/bootstrap/bootstrap', 'sea-modules/jquery/cookie/jquery.cookie', 'sea-modules/jquery/dialog/jquery.dialog', 'static/scripts/index/common/ajax', 'static/scripts/index/common/timeFomate', 'static/scripts/index/message', 'static/scripts/index/datatable', 'static/scripts/index/common/urlConfig'], function (require, exports, module) {
    module.exports = function () {
        alert(8)
        //获取所需组件依赖
        require('sea-modules/bootstrap/bootstrap');
        require('sea-modules/jquery/cookie/jquery.cookie');
        require('sea-modules/jquery/dialog/jquery.dialog');
        var ajaxFun = require('static/scripts/index/common/ajax');
        var timeFomate = require('static/scripts/index/common/timeFomate');
        var message = require('static/scripts/index/message');
        var Table = require('static/scripts/index/datatable');
        var token = $.cookie('bizData');
        var UrlConfig = require('static/scripts/index/common/urlConfig');

        getEarningsList(UrlConfig.queryAllUserIncome+"?areaCode=-1&areaType=-1&account=&token="+token);
        function getEarningsList(url) {
            var col = [
                {
                    data: 'userName',
                    title: '用户名'
                },
                {
                    data: 'account',
                    title: '账号'
                },
                {
                    data: 'phoneNum',
                    title: '联系电话'
                },
                {
                    data: 'registAddress',
                    title: '注册地'
                },
                {
                    data: 'firstLevelCount',
                    title: '一级奖励数量'
                },
                {
                    data: 'secondLevelCount',
                    title: '二级奖励数量'
                },
                {
                    data: 'allIncome',
                    title: '总收益'
                },
                {
                    data: 'notSettled',
                    title: '未结算'
                },
                {
                    data: 'settled',
                    title: '已结算'
                },
                {
                    data: 'details',
                    title: '详情'
                },
                {
                    data: 'funs',
                    title: '操作'
                }
            ];
            var columnDefs = [
                {
                    "sClass": "center",
                    "aTargets": [0]
                },
                {
                    "sClass": "center",
                    "aTargets": [1]
                },
                {
                    "sClass": "center",
                    "aTargets": [2]
                },
                {
                    "sClass": "center",
                    "aTargets": [3]
                },
                {
                    "sClass": "center",
                    "aTargets": [4]
                },
                {
                    "sClass": "center",
                    "aTargets": [5]
                },
                {
                    "sClass": "center",
                    "aTargets": [6]
                },
                {
                    "sClass": "center",
                    "aTargets": [7]
                },
                {
                    "sClass": "center",
                    "aTargets": [8]
                },
                {
                    "sClass": "center",
                    "aTargets": [9],
                    "render": function (data, type, row) {
                        return '<a href="javascript:void(0);" class="btn btn-links"  onclick=\"shareIncome('+ row.userId +')\">结算记录</a><a href="javascript:void(0);" class="btn btn-links" onclick=\"shareIncomeDetail('+ row.userId +')\">收益详情</a>';
                    }
                },
                {
                    "sClass": "center",
                    "aTargets": [10],
                    "render": function (data, type, row) {
                        return '<button type="button" class="btn btn-info"  onclick=\"settlement('+ row.userId +')\">结算</button>';
                    }
                }
            ];

            var TableInstance = Table({
                columns: col,
                tableContentId: 'shareManagerContent',
                tableId: (+new Date()) + '_table_body',
                sAjaxSource: url,
                columnDefs: columnDefs
            });
            TableInstance.init();
        }

        window.shareIncome = function(id){
            $.get('../tmpl/shareIncome/shareIncome.html', function (tmpl) {
                require('sea-modules/jquery/dialog/jquery.dialog');
                $("#shareIncome_dialog").dialog({
                    title: "结算记录",
                    tmpl: tmpl,
                    onClose: function () {
                        $("#shareIncome_dialog").dialog("destroy");
                    },
                    render: function () {
                        $.ajax({
                            type: 'GET',
                            url: UrlConfig.querySettlementRecordsByDepartmentCode + '?token=' + token,
                            contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                            data: {
                                departmentCode: id,
                            },
                            dataType: 'json',
                            success: function (data) {
                                console.log(data);
                                var list = '';
                                var dataList = data.bizData.list;
                                for (var i = 0; i < dataList.length; i++) {
                                    list += '<tr><td class="center">' + timeFomate(dataList[i].requestTime) + '</td><td class="center">' + dataList[i].money + '</td></tr>'
                                }
                                $('#shareIncome-list').append(list);
                            },
                            beforeSend: function (xhr) {
                            },
                            error: function (data) {

                            }
                        });
                    },
                    buttons: [
                        {
                            text: "确定结算",
                            'class': "btn btn-primary",
                            click: function () {
                                var selNoOutboundArr = [];
                                $('.selNoOutbound[type="radio"]:checked').each(function () {
                                    selNoOutboundArr.push($(this).attr('data-id'));
                                });
                                var money = $('#money').val();
                                $.ajax({
                                    type: 'POST',
                                    url: UrlConfig.settlementByDepartmentCode,
                                    contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                                    data: {
                                        departmentCode: selNoOutboundArr.join(","),
                                        money:money
                                    },
                                    dataType: 'json',
                                    success: function (data) {
                                        console.log(data)

                                        //if(data.rtnCode=="0000000"){
                                        //    console.log(data);
                                        //    getRecordList(UrlConfig.queryAllDepartmentIncome + "?areaCode=-1&areaType=-1&account=&token=" + token);
                                        //    $("#settlement_dialog").dialog("destroy");
                                        //}

                                    },
                                    beforeSend: function (xhr) {
                                    },
                                    error: function (data) {
                                    }
                                });
                            }
                        },
                        {
                            text: "取消",
                            'class': "btn btn-primary",
                            click: function () {
                                $("#shareIncome_dialog").dialog("destroy");
                            }
                        }]
                });
            })
        };


        window.shareIncomeDetail = function(id){
            $.get('../tmpl/shareIncome/shareIncomeDetail.html', function (tmpl) {
                require('sea-modules/jquery/dialog/jquery.dialog');
                $("#shareIncomeDetail_dialog").dialog({
                    title: "收益详情",
                    tmpl: tmpl,
                    onClose: function () {
                        $("#shareIncomeDetail_dialog").dialog("destroy");
                    },
                    render: function () {
                        $.ajax({
                            type: 'GET',
                            url: UrlConfig.querySettlementRecordsByDepartmentCode + '?token=' + token,
                            contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                            data: {
                                departmentCode: id,
                            },
                            dataType: 'json',
                            success: function (data) {
                                console.log(data);
                                var list = '';
                                var dataList = data.bizData.list;
                                for (var i = 0; i < dataList.length; i++) {
                                    list += '<tr><td class="center">' + timeFomate(dataList[i].requestTime) + '</td><td class="center">' + dataList[i].money + '</td></tr>'
                                }
                                $('#shareIncome-list').append(list);
                            },
                            beforeSend: function (xhr) {
                            },
                            error: function (data) {

                            }
                        });
                    },
                    buttons: [
                        {
                            text: "取消",
                            'class': "btn btn-primary",
                            click: function () {
                                $("#shareIncomeDetail_dialog").dialog("destroy");
                            }
                        }]
                });
            })
        };



        window.settlement = function(id){
            $.get('../tmpl/shareIncome/settlement.html', function (tmpl) {
                require('sea-modules/jquery/dialog/jquery.dialog');
                $("#settlement_dialog").dialog({
                    title: "结算",
                    tmpl: tmpl,
                    onClose: function () {
                        $("#settlement_dialog").dialog("destroy");
                    },
                    render: function () {
                        $.ajax({
                            type: 'GET',
                            url: UrlConfig.querySettlementRecordsByDepartmentCode + '?token=' + token,
                            contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                            data: {
                                departmentCode: id,
                            },
                            dataType: 'json',
                            success: function (data) {
                                console.log(data);
                                var list = '';
                                var dataList = data.bizData.list;
                                for (var i = 0; i < dataList.length; i++) {
                                    list += '<tr><td class="center">' + timeFomate(dataList[i].requestTime) + '</td><td class="center">' + dataList[i].money + '</td></tr>'
                                }
                                $('#shareIncome-list').append(list);
                            },
                            beforeSend: function (xhr) {
                            },
                            error: function (data) {

                            }
                        });
                    },
                    buttons: [
                        {
                            text: "确定结算",
                            'class': "btn btn-primary",
                            click: function () {

                                var money = $('#money').val();
                                $.ajax({
                                    type: 'POST',
                                    url: UrlConfig.settlementByDepartmentCode,
                                    contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                                    data: {
                                        departmentCode: id,
                                        money:money
                                    },
                                    dataType: 'json',
                                    success: function (data) {
                                        $("#settlement_dialog").dialog("destroy");
                                        getEarningsList(UrlConfig.queryAllUserIncome+"?areaCode=-1&areaType=-1&account=&token="+token);

                                    },
                                    beforeSend: function (xhr) {
                                    },
                                    error: function (data) {
                                    }
                                });
                            }
                        },
                        {
                            text: "取消",
                            'class': "btn btn-primary",
                            click: function () {
                                $("#settlement_dialog").dialog("destroy");
                            }
                        }]
                });
            })
        };





    }
});