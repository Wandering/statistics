/**
 * Created by pdeng on 16/3/24.
 */
define(function (require, exports, module) {
    module.exports = function (dateDay) {
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

        require('datetimepicker');
        require('datetimepickerCN');
        $('#active_start_date').datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            weekStart: 1,
            autoclose: true,
            startView: 2,
            minView: 2,
            forceParse: 0
        }).on('changeDate', function(evl) {
            var startDate = $('#active_start_date').val();
            $('#active_end_date').datetimepicker('setStartDate', startDate);
            setTimeout(function() {
                var endDate = Tool.timeFormat(new Date(+new Date(startDate) + 365 * 24 * 60 * 60 * 1000), 'yyyy-MM-dd');
                $('#active_end_date').datetimepicker('setEndDate', endDate);
            }, 100);
        });
        $('#active_end_date').datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            weekStart: 1,
            autoclose: true,
            startView: 2,
            minView: 2,
            forceParse: 0
        }).on('changeDate', function(evl) {
            var endDate = $('#active_end_date').val();
            $('#active_start_date').datetimepicker('setEndDate', endDate);
            setTimeout(function() {
                var startDate = Tool.timeFormat(new Date(+new Date(endDate) - 365 * 24 * 60 * 60 * 1000), 'yyyy-MM-dd');
                $('#active_start_date').datetimepicker('setStartDate', startDate);
            }, 100);
        });




        getMonitoredList(UrlConfig.getMonitorsList);

        function getMonitoredList(url) {
            var col = [{
                data: 'index',
                title: '编号'
            }, {
                data: 'account',
                title: '用户手机号'

            }, {
                data: 'cardNumber',
                title: 'VIP卡号'
            }, {
                data: 'productType',
                title: '种类'
            }, {
                data: 'errorStatus',
                title: '状态'
            }, {
                data: 'area',
                title: '用户注册地'
            }, {
                data: 'cardArea',
                title: 'vip卡来源地'
            }, {
                data: 'activeDate',
                title: '激活日期'
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
                "aTargets": [3],
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
            },{
                "sClass": "center",
                "aTargets": [4],
                "render": function (data, type, row) {
                    var str = '';
                    if (data == '1') {
                        str = '异常'
                    } else {
                        str = '正常'
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
                "render": function (data, type, row) {
                    var str = '';
                    if (data == null || data == undefined || data == '') {
                        str = '未激活'
                    } else {
                        str = timeFomate(data);
                    }
                    return str;
                },
                "aTargets": [7]
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
            var productType = $('#orderTypePrice option:selected').val();
            var start_date = $('#active_start_date').val();
            var end_date = $('#active_end_date').val();

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
            var link = '/admin/monitors?token=' + token + '&queryParam=' + numberOrCard + '&area=' + selectArea + '&status=' + statusType + '&activityStatus=' + activityStatus + '&productType='+productType + '&startDate=' + timesStartDate + '&endDate=' + timesEndDate ;
            getMonitoredList(link);
        });
    }
});