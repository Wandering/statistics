/**
 * Created by pdeng on 16/3/24.
 */
define(function (require, exports, module) {
    var UrlConfig = require('../common/urlConfig');
        var moduleChart = require('../common/businessChart');
        moduleChart({
            url: UrlConfig.getErrorChart,
            handle: function(param) {
                var numberOrCard = $('#phoneOrVipNumber').val();
                var selectArea = $('#selectArea').val();
                var statusType = $('#statusType').val();
                var activityStatus = $('#activityStatusSelect').val();
                var link = '/admin/monitors?token=' + token + '&queryParam=' + numberOrCard + '&area=' + selectArea + '&status=' + statusType + '&activityStatus=' + activityStatus;
                getMonitoredList(link);
            },

            legendData: ['已激活用户状态监控'],
            data: function(bizData) {
                console.log(bizData)
                var xAxisData = [],
                    num = [];
                for (var i = 0; i < bizData.length; i++) {
                    xAxisData.push(bizData[i].dateDay);
                    num.push(bizData[i].num);
                }

                return {
                    xAxisData: xAxisData,
                    seriesData: [{
                        name: '已激活用户状态监控',
                        type: 'line',
                        barMaxWidth: 30,
                        data: num
                    }]
                };
            }
        });




    module.exports = function () {
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
        getMonitoredList(UrlConfig.getMonitorsList);
        function getMonitoredList(url) {
            var col = [{
                data: 'index',
                title: '编号'
            }, {
                data: 'account',
                title: '用户手机号'

            }, {
                data: 'errorStatus',
                title: '状态'
            }, {
                data: 'cardNumber',
                title: 'VIP卡号'
            }, {
                data: 'area',
                title: '用户注册地'
            }, {
                data: 'activeDate',
                title: '激活日期'
            }, {
                data: 'cardArea',
                title: 'vip卡来源地'
            }];
            var columnDefs = [{
                "sClass": "center",
                "aTargets": [0]
            }, {
                "sClass": "center",
                "render": function (data, type, row) {
                    var str = '';
                    if (data == '1') {
                        str = '异常'
                    } else {
                        str = '正常'
                    }
                    return str;
                },
                "aTargets": [2]
            }, {
                "sClass": "center",
                "aTargets": [1]
            }, {
                "sClass": "center",
                "aTargets": [4]
            }, {
                "sClass": "center",
                "render": function (data, type, row) {
                    var str = '';
                    if (data == null || data == undefined || data == '') {
                        str = '未激活'
                    } else {
                        str = timeFomate(data);
                    }
                    return str;
                },
                "aTargets": [5]
            }];
            var TableInstance = Table({
                columns: col,
                tableContentId: 'dataMonitoredContent',
                tableId: (+new Date()) + '_table_body',
                sAjaxSource: url,
                columnDefs: columnDefs
            });
            TableInstance.init();
        }

        /**
         * 状态：正常（0）|  异常 （1）
         * 激活日期：已激活（1）|  未激活（0）
         */
        ajaxFun.get(UrlConfig.getProvinceList, function (res) {
            var optionList = '<option value="">所在地</option>';
            res.bizData.forEach(function (v) {
                optionList += '<option value="' + v.simpleCode + '">' + v.name + '</option>'
            });
            $('#selectArea').html(optionList);
        });
        $(document).on('click', '#monitoredSearch', function () {
            var numberOrCard = $('#phoneOrVipNumber').val();
            var selectArea = $('#selectArea').val();
            var statusType = $('#statusType').val();
            var activityStatus = $('#activityStatusSelect').val();
            var link = '/admin/monitors?token=' + token + '&queryParam=' + numberOrCard + '&area=' + selectArea + '&status=' + statusType + '&activityStatus=' + activityStatus;
            getMonitoredList(link);
        });
















    }
});