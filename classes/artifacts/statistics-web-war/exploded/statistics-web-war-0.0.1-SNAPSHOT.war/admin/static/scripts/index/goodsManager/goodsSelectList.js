/**
 * Created by pdeng on 16/3/23.
 */
define(function (require, exports, module) {
    module.exports = function () {
        //获取所需组件依赖
        require('bootstrap');
        require('cookie');
        var timeFomate = require('../common/timeFomate.js');
        var message = require('../message.js');
        var Table = require('../datatable.js');
        var UrlConfig = require('../common/urlConfig');
        var token = $.cookie('bizData');
        var cookieJson = JSON.parse($.cookie('userInfo'));
        if (cookieJson.areaCode.length == 6) {
            $('#tab-btn,.tables-t').remove();
            willOutput(UrlConfig.getGoodsMange);
        }
        $(document).on('click', '#tab-btn li', function () {
            $(this).addClass('active').siblings().removeClass('active');
            if ($(this).attr('roleType') == 1) {
                //未出库table
                willOutput(UrlConfig.getGoodsMange);
                $('.tables-t .h4-st').text('未出库货物');
                $('#production,#batchOutbound').show();
                $('#flowarea').hide();
            } else {
                $('.tables-t .h4-st').text('已出库货物');
                //已出库table
                alreadyOutput(UrlConfig.getGoodsMangeOut);
                $('#production,#batchOutbound').hide();
                $('#flowarea').show();
            }
            var n = $(this).index();
            var placeTip = ['未出库vip卡号查询', '已出库vip卡号查询'];
            $('#vip-card').attr({'data-type': n, 'placeholder': placeTip[n]});
            $('#search-btn').attr('data-type', n);
        });


        $('#tab-btn li:eq(0)').click();
        //$('#flowarea').hide();


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
                title: '<input type="checkbox" id="selectall">'
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
                    return '<input class="selNoOutbound" onclick="clickChecked()" type="checkbox"  data-id="' + data + '"  />';
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
        var productionDepartment = function (formArry, succCallback, id) {
            var selNoOutboundArr = [];
            $('.selNoOutbound[type="checkbox"]:checked').each(function () {
                selNoOutboundArr.push($(this).attr('data-id'));
            });
            $.ajax({
                type: 'post',
                url: '/admin/output?token=' + token,
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                data: {
                    area: formArry[0] || '',
                    outputList: selNoOutboundArr.join(",")
                },
                dataType: 'json',
                success: function (data) {
                    console.log(data)
                    succCallback(data);
                },
                beforeSend: function (xhr) {
                },
                error: function (data) {

                }
            });
        };
        var productionBatchDepartment = function (formArry, succCallback, id) {
            var outboundBatchNum = parseInt($('#outbound_batch_num').val());
            $.ajax({
                type: 'post',
                url: '/admin/output?token=' + token,
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                data: {
                    area: formArry[0] || '',
                    rows: outboundBatchNum
                },
                dataType: 'json',
                success: function (data) {
                    console.log(data)
                    succCallback(data);

                },
                beforeSend: function (xhr) {
                },
                error: function (data) {

                }
            });

        };
        var flowAreaDepartment = function (formArry, succCallback, id) {
            var flowAreaArr = [];
            console.log($('#flow-area-list [type="checkbox"]:checked').parent().attr('simpleCode'))
            $('#flow-area-list [type="checkbox"]:checked').each(function () {
                flowAreaArr.push($(this).parent().attr('simpleCode'));
            });
            console.log(flowAreaArr)
            $.ajax({
                type: 'post',
                url: '/admin/agents?token=' + token,
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                data: {
                    isOutput: true,
                    area: flowAreaArr.join(",")
                },
                dataType: 'json',
                success: function (data) {
                    console.log(data)
                    succCallback(data);
                },
                beforeSend: function (xhr) {
                },
                error: function (data) {

                }
            });
        };


        // 全选
        $('#selectall').on('click', function () {
            var that = this;
            $('.selNoOutbound[type="checkbox"]').each(function () {
                this.checked = that.checked;
            });
        });

        // 单选
        window.clickChecked = function () {
            var selNoOutboundLength = $('.selNoOutbound[type="checkbox"]').length;
            var selNoOutboundCheckedLength = $('.selNoOutbound[type="checkbox"]:checked').length;
            if (selNoOutboundLength == selNoOutboundCheckedLength) {
                $('#selectall')[0].checked = true;
            } else {
                $('#selectall')[0].checked = false;
            }
        };

        var ButtonEvent = {
            production: function (elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function (e) {
                    if ($('.selNoOutbound[type="checkbox"]:checked').length == 0) {
                        message({
                            title: '温馨提示',
                            msg: '请至少选择一个出库货物',
                            type: 'alert'
                        });
                        return false;
                    }
                    $.get('../tmpl/outbound/outbound.html', function (tmpl) {
                        require('dialog');
                        $("#outbound_dialog").dialog({
                            title: "出库",
                            tmpl: tmpl,
                            onClose: function () {
                                $("#outbound_dialog").dialog("destroy");
                            },
                            render: function () {
                                $.getJSON('/admin/getCurrUserNextArea?token=' + token, function (res) {
                                    console.log(res.bizData[0].name)
                                    for (var i = 0; i < res.bizData.length; i++) {
                                        $('#dep_provinces').append('<option simpleCode="' + res.bizData[i].simpleCode + '" value="' + res.bizData[i].id + '">' + res.bizData[i].name + '</option>')
                                    }
                                });
                            },
                            buttons: [{
                                text: "出库",
                                'class': "btn btn-primary",
                                click: function () {
                                    var vali = require('./outbound_from.js');
                                    vali.validate(function (formArry) {
                                        productionDepartment(formArry, function (ret) {
                                            console.log(ret)
                                            if ('0000000' === ret.rtnCode) {
                                                willOutput(UrlConfig.getGoodsMange);
                                                $("#outbound_dialog").dialog("destroy");
                                            } else {
                                                $("#outbound_dialog").dialog("destroy");
                                                message({
                                                    title: '温馨提示',
                                                    msg: ret.msg,
                                                    type: 'alert',
                                                    clickHandle: function () {
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
                                click: function () {
                                    $("#outbound_dialog").dialog("destroy");
                                }
                            }]
                        });
                    })
                });
            },

            batchOutbound: function (elementId) {

                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function (e) {
                    $.get('../tmpl/outbound/outbound_batch.html', function (tmpl) {
                        require('dialog');
                        $("#dep_provinces_batch").dialog({
                            title: "批量出库",
                            tmpl: tmpl,
                            onClose: function () {
                                $("#dep_provinces_batch").dialog("destroy");
                            },
                            render: function () {
                                $.getJSON('/admin/getCurrUserNextArea?token=' + token, function (res) {
                                    console.log(res.bizData[0].name)
                                    for (var i = 0; i < res.bizData.length; i++) {
                                        $('#dep_provinces_batch').append('<option simpleCode="' + res.bizData[i].simpleCode + '" value="' + res.bizData[i].id + '">' + res.bizData[i].name + '</option>')
                                    }
                                });
                                $('body').on('click', '#card-area-btn', function () {
                                    var outboundBatchNum = $.trim($('#outbound_batch_num').val());
                                    if (outboundBatchNum == '' || outboundBatchNum == '0') {
                                        tip($('#dep_provinces_batch').parent().parent(), '请输入出库数量');
                                        return;
                                    }
                                    $.getJSON('/admin/outPutCardNumber?rows=' + outboundBatchNum, function (res) {
                                        console.log(res)


                                        if (res.rtnCode == "0000000") {
                                            $('#card-interval').show();
                                            var cardStart = res.bizData.start,
                                                cardEnd = res.bizData.end;
                                            $('#card-start').text(cardStart);
                                            $('#card-end').text(cardEnd);
                                        }

                                    })


                                })

                            },
                            buttons: [{
                                text: "批量出库",
                                'class': "btn btn-primary",
                                click: function () {
                                    var vali = require('./outbound_batch_from.js');
                                    vali.validate(function (formArry) {
                                        productionBatchDepartment(formArry, function (ret) {
                                            console.log(ret)
                                            if ('0000000' === ret.rtnCode) {
                                                willOutput(UrlConfig.getGoodsMange);
                                                $("#dep_provinces_batch").dialog("destroy");
                                            } else {
                                                $("#dep_provinces_batch").dialog("destroy");
                                                message({
                                                    title: '温馨提示',
                                                    msg: ret.msg,
                                                    type: 'alert',
                                                    clickHandle: function () {
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
                                click: function () {
                                    $("#dep_provinces_batch").dialog("destroy");
                                }
                            }]
                        });
                    })
                });
            },

            flowarea: function (elementId) {
                $('#' + elementId).hide();
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function (e) {
                    $.get('../tmpl/outbound/flow_area.html', function (tmpl) {
                        require('dialog');
                        $("#flow_area_dialog").dialog({
                            title: "流向地查询",
                            tmpl: tmpl,
                            onClose: function () {
                                $("#flow_area_dialog").dialog("destroy");
                            },
                            render: function () {
                                $.getJSON('/admin/getCurrUserNextArea?token=' + token, function (res) {
                                    console.log(res)
                                    for (var i = 0; i < res.bizData.length; i++) {
                                        $('#flow-area-list').append('<li><label simpleCode="' + res.bizData[i].simpleCode + '" id="' + res.bizData[i].id + '"> <input type="checkbox" >' + res.bizData[i].name + '</label></li>')
                                    }
                                });
                            },
                            buttons: [{
                                text: "确定",
                                'class': "btn btn-primary",
                                click: function () {
                                    var vali = require('./outbound_flow_area.js');
                                    vali.validate(function (formArry) {
                                        flowAreaDepartment(formArry, function (ret) {
                                            console.log(ret)
                                            if ('0000000' === ret.rtnCode) {
                                                var flowAreaArr = [];
                                                console.log($('#flow-area-list [type="checkbox"]:checked').parent().attr('simpleCode'))
                                                $('#flow-area-list [type="checkbox"]:checked').each(function () {
                                                    flowAreaArr.push($(this).parent().attr('simpleCode'));
                                                });
                                                console.log(flowAreaArr)
                                                alreadyOutput('/admin/agents?token=' + token + '&isOutput=true&area=' + flowAreaArr.join(','));
                                                $("#flow_area_dialog").dialog("destroy");
                                            } else {
                                                $("#flow_area_dialog").dialog("destroy");
                                                message({
                                                    title: '温馨提示',
                                                    msg: ret.msg,
                                                    type: 'alert',
                                                    clickHandle: function () {
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
                                click: function () {
                                    $("#flow_area_dialog").dialog("destroy");
                                }
                            }]
                        });
                    })
                });
            }


        };


        require.async('../renderResource', function (resource) {
            resource(ButtonEvent, token);
        });


        function tip(ele, str) {
            var errorLable = ele.find('p');
            errorLable.html(str);
            errorLable.show(500);
            setTimeout(function () {
                errorLable.hide(500);
            }, 2000)
        }


    }


});