define(function (require, exports, module) {
    module.exports = function () {
        alert(8)
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


        $.getJSON(UrlConfig.findProvinceList, function (res) {
            console.log(res)
            for (var i = 0; i < res.bizData.length; i++) {
                $('#dep_provinces').append('<option value="' + res.bizData[i].id + '">' + res.bizData[i].provinceName + '</option>')
            }
        });


        $.getJSON(UrlConfig.findCityList, function (res) {
            console.log(res)
            for (var i = 0; i < res.bizData.length; i++) {
                $('#dep_city').append('<option value="' + res.bizData[i].id + '">' + res.bizData[i].cityName + '</option>')
            }
        });


        $.getJSON(UrlConfig.findCountyList, function (res) {
            console.log(res)
            for (var i = 0; i < res.bizData.length; i++) {
                $('#dep_county').append('<option value="' + res.bizData[i].id + '">' + res.bizData[i].countyName + '</option>')
            }
        });










        getEarningsList(UrlConfig.queryAllUserIncome+"?areaCode=-1&areaType=-1&account=&token="+token);
        function getEarningsList(url) {
            var col = [
                {
                    data: 'userName',
                    title: '用户名'
                },
                {
                    data: 'departmentName',
                    title: '账号'
                },
                {
                    data: 'departmentLevel',
                    title: '联系电话'
                },
                {
                    data: 'webPrice',
                    title: '注册地'
                },
                {
                    data: 'salePrice',
                    title: '一级奖励数量'
                },
                {
                    data: 'webSaleCount',
                    title: '二级奖励数量'
                },
                {
                    data: 'wechatSaleCount',
                    title: '总收益'
                },
                {
                    data: 'netIncome',
                    title: '未结算'
                },
                {
                    data: 'notSettled',
                    title: '已结算'
                },
                {
                    data: 'settled',
                    title: '详情'
                },
                {
                    data: 'settled',
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
                        return '<button type="button">结算</button>';
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


        //ajaxFun.get(UrlConfig.getProvinceList, function (res) {
        //    var optionList = '<option value="">所在地</option>';
        //    res.bizData.forEach(function (v) {
        //        optionList += '<option value="' + v.simpleCode + '">' + v.name + '</option>'
        //    });
        //    $('#selectArea').html(optionList);
        //});
        //$(document).on('click', '#monitoredSearch', function () {
        //    var numberOrCard = $('#phoneOrVipNumber').val();
        //    var selectArea = $('#selectArea').val();
        //    var statusType = $('#statusType').val();
        //    var activityStatus = $('#activityStatusSelect').val();
        //    var link = '/admin/monitors?token=' + token + '&queryParam=' + numberOrCard + '&area=' + selectArea + '&status=' + statusType + '&activityStatus=' + activityStatus;
        //    getMonitoredList(link);
        //});


    }
});