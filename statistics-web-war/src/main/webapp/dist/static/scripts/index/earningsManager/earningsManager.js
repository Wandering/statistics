
define('static/scripts/index/earningsManager/earningsManager', ['sea-modules/bootstrap/bootstrap', 'sea-modules/jquery/cookie/jquery.cookie', 'sea-modules/jquery/dialog/jquery.dialog', 'static/scripts/index/common/ajax', 'static/scripts/index/common/timeFomate', 'static/scripts/index/message', 'static/scripts/index/datatable', 'static/scripts/index/common/urlConfig'], function (require, exports, module) {
    module.exports = function () {
        // ============  代理商收益管理
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
            data:[],
            cityData: [],
            countyData: [],
            init: function() {
                this.getData();
                var that = this;
                $('#agentIncome-select-province').on('change', function() {
                    var provinceId = $(this).val();
                    that.cityData = that.getCity(provinceId);
                    that.renderCity(that.render(that.cityData));
                    var cityId =  $('#agentIncome-select-city').val();
                    that.countyData = that.getCounty(cityId);
                    that.renderCounty(that.render(that.countyData));
                });
                $('#agentIncome-select-city').on('change', function() {
                    var cityId = $(this).val();
                    that.countyData = that.getCounty(cityId);
                    that.renderCounty(that.render(that.countyData));
                });
            },

            getData: function() {
                var that = this;
                $.getJSON(UrlConfig.getAllAreaInfo, function (res) {
                    that.data = res.bizData;
                    that.renderProvince(that.render(that.data));

                    var provinceId = $('#agentIncome-select-province').val();
                    that.cityData = that.getCity(provinceId);


                    that.renderCity(that.render(that.cityData));
                    var cityId =  $('#agentIncome-select-city').val();
                    that.countyData = that.getCounty(cityId);

                    that.renderCounty(that.render(that.countyData));
                });
            },

            renderProvince: function(html) {
                html = '<option value="00">请选择省</option>' + html;
                $('#agentIncome-select-province').html(html);
            },

            getCity: function(provinceId) {
                for (var i = 0, len = this.data.length; i < len; i++) {
                    if (this.data[i].areaId == provinceId) {
                        return this.data[i].childList;
                    }
                }
                return [];
            },
            renderCity: function(html) {
                html = '<option value="00">请选择市</option>' + html;
                $('#agentIncome-select-city').html(html);
            },
            getCounty: function(cityId) {
                for (var i = 0, len = this.cityData.length; i < len; i++) {
                    if (this.cityData[i].areaId == cityId) {
                        return this.cityData[i].childList;
                    }
                }
                return [];
            },

            renderCounty: function(html) {
                html = '<option value="00">请选择区县</option>' + html;
                $('#agentIncome-select-district').html(html);
            },

            render: function(data) {
                var html = [];
                for (var i = 0,len = data.length; i < len; i++) {
                    html.push('<option value="' + data[i].areaId + '">' + data[i].areaName + '</option>');
                }
                return html.join('');
            }
        };
        Area.init();









        // 查询事件
        $('#earningsManagerSearch').on('click',function(){
            var areaCode = '';
            var areaType = '';
            var agentIncomeSelectProvinceV = $('#agentIncome-select-province option:selected').val();
            var agentIncomeSelectCityV = $('#agentIncome-select-city option:selected').val();
            var agentIncomeSelectDistrictV = $('#agentIncome-select-district option:selected').val();

            if(agentIncomeSelectProvinceV=="00" && agentIncomeSelectCityV=="00" && agentIncomeSelectDistrictV=="00"){
                areaCode = "-1";
                areaType = "-1";
            }else if(agentIncomeSelectProvinceV!="00" && agentIncomeSelectCityV=="00" && agentIncomeSelectDistrictV=="00"){
                areaCode = agentIncomeSelectProvinceV;
                areaType = "1";
            }else if(agentIncomeSelectCityV!="00" && agentIncomeSelectCityV!="00" && agentIncomeSelectDistrictV=="00"){
                areaCode = shareSelectCityV;
                areaType = "2";
            }else if(agentIncomeSelectProvinceV!="00" && agentIncomeSelectCityV!="00" && agentIncomeSelectDistrictV!="00"){
                areaCode = agentIncomeSelectDistrictV;
                areaType = "3";
            }

            getRecordList(UrlConfig.queryAllDepartmentIncome + "?areaCode="+ areaCode +"&areaType="+ areaType +"&token=" + token);
        });


        getRecordList(UrlConfig.queryAllDepartmentIncome + "?areaCode=-1&areaType=-1&token=" + token);
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
                    data: 'productName',
                    title: '种类'
                },
                {
                    data: 'pickupPrice',
                    title: '出厂价'
                },
                {
                    data: 'salePrice',
                    title: '售价'
                },
                {
                    data: 'webSaleCount',
                    title: 'web销量'
                },

                {
                    data: 'wechatSaleCount',
                    title: '微信销量'
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
                    "bVisible": false,
                    "sClass": "center line35",
                    "aTargets": [0]
                },
                {
                    "sClass": "center line35",
                    "aTargets": [1]
                },
                {
                    "bVisible": false,
                    "sClass": "center line35",
                    "aTargets": [2]
                },
                {
                    "sClass": "center",
                    "aTargets": [3],
                    "render": function (data, type, row) {
                        var listHtml = '';
                        for (var i = 0; i < row.productSales.length; i++) {
                            listHtml += '<div>' + row.productSales[i].productName + '</div>';
                        }
                        return listHtml;
                    }
                },
                {
                    "sClass": "center",
                    "aTargets": [4],
                    "render": function (data, type, row) {
                        var listHtml = '';
                        for (var i = 0; i < row.productSales.length; i++) {
                            listHtml += '<div>' + row.productSales[i].pickupPrice + '</div>';
                        }
                        return listHtml;
                    }
                },
                {
                    "sClass": "center",
                    "aTargets": [5],
                    "render": function (data, type, row) {
                        var listHtml = '';
                        for (var i = 0; i < row.productSales.length; i++) {
                            listHtml += '<div>' + row.productSales[i].salePrice + '</div>';
                        }
                        return listHtml;
                    }
                },
                {
                    "sClass": "center",
                    "aTargets": [6],
                    "render": function (data, type, row) {
                        var listHtml = '';
                        for (var i = 0; i < row.productSales.length; i++) {
                            listHtml += '<div>' + row.productSales[i].webSaleCount + '</div>';
                        }
                        return listHtml;
                    }
                },
                {
                    "sClass": "center",
                    "aTargets": [7],
                    "render": function (data, type, row) {
                        var listHtml = '';
                        for (var i = 0; i < row.productSales.length; i++) {
                            listHtml += '<div>' + row.productSales[i].wechatSaleCount + '</div>';
                        }
                        return listHtml;
                    }
                },
                {
                    "sClass": "center line35",
                    "aTargets": [8]
                },
                {
                    "sClass": "center line35",
                    "aTargets": [9]
                },
                {
                    "sClass": "center line35",
                    "aTargets": [10]
                },
                {
                    "sClass": "center line35",
                    "aTargets": [11],
                    "render": function (data, type, row) {

                        return '<a href="javascript:void(0);" class="btn btn-links"  onclick=\"shareIncome('+ row.departmentCode +')\">结算记录</a>';
                    }
                },
                {
                    "sClass": "center",
                    "aTargets": [12],
                    "render": function (data, type, row) {
                        return '<a href="javascript:void(0);" class="btn btn-info" departmentCode="'+ row.departmentCode +'" departmentName="'+ row.departmentName +'" wechatPrice="'+ row.wechatPrice +'" webPrice="'+ row.webPrice +'" wechatSaleCount="'+ row.wechatSaleCount +'" webSaleCount="'+ row.webSaleCount +'" notSettled="'+ row.notSettled +'"  onclick=\"settlement(this)\">结算</a>';
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
                tableContentId: 'earningsManager_form',
                tableId: (+new Date()) + '_table_body',
                sAjaxSource: url,
                columnDefs: columnDefs
            });
            TableInstance.init();
        }






        window.shareIncome = function(id){
            $.get('../tmpl/earningsManager/earningsManager.html', function (tmpl) {
                require('sea-modules/jquery/dialog/jquery.dialog');
                $("#earningsManager_dialog").dialog({
                    title: "结算记录详情",
                    tmpl: tmpl,
                    onClose: function () {
                        $("#earningsManager_dialog").dialog("destroy");
                    },
                    render: function () {

                        $(".modal-dialog").css('width','60%');
                        getSettleList(UrlConfig.querySettlementRecordsByDepartmentCode + '?departmentCode='+ id +'&token=' + token)
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
            console.log($(obj).attr('departmentCode'))
            $.get('../tmpl/earningsManager/settlementManager.html', function (tmpl) {
                require('sea-modules/jquery/dialog/jquery.dialog');
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
                                        departmentCode: $(obj).attr('departmentCode'),
                                        money:money,
                                        type:1
                                    },
                                    dataType: 'json',
                                    success: function (data) {

                                        if (data.rtnCode == "0000000") {
                                            getRecordList(UrlConfig.queryAllDepartmentIncome + "?areaCode=-1&areaType=-1&account=&token=" + token);
                                            $("#settlement_dialog").dialog("destroy");
                                        }
                                        if (data.rtnCode == "0100015") {
                                            tip2($('.form-error'), data.msg);
                                        }


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