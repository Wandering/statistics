define('static/scripts/index/shareIncome/shareIncome', ['sea-modules/bootstrap/bootstrap', 'sea-modules/jquery/cookie/jquery.cookie', 'sea-modules/jquery/dialog/jquery.dialog', 'static/scripts/index/common/ajax', 'static/scripts/index/common/timeFomate', 'static/scripts/index/message', 'static/scripts/index/datatable', 'static/scripts/index/common/urlConfig'], function (require, exports, module) {
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


        getEarningsList(UrlConfig.queryAllUserIncome + "?areaCode=-1&areaType=-1&account=&token=" + token);
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
                        return '<a href="javascript:void(0);" class="btn btn-links">结算记录</a><a href="javascript:void(0);" class="btn btn-links">收益详情</a>';
                    }
                },
                {
                    "sClass": "center",
                    "aTargets": [10],
                    "render": function (data, type, row) {
                        return '<button type="button" class="btn btn-info">结算</button>';
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

        /*
         *
         * 分享收益<查询>模块
         *
         *
         */
        //    "areaCode":"地区编码  -1:所有地区  long",
        //    "areaType":"类型  -1：所有地区 1:省 2：市  3：区县",
        //    "account":"账号   String",
        //    "currentPageNo":"当前页  int",
        //    "pageSize":"页大小  int"
        ajaxFun.get(UrlConfig.findProvinceList, function (res) {
            var optionList = '<option value="">选择省份</option>';
            res.bizData.forEach(function (v) {
                optionList += '<option value="' + v.simpleCode + '">' + v.name + '</option>'
            });
            $('#share-select-province').html(optionList);
        });
        ajaxFun.get(UrlConfig.findCityList, function (res) {
            var optionList = '<option value="">选择城市</option>';
            res.bizData.forEach(function (v) {
                optionList += '<option value="' + v.simpleCode + '">' + v.name + '</option>'
            });
            $('#share-select-city').html(optionList);
        });
        ajaxFun.get(UrlConfig.findCountyList, function (res) {
            var optionList = '<option value="">选择县</option>';
            res.bizData.forEach(function (v) {
                optionList += '<option value="' + v.simpleCode + '">' + v.name + '</option>'
            });
            $('#share-select-district').html(optionList);
        });

        $(document).on('click', '#shareManagerSearch', function () {
            var numberOrCard = $('#share-select-province').val();
            var selectArea = $('#selectArea').val();
            var statusType = $('#statusType').val();
            var activityStatus = $('#activityStatusSelect').val();
            var link = UrlConfig.queryAllUserIncome + '?token=' + token + '&areaCode='+areaCodeVal+'&areaCode='+'&areaType'+areaTypeVal+'&account'+accountVal+'&currentPageNo'+currentPageNo+'&pageSize'+pageSize;
            getEarningsList(link);
        });


    }
});