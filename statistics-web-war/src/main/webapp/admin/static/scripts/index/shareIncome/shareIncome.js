define(function (require, exports, module) {
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


        function tip(ele, str) {
            var errorLable = ele.find('p');
            errorLable.html(str);
            errorLable.show(500);
            setTimeout(function () {
                errorLable.hide(500);
            }, 2000)
        }

        function tip2(ele, str) {
            ele.html(str);
            ele.show(500);
            setTimeout(function () {
                ele.hide(500);
            }, 2000)
        }


        var Area = {
            data: [],
            cityData: [],
            countyData: [],
            init: function () {
                this.getData();
                var that = this;
                $('#share-select-province').on('change', function () {
                    var provinceId = $(this).val();
                    that.cityData = that.getCity(provinceId);
                    that.renderCity(that.render(that.cityData));
                    var cityId = $('#share-select-city').val();
                    that.countyData = that.getCounty(cityId);
                    that.renderCounty(that.render(that.countyData));
                });
                $('#share-select-city').on('change', function () {
                    var cityId = $(this).val();
                    that.countyData = that.getCounty(cityId);
                    that.renderCounty(that.render(that.countyData));
                });
            },

            getData: function () {
                var that = this;
                $.getJSON(UrlConfig.getAllAreaInfo, function (res) {
                    that.data = res.bizData;
                    that.renderProvince(that.render(that.data));

                    var provinceId = $('#share-select-province').val();
                    that.cityData = that.getCity(provinceId);


                    that.renderCity(that.render(that.cityData));
                    var cityId = $('#share-select-city').val();
                    that.countyData = that.getCounty(cityId);

                    that.renderCounty(that.render(that.countyData));
                });
            },

            renderProvince: function (html) {
                html = '<option value="00">请选择省</option>' + html;
                $('#share-select-province').html(html);
            },

            getCity: function (provinceId) {
                for (var i = 0, len = this.data.length; i < len; i++) {
                    if (this.data[i].areaId == provinceId) {
                        return this.data[i].childList;
                    }
                }
                return [];
            },
            renderCity: function (html) {
                html = '<option value="00">请选择市</option>' + html;
                $('#share-select-city').html(html);
            },
            getCounty: function (cityId) {
                for (var i = 0, len = this.cityData.length; i < len; i++) {
                    if (this.cityData[i].areaId == cityId) {
                        return this.cityData[i].childList;
                    }
                }
                return [];
            },

            renderCounty: function (html) {
                html = '<option value="00">请选择区县</option>' + html;
                $('#share-select-district').html(html);
            },

            render: function (data) {
                var html = [];
                for (var i = 0, len = data.length; i < len; i++) {
                    html.push('<option value="' + data[i].areaId + '">' + data[i].areaName + '</option>');
                }
                return html.join('');
            }
        };
        Area.init();


        // 查询事件
        $('#shareManagerSearch').on('click', function () {
            var areaCode = '';
            var areaType = '';
            var shareSelectProvinceV = $('#share-select-province option:selected').val();
            var shareSelectCityV = $('#share-select-city option:selected').val();
            var shareSelectDistrictV = $('#share-select-district option:selected').val();

            if (shareSelectProvinceV == "00" && shareSelectCityV == "00" && shareSelectDistrictV == "00") {
                areaCode = "-1";
                areaType = "-1";
            } else if (shareSelectProvinceV != "00" && shareSelectCityV == "00" && shareSelectDistrictV == "00") {
                areaCode = shareSelectProvinceV;
                areaType = "1";
            } else if (shareSelectProvinceV != "00" && shareSelectCityV != "00" && shareSelectDistrictV == "00") {
                areaCode = shareSelectCityV;
                areaType = "2";
            } else if (shareSelectProvinceV != "00" && shareSelectCityV != "00" && shareSelectDistrictV != "00") {
                areaCode = shareSelectDistrictV;
                areaType = "3";
            }
            var account = $.trim($('#account-txt').val());

            getEarningsList(UrlConfig.queryAllUserIncome + "?areaCode=" + areaCode + "&areaType=" + areaType + "&account=" + account + "&token=" + token);
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
                        return '<a href="javascript:void(0);" class="btn btn-links"  onclick=\"shareIncome(' + row.userId + ')\">结算记录</a><a href="javascript:void(0);" class="btn btn-links" onclick=\"shareIncomeDetail(' + row.userId + ')\">收益详情</a>';
                    }
                },
                {
                    "sClass": "center",
                    "aTargets": [10],
                    "render": function (data, type, row) {
                        console.log(row)
                        return '<button type="button" class="btn btn-info"  onclick=\"settlement(' + row.userId + ')\">结算</button>';
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


        function getSettleList(url) {
            var col = [
                {
                    data: 'requestTime',
                    title: '日期'
                },
                {
                    data: 'money',
                    title: '已结算金额'
                }
            ];
            var columnDefs = [
                {
                    "sClass": "center",
                    "render": function (data, type, row) {
                        return timeFomate(data);
                    },
                    "aTargets": [0]
                },
                {
                    "sClass": "center",
                    "aTargets": [1]
                }
            ];

            var TableInstance = Table({
                columns: col,
                tableContentId: 'shareIncomeManager_form',
                tableId: (+new Date()) + '_table_body',
                sAjaxSource: url,
                columnDefs: columnDefs
            });
            TableInstance.init();
        }


        function getShareIncomeDetailList(url) {
            var col = [
                {
                    data: 'orderNo',
                    title: '订单编号'
                },
                {
                    data: 'phoneNum',
                    title: '购买者账号'
                },
                {
                    data: 'userName',
                    title: '购买者姓名'
                },
                {
                    data: 'createDate',
                    title: '购买时间'
                },
                {
                    data: 'goodsCount',
                    title: '购买数量'
                },
                {
                    data: 'rewardLevel',
                    title: '奖励类型'
                },
                {
                    data: 'rewardMoney',
                    title: '奖励总金额'
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
                    "render": function (data, type, row) {
                        return timeFomate(data);
                    },
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
                }
            ];

            var TableInstance = Table({
                columns: col,
                tableContentId: 'shareIncomeDetailManager_form',
                tableId: (+new Date()) + '_table_body',
                sAjaxSource: url,
                columnDefs: columnDefs
            });
            TableInstance.init();
        }


        window.shareIncome = function (id) {
            $.get('../tmpl/shareIncome/shareIncome.html', function (tmpl) {
                require('dialog');
                $("#shareIncome_dialog").dialog({
                    title: "结算记录",
                    tmpl: tmpl,
                    onClose: function () {
                        $("#shareIncome_dialog").dialog("destroy");
                    },
                    render: function () {
                        $(".modal-dialog").css('width', '60%');
                        getSettleList(UrlConfig.querySettlementRecordsByDepartmentCode + '?departmentCode=' + id + '&token=' + token)
                    },
                    buttons: [
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

        window.shareIncomeDetail = function (id) {
            $.get('../tmpl/shareIncome/shareIncomeDetail.html', function (tmpl) {
                require('dialog');
                $("#shareIncomeDetail_dialog").dialog({
                    title: "收益详情",
                    tmpl: tmpl,
                    onClose: function () {
                        $("#shareIncomeDetail_dialog").dialog("destroy");
                    },
                    render: function () {
                        $(".modal-dialog").css('width', '80%');
                        getShareIncomeDetailList(UrlConfig.queryUserIncomeDetailByUserId + '?userId=' + id + '&token=' + token)
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


        window.settlement = function (id) {
            $.get('../tmpl/shareIncome/settlement.html', function (tmpl) {
                require('dialog');
                $("#settlement_dialog").dialog({
                    title: "结算",
                    tmpl: tmpl,
                    onClose: function () {
                        $("#settlement_dialog").dialog("destroy");
                    },
                    render: function () {
                    },
                    buttons: [
                        {
                            text: "确定结算",
                            'class': "btn btn-primary",
                            click: function () {
                                var money = $.trim($('#money').val());

                                if (money == "") {
                                    tip2($('.form-error'), '请输入结算金额');
                                    return;
                                }

                                if (!/^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/ig.test(money)) {
                                    tip2($('.form-error'), '请输入正确的金额');
                                    return;
                                }

                                $.ajax({
                                    type: 'POST',
                                    url: UrlConfig.settlementByDepartmentCode,
                                    contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                                    data: {
                                        departmentCode: id,
                                        money: money,
                                        type: 2
                                    },
                                    dataType: 'json',
                                    success: function (data) {
                                        console.log(data)
                                        if (data.rtnCode == "0000000") {
                                            $("#settlement_dialog").dialog("destroy");
                                            getEarningsList(UrlConfig.queryAllUserIncome + "?areaCode=-1&areaType=-1&account=&token=" + token);
                                        }
                                        if (data.rtnCode == "0100015") {
                                            tip2($('.form-error'), data.msg);
                                        }
                                    },
                                    beforeSend: function (xhr) {
                                    },
                                    error: function (data) {
                                        alert(data.msg)
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