/**
 * Created by pdeng on 16/3/23.
 */
define('static/scripts/index/goodsManager/goodsSelectList', ['sea-modules/bootstrap/bootstrap', 'sea-modules/jquery/cookie/jquery.cookie', 'static/scripts/index/common/timeFomate', 'static/scripts/index/message', 'static/scripts/index/datatable', 'static/scripts/index/common/urlConfig', 'sea-modules/jquery/dialog/jquery.dialog', 'static/scripts/index/goodsManager/outbound_from'], function (require, exports, module) {
    module.exports = function () {
        //获取所需组件依赖
        require('sea-modules/bootstrap/bootstrap');
        require('sea-modules/jquery/cookie/jquery.cookie');
        var timeFomate = require('static/scripts/index/common/timeFomate');
        var message = require('static/scripts/index/message');
        var Table = require('static/scripts/index/datatable');
        var UrlConfig = require('static/scripts/index/common/urlConfig');
        var token = $.cookie('bizData');
        $(document).on('click', '#tab-btn li', function () {
            $(this).addClass('active').siblings().removeClass('active');

            if($(this).attr('roleType')==1){
                //未出库table
                willOutput(UrlConfig.getGoodsMange);
                $('.tables-t .h4-st').text('未出库货物');

            }else{
                $('.tables-t .h4-st').text('已出库货物');
                //已出库table
                alreadyOutput(UrlConfig.getGoodsMangeOut);
            }
            var n = $(this).index();
            //$('.table-responsive').hide().eq(n).fadeIn(100);
            var placeTip = ['未出库vip卡号查询', '已出库vip卡号查询'];
            $('#vip-card').attr({'data-type': n, 'placeholder': placeTip[n]});
            $('#search-btn').attr('data-type', n);
        });
        $('#tab-btn li:eq(0)').click();
        $(document).on('click', '#search-btn', function () {
            var cardNumber = $.trim($('#vip-card').val());
            if (cardNumber.length > 10) {
                $('.form-error').text('您输入的卡号不正确').fadeIn(1000).fadeOut(1000);
                return false
            }
            var foo = $(this).attr('data-type');
            foo == '1' ? alreadyOutput('/admin/agents?token=' + token + '&isOutput=true&cardNumber=' + cardNumber) : willOutput('/admin/agents?token=' + token + '&isOutput=false&cardNumber=' + cardNumber);
        });

        function willOutput(url) {
            var col = [{
                data: 'id',
                title: '<input type="checkbox">'
            }, {
                data: 'index',
                title: '编号'
            }, {
                data: 'cardNumber',
                title: 'VIP卡号'
            }, {
                data: 'goodsStatus',
                title: '状态'
            }, {
                data: 'inputDate',
                title: '入库日期'
            }];
            var columnDefs = [{
                "sClass": "center",
                "sWidth": "30px",
                "render": function (data, type, row) {
                    return '<input class="selNoOutbound" type="checkbox"  data-id="' + data + '"  />';
                },
                "aTargets": [0]
            }, {
                "sClass": "center",
                "aTargets": [1]
            }, {
                "sClass": "center",
                "aTargets": [2]
            }, {
                "sClass": "center",
                "aTargets": [3]
            }, {
                "render": function (data, type, row) {
                    return timeFomate(data);
                },
                "sClass": "center",
                "aTargets": [4]
            }];
            var TableInstance = Table({
                columns: col,
                tableContentId: 'table_content',
                tableId: (+new Date()) + '_table_body',
                sAjaxSource: url,
                columnDefs: columnDefs
            });
            TableInstance.init();
        }

        function alreadyOutput(url) {
            var col = [{
                data: 'cardNumber',
                title: 'VIP卡号'
            }, {
                data: 'flow',
                title: '流向地'
            }, {
                data: 'inputDate',
                title: '激活日期'
            }, {
                data: 'outputDate',
                title: '出库日期'
            }];
            var columnDefs = [{
                "sClass": "center",
                "aTargets": [0]
            }, {
                "sClass": "center",
                "aTargets": [1]
            }, {
                "sClass": "center",
                "render": function (data, type, row) {
                    var str = '';
                    if (data == '0' || data == null) {
                        str = '未激活';
                    } else {
                        str = timeFomate(data);
                    }
                    return str;
                },
                "aTargets": [2]
            }, {
                "render": function (data, type, row) {
                    return timeFomate(data);
                },
                "sClass": "center",
                "aTargets": [3]
            }];
            var TableInstance = Table({
                columns: col,
                tableContentId: 'table_content',
                tableId: (+new Date()) + '_table_body',
                sAjaxSource: url,
                columnDefs: columnDefs
            });
            TableInstance.init();
        };



        var productionDepartment = function(formArry, succCallback, id) {
            var departmentJson = {
                area: formArry[0] || ''
            };
            var selNoOutboundArr = [];
            $('.selNoOutbound[type="checkbox"]:checked').each(function(i){
                selNoOutboundArr.push($(this).attr('data-id'));
            });
            departmentJson.outputList = selNoOutboundArr.join(",");
            $.ajax({
                type: 'post',
                url: '/admin/output?token=' + token,
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                data: {
                    departmentJson: JSON.stringify(departmentJson)
                },
                dataType: 'json',
                success: function(data) {
                    console.log(data)
                    succCallback(data);

                },
                beforeSend: function(xhr) {},
                error: function(data) {

                }
            });
        };










        var ButtonEvent = {
            production: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    $.get('../tmpl/outbound/outbound.html', function(tmpl) {
                        require('sea-modules/jquery/dialog/jquery.dialog');
                        $("#outbound_dialog").dialog({
                            title: "出库",
                            tmpl: tmpl,
                            onClose: function() {
                                $("#outbound_dialog").dialog("destroy");
                            },
                            render: function() {
                                $.getJSON('/admin/getCurrUserNextArea?token=' + token,function(res){
                                    console.log(res.bizData[0].name)
                                    for(var i=0;i<res.bizData.length;i++){
                                        $('#dep_provinces').append('<option simpleCode="'+ res.bizData[i].simpleCode  +'" value="'+ res.bizData[i].id +'">'+ res.bizData[i].name +'</option>')
                                    }
                                });
                            },
                            buttons: [{
                                text: "出库",
                                'class': "btn btn-primary",
                                click: function() {
                                    var vali = require('static/scripts/index/goodsManager/outbound_from');
                                    vali.validate(function(formArry) {
                                        productionDepartment(formArry, function(ret) {
                                            //console.log(ret)
                                            if ('0000000' === ret.rtnCode) {
                                                tableObj.fnDraw();
                                                var node = {
                                                    id: ret.bizData.departmentCode,
                                                    name: formArry[0]
                                                };
                                                //treeCallback({
                                                //    type: 'add',
                                                //    obj: node
                                                //});

                                                $("#add_dep").dialog("destroy");
                                            } else {
                                                $("#add_dep").dialog("destroy");
                                                message({
                                                    title: '温馨提示',
                                                    msg: ret.msg,
                                                    type: 'alert',
                                                    clickHandle: function() {
                                                        window.location.href = 'login.html';
                                                    }
                                                });
                                            }
                                        });
                                    });


                                }
                            }, {
                                text: "取消",
                                'class': "btn btn-primary",
                                click: function() {
                                    $("#outbound_dialog").dialog("destroy");
                                }
                            }]
                        });
                    })
                });
            },

            batchOutbound: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    if (!tableObj) {
                        return;
                    }

                    $.get('../tmpl/outbound/outbound.html', function(tmpl) {
                        require('sea-modules/jquery/dialog/jquery.dialog');
                        $("#outbound_dialog").dialog({
                            title: "批量出库",
                            tmpl: tmpl,
                            onClose: function() {
                                $("#outbound_dialog").dialog("destroy");
                            },
                            render: function() {


                            },
                            buttons: [{
                                text: "批量出库",
                                'class': "btn btn-primary",
                                click: function() {



                                }
                            }, {
                                text: "取消",
                                'class': "btn btn-primary",
                                click: function() {
                                    $("#outbound_dialog").dialog("destroy");
                                }
                            }]
                        });
                    })
                });
            },


            batchOutbound: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    if (!tableObj) {
                        return;
                    }

                    $.get('../tmpl/outbound/outbound.html', function(tmpl) {
                        require('sea-modules/jquery/dialog/jquery.dialog');
                        $("#outbound_dialog").dialog({
                            title: "批量出库",
                            tmpl: tmpl,
                            onClose: function() {
                                $("#outbound_dialog").dialog("destroy");
                            },
                            render: function() {


                            },
                            buttons: [{
                                text: "批量出库",
                                'class': "btn btn-primary",
                                click: function() {



                                }
                            }, {
                                text: "取消",
                                'class': "btn btn-primary",
                                click: function() {
                                    $("#outbound_dialog").dialog("destroy");
                                }
                            }]
                        });
                    })
                });
            }
        };

        require.async(['static/scripts/index/renderResource'], function(resource) {
            resource(ButtonEvent, token);
        });








    }


});