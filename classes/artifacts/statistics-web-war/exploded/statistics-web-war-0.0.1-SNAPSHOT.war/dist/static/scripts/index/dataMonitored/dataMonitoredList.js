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
                data: 'account',
                title: '手机号'
            }, {
                data: 'area',
                title: '所在地'
            }, {
                data: 'activeDate',
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
        /**
         * 数据监控图
         */
        //var echart = require('');
        //option = {
        //    title : {
        //        text: '未来一周气温变化',
        //        subtext: '纯属虚构'
        //    },
        //    tooltip : {
        //        trigger: 'axis'
        //    },
        //    legend: {
        //        data:['最高气温','最低气温']
        //    },
        //    toolbox: {
        //        show : true,
        //        feature : {
        //            mark : {show: true},
        //            dataView : {show: true, readOnly: false},
        //            magicType : {show: true, type: ['line', 'bar']},
        //            restore : {show: true},
        //            saveAsImage : {show: true}
        //        }
        //    },
        //    calculable : true,
        //    xAxis : [
        //        {
        //            type : 'category',
        //            boundaryGap : false,
        //            data : ['周一','周二','周三','周四','周五','周六','周日']
        //        }
        //    ],
        //    yAxis : [
        //        {
        //            type : 'value',
        //            axisLabel : {
        //                formatter: '{value} °C'
        //            }
        //        }
        //    ],
        //    series : [
        //        {
        //            name:'最高气温',
        //            type:'line',
        //            data:[11, 11, 15, 13, 12, 13, 10],
        //            markPoint : {
        //                data : [
        //                    {type : 'max', name: '最大值'},
        //                    {type : 'min', name: '最小值'}
        //                ]
        //            },
        //            markLine : {
        //                data : [
        //                    {type : 'average', name: '平均值'}
        //                ]
        //            }
        //        },
        //        {
        //            name:'最低气温',
        //            type:'line',
        //            data:[1, -2, 2, 5, 3, 2, 0],
        //            markPoint : {
        //                data : [
        //                    {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
        //                ]
        //            },
        //            markLine : {
        //                data : [
        //                    {type : 'average', name : '平均值'}
        //                ]
        //            }
        //        }
        //    ]
        //};

    }
});