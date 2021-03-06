define(function (require, exports, module) {
    module.exports = function () {
        //获取所需组件依赖
        require('bootstrap');
        require('cookie');
        require('dialog');
        var UrlConfig = require('../common/urlConfig');
        var message = require('../message');
        var Table = require('../datatable');

        var token = $.cookie('bizData');
        var col = [{
            data: 'id'
        }, {
            data: 'splitPercentage',
            title: '拿货代理商'
        }, {
            data: 'levelProfits1',
            title: '一级奖励'
        }, {
            data: 'levelProfits2',
            title: '二级奖励'
        }];
        var columnDefs = [{
            "bVisible": false,
            "aTargets": [0]
        }];
        var TableInstance = Table({
            columns: col,
            tableContentId: 'table_content_proportion',
            tableId: (+new Date()) + '_table_body',
            columnDefs: columnDefs,
            sAjaxSource: UrlConfig.findSeparate+'?token=' + token
        });
        TableInstance.init();
        var tableObj = TableInstance.dataTable;


        var settingDepartment = function (formArry, succCallback, id) {
            //var departmentJson = {
            //    levelProfits1: formArry[0] || '', // 一级奖励比例
            //    levelProfits2: formArry[1] || '' // 二级奖励比例
            //};

            $.ajax({
                type: 'GET',
                url: '/separateController/updateSeparate?token=' + token,
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                data: {
                    //departmentJson: JSON.stringify(departmentJson)
                    levelProfits1: formArry[0] || '', // 一级奖励比例
                    levelProfits2: formArry[1] || '' // 二级奖励比例
                },
                dataType: 'json',
                success: function (data) {
                    console.log(data)
                    succCallback(data);
                },
                beforeSend: function (xhr) {
                    $('.single-buttons').attr('disabled', 'disabled');
                },
                complete: function () {
                    $('.single-buttons').removeAttr('disabled');
                },
                error: function (data) {

                }
            });
        };

        var ButtonEvent = {
            setting: function (elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function (e) {
                    if (!tableObj) {
                        return;
                    }
                    $.get('../tmpl/proportion/proportion.tmpl', function (tmpl) {
                        $("#setting_proportion").dialog({
                            title: "分成规则",
                            tmpl: tmpl,
                            onClose: function () {
                                $("#setting_proportion").dialog("destroy");
                            },
                            render: function () {
                                $.getJSON('/separateController/findSeparate?token=' + token, function (res) {
                                    console.log(res);
                                    $('#proportion-first').val(res.bizData.levelProfits1);
                                    $('#proportion-second').val(res.bizData.levelProfits2);
                                });
                            },
                            buttons: [{
                                text: "确定",
                                'class': "btn btn-primary",
                                click: function () {
                                    var vali = require('./proportion-form.js');
                                    vali.validate(function (formArry) {
                                        settingDepartment(formArry, function (ret) {
                                            tableObj.fnDraw();
                                            console.log(ret)
                                            if ('0000000' === ret.rtnCode) {
                                                $("#setting_proportion").dialog("destroy");
                                            } else {
                                                $("#setting_proportion").dialog("destroy");
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
                                    $("#setting_proportion").dialog("destroy");
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


    }


});