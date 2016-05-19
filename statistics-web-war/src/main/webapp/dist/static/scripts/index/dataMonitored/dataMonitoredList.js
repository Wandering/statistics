/**
 * Created by pdeng on 16/3/24.
 */
define('static/scripts/index/dataMonitored/dataMonitoredList', ['sea-modules/bootstrap/bootstrap', 'sea-modules/jquery/cookie/jquery.cookie', 'sea-modules/jquery/dialog/jquery.dialog', 'static/scripts/index/common/ajax', 'static/scripts/index/common/timeFomate', 'static/scripts/index/message', 'static/scripts/index/datatable', 'static/scripts/index/common/urlConfig', 'sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker', 'sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker.zh-CN'], function (require, exports, module) {
    module.exports = function (dateDay) {
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

        require('sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker');
        require('sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker.zh-CN');
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