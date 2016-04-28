define(function (require, exports, module) {
    module.exports = function () {
        // ============  代理商收益管理

        alert(22)



        //获取所需组件依赖
        require('bootstrap');
        require('cookie');
        require('dialog');
        var ajaxFun = require('../common/ajax');
        var timeFomate = require('../common/timeFomate.js');
        var message = require('../message.js');
        var Table = require('../datatable.js');
        var token = $.cookie('bizData');
        var UrlConfig = require('../common/urlConfig');


        $.getJSON(UrlConfig.getAllAreaInfo, function (res) {
            console.log(res)
            for (var i = 0; i < res.bizData.length; i++) {
                $('#dep_provinces').append('<option value="' + res.bizData[i].id + '">' + res.bizData[i].provinceName + '</option>')
            }
        });
        //
        //
        //$.getJSON(UrlConfig.findCityList, function (res) {
        //    console.log(res)
        //    for (var i = 0; i < res.bizData.length; i++) {
        //        $('#dep_city').append('<option value="' + res.bizData[i].id + '">' + res.bizData[i].cityName + '</option>')
        //    }
        //});
        //
        //
        //$.getJSON(UrlConfig.findCountyList, function (res) {
        //    console.log(res)
        //    for (var i = 0; i < res.bizData.length; i++) {
        //        $('#dep_county').append('<option value="' + res.bizData[i].id + '">' + res.bizData[i].countyName + '</option>')
        //    }
        //});









        //var areaCode = $('#orderType option:selected').val();


        getRecordList(UrlConfig.queryAllDepartmentIncome + "?areaCode=-1&areaType=-1&account=&token=" + token);
        function getRecordList(url) {
            var col = [
                {
                    data: 'departmentCode',
                    title: '部门编码'
                },
                {
                    data: 'departmentName',
                    title: '代理商'
                },
                {
                    data: 'departmentLevel',
                    title: '代理商等级'
                },
                {
                    data: 'salePrice',
                    title: '出厂价'
                },
                {
                    data: 'wechatPrice',
                    title: '微信售价'
                },
                {
                    data: 'webPrice',
                    title: 'web售价'
                },
                {
                    data: 'wechatSaleCount',
                    title: '微信销量'
                },

                {
                    data: 'webSaleCount',
                    title: 'web销量'
                },

                {
                    data: 'netIncome',
                    title: '网上收益'
                },
                {
                    data: 'notSettled',
                    title: '未结算'
                },
                {
                    data: 'settled',
                    title: '已结算'
                }
                ,
                {
                    data: 'detail',
                    title: '详情'
                }
                ,
                {
                    data: 'detail',
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
                    "aTargets": [9]
                },
                {
                    "sClass": "center",
                    "aTargets": [10]
                },
                {
                    "sClass": "center",
                    "aTargets": [11],
                    "render": function (data, type, row) {

                        return '<a href="javascript:void(0);" class="btn btn-links"  onclick=\"shareIncome('+ row.departmentCode +')\">结算记录</a>';
                    }

                },
                {
                    "sClass": "center",
                    "aTargets": [12],
                    "render": function (data, type, row) {
                        return '<a href="javascript:void(0);" class="btn btn-links" departmentCode="'+ row.departmentCode +'" departmentName="'+ row.departmentName +'" wechatPrice="'+ row.wechatPrice +'" webPrice="'+ row.webPrice +'" wechatSaleCount="'+ row.wechatSaleCount +'" webSaleCount="'+ row.webSaleCount +'" notSettled="'+ row.notSettled +'"  onclick=\"settlement(this)\">结算</a>';
                    }
                }
            ];
            var TableInstance = Table({
                columns: col,
                tableContentId: 'earningsManagerContent',
                tableId: (+new Date()) + '_table_body',
                sAjaxSource: url,
                columnDefs: columnDefs
            });
            TableInstance.init();
        }
        window.shareIncome = function(id){
            $.get('../tmpl/earningsManager/earningsManager.html', function (tmpl) {
                require('dialog');
                $("#earningsManager_dialog").dialog({
                    title: "结算记录详情",
                    tmpl: tmpl,
                    onClose: function () {
                        $("#earningsManager_dialog").dialog("destroy");
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
                                $('#earningsManager-list').append(list);
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
                                $("#earningsManager_dialog").dialog("destroy");
                            }
                        }]
                });
            })
        };



        window.settlement = function(obj){
            $.get('../tmpl/earningsManager/settlementManager.html', function (tmpl) {
                require('dialog');
                $("#settlement_dialog").dialog({
                    title: "结算记录详情",
                    tmpl: tmpl,
                    onClose: function () {
                        $("#settlement_dialog").dialog("destroy");
                    },
                    render: function () {
                        $('.departmentName').text($(obj).attr('departmentName'));
                        $('.wechatPrice').text($(obj).attr('wechatPrice'));
                        $('.webPrice').text($(obj).attr('webPrice'));
                        $('.wechatSaleCount').text($(obj).attr('wechatSaleCount'));
                        $('.webSaleCount').text($(obj).attr('webSaleCount'));
                        $('.notSettled').text($(obj).attr('notSettled'));
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
                                $("#settlement_dialog").dialog("destroy");
                            }
                        }]
                });
            })
        };



    }
});