/**
 * Created by pdeng on 16/3/24.
 */
define('static/scripts/index/dataMonitored/dataMonitoredList', ['sea-modules/bootstrap/bootstrap', 'sea-modules/jquery/cookie/jquery.cookie', 'sea-modules/jquery/dialog/jquery.dialog', 'static/scripts/index/common/ajax', 'static/scripts/index/common/timeFomate', 'static/scripts/index/message', 'static/scripts/index/datatable', 'static/scripts/index/common/urlConfig'], function (require, exports, module) {


    module.exports = function () {
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
        getMonitoredList(UrlConfig.getMonitorsList);
        function getMonitoredList(url) {
            var col = [{
                data: 'index',
                title: '编号'
            }, {
                data: 'cardNumber',
                title: 'VIP卡号'
            }, {
                data: 'errorStatus',
                title: '状态'
            }, {
                data: 'inputDate',
                title: '手机号'
            }, {
                data: 'area',
                title: '所在地'
            }, {
                data: 'inputDate',
                title: '激活日期'
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
                "aTargets": [3]
            }, {
                "sClass": "center",
                "aTargets": [4]
            }, {
                "sClass": "center",
                "render": function (data, type, row) {
                    var str = '';
                    if (data == '0') {
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
         *
         */
        ajaxFun.get(UrlConfig.getProvinceList, function (res) {
            var optionList = '<option value="">所在地</option>';
            res.bizData.forEach(function (v) {
                optionList += '<option value="' + v.simpleCode + '">' + v.name + '</option>'
            });
            $('#selectArea').html(optionList);
        });
        //$(document).on('change', '#monitoredProvinceSelect', function () {
        //    var selectArea = $(this).val();
        //});
        $(document).on('click', '#monitoredSearch', function () {
            var numberOrCard = $('#phoneOrVipNumber').val();
            var selectArea = $('#selectArea').val();
            var statusType = $('#statusType').val();
            var activityStatus = $('#activityStatusSelect').val();
            var link = '/admin/monitors?token='+token+'&queryParam='+numberOrCard+'&area='+selectArea+'&status='+statusType+'&activityStatus='+activityStatus;
            getMonitoredList(link);
        })
    }
});