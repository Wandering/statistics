define('static/scripts/index/authority/company_list', ['sea-modules/bootstrap/bootstrap', 'sea-modules/jquery/cookie/jquery.cookie', 'static/scripts/index/message', 'static/scripts/index/datatable', 'sea-modules/jquery/dialog/jquery.dialog', 'static/scripts/index/uploadify'], function(require, exports, module) {
    module.exports = function(productCode) {
        require('sea-modules/bootstrap/bootstrap');

        require('sea-modules/jquery/cookie/jquery.cookie');
        var token = $.cookie('bizData');
        //var vali = require('./company_form.js');
        var message = require('static/scripts/index/message');
        var Table = require('static/scripts/index/datatable');
        var col = [{
            data: 'companyId'
        }, {
            data: 'companyName',
            title: '公司名称'
        }, {
            data: 'companyLogo',
            title: '公司logo'
        }, {
            data: 'companyAddress',
            title: '公司地址'
        }, {
            data: 'zipCode',
            title: '邮政编码'
        }, {
            data: 'contactPhone',
            title: '联系电话'
        }, {
            data: 'contactFax',
            title: '传真'
        }, {
            data: 'contactPerson',
            title: '联系人'
        }, {
            data: 'companyEmail',
            title: '电子邮件'
        },{
            data: 'loginNumber',
            title: '管理员'
        },{
            data:'userName',
            title:'真实姓名'
        }];

        var columnDefs = [{
            "bVisible": false,
            "aTargets": [0]
        },{
            "aTargets": [2],
            "sWidth": "18px",
            "render":function(data, type, row) {
                return '<img src="' + data + '" width="18" height="18" />';
            }
        },{
            "aTargets": [3],
            "render":function(data, type, row) {
                return '<a style="color:#fff;text-decoration:underline" href="' + data + '" target="_blank">' + data + '</a>';
            }
        },{
            "aTargets": [8],
            "render":function(data, type, row) {
                return '<a style="color:#fff;text-decoration:underline" href="Mailto:' + data + '">' + data + '</a>';
            }
        }];

        var TableInstance = Table({
            columns: col,
            tableContentId: 'table_content',
            tableId: (+new Date()) + '_table_body',
            columnDefs: columnDefs,
            sAjaxSource: '/statistics/company/queryCompany?productCode=' + productCode + '&token=' + token
        });
        TableInstance.init();
        var tableObj = TableInstance.dataTable;

        var ButtonEvent = {
            add: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    if (!tableObj) {
                        return;
                    }
                    $.get('../tmpl/company/company_form.html', function(tmpl) {
                        require('sea-modules/jquery/dialog/jquery.dialog');
                        $("#add_company").dialog({
                            title: "新增公司",
                            tmpl: tmpl,
                            onClose: function() {
                                $("#add_company").dialog("destroy");
                            },
                            render: function() {
                                setTimeout(function() {
                                    var uploadify = require('static/scripts/index/uploadify');
                                    uploadify(function(file, data, response) {
                                        //获取到data处理
                                        var obj = JSON.parse(data);
                                        if (obj.code == 200) {
                                            $('#company_logo_img').attr('src', obj.data.url);
                                            $('#company_logo').val(obj.data.url);
                                        }
                                    });
                                }, 100);
                            },
                            buttons: [{
                                text: "下一步",
                                'class': "btn btn-success step-1",
                                click: function() {
                                    vali.validateTheFirstStep(function() {
                                        var button = $('.modal-footer button.btn-success');
                                        var addButton = $('.modal-footer button.add');
                                        if (button.hasClass('step-1')) {
                                            button.removeClass('step-1');
                                            button.addClass('step-2');
                                            button.html('上一步');
                                            $('#step_title_1').removeClass('focus');
                                            $('#step_title_2').addClass('focus');
                                            var left = $('#page_body').width() / 2;
                                            addButton.show();
                                            $('#page_body').animate({
                                                left: -left + 'px'
                                            });
                                        } else if (button.hasClass('step-2')) {
                                            button.removeClass('step-2');
                                            button.addClass('step-1');
                                            button.html('下一步');
                                            $('#step_title_2').removeClass('focus');
                                            $('#step_title_1').addClass('focus');
                                            addButton.hide();
                                            $('#page_body').animate({
                                                left: 0 + 'px'
                                            });
                                        }
                                    });
                                }
                            },{
                                text: "新增",
                                'class': "btn btn-primary add custom-hide",
                                click: function() {
                                    vali.validateTheSecondStep(function(formArry) {
                                        var companyJson = {
                                            productCode: productCode,
                                            companyName: formArry[0] || '',
                                            companyShortName: formArry[1] || '',
                                            companyLogo: formArry[3] || '',
                                            companyAddress: formArry[2] || '',
                                            zipCode: formArry[4] || '',
                                            contactPhone: formArry[5] || '',
                                            contactFax: formArry[6] || '',
                                            contactPerson: formArry[7] || '',
                                            companyEmail: formArry[8] || '',
                                            userName:formArry[9] || '',
                                            loginNumber:formArry[10] || '',
                                            password:formArry[11] || '',
                                            phone:formArry[12] || '',
                                            email:formArry[13] || ''

                                        };
                                        //console.log(JSON.stringify(companyJson));
                                        $.ajax({
                                            type: 'post',
                                            url: '/statistics/company/addOrEditCompany?token=' + token,
                                            contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                                            data: {
                                                companyJson: JSON.stringify(companyJson)
                                            },
                                            dataType: 'json',
                                            success: function(data) {
                                                if ('0000000' === data.rtnCode) {
                                                    tableObj.fnDraw();
                                                    $("#add_company").dialog("destroy");
                                                } else {
                                                    $("#add_company").dialog("destroy");
                                                    message({
                                                        title: '温馨提示',
                                                        msg: data.msg,
                                                        type: 'alert',
                                                        clickHandle: function() {
                                                            if ('0100014' === data.rtnCode) {
                                                                window.location.href = 'login.html';
                                                            }
                                                        }
                                                    });
                                                }
                                            },
                                            beforeSend: function(xhr) {},
                                            error: function(data) {

                                            }
                                        });
                                    });
                                }
                            }, {
                                text: "取消",
                                'class': "btn btn-primary",
                                click: function() {
                                    $("#add_company").dialog("destroy");
                                }
                            }]
                        });
                    })
                });
            },
            update: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    if (!tableObj) {
                        return;
                    }
                    var anSelected = Table.fnGetSelected(tableObj);
                    if (anSelected.length === 0) {
                        return;
                    }
                    var aData = tableObj.fnGetData(anSelected[0]);
                    //console.log(aData)
                    $.get('/statistics/company/getCompany?id=' + aData.companyId + '&token=' + token, function(data) {
                        if ('0000000' === data.rtnCode) {
                            $.get('../tmpl/company/update_company.html', function(tmpl) {
                                require('sea-modules/jquery/dialog/jquery.dialog');
                                $("#add_company").dialog({
                                    title: "修改代理公司",
                                    tmpl: tmpl,
                                    onClose: function() {
                                        $("#add_company").dialog("destroy");
                                    },
                                    render: function() {
                                        setTimeout(function() {
                                            var uploadify = require('static/scripts/index/uploadify');
                                            uploadify(function(file, data, response) {
                                                //获取到data处理
                                                var obj = JSON.parse(data);
                                                if (obj.code == 200) {
                                                    $('#company_logo_img').attr('src', obj.data.url);
                                                    $('#company_logo').val(obj.data.url);
                                                }
                                            });
                                        }, 100);
                                        $('#company_name').val(data.bizData.companyName);
                                        $('#company_abbreviation').val(data.bizData.companyShortName);
                                        $('#company_address').val(data.bizData.companyAddress);
                                        $('#company_logo').val(data.bizData.companyLogo);
                                        $('#company_logo_img').attr('src', data.bizData.companyLogo);
                                        $('#post_code').val(data.bizData.zipCode);
                                        $('#telephone').val(data.bizData.contactPhone);
                                        $('#fax').val(data.bizData.contactFax);
                                        $('#contacts').val(data.bizData.contactPerson);
                                        $('#e_mail').val(data.bizData.email);
                                    },
                                    buttons: [{
                                        text: "修改",
                                        'class': "btn btn-primary",
                                        click: function() {
                                            vali.validateTheFirstStep(function(formArry) {
                                                var companyJson = {
                                                    companyId: aData.id,
                                                    companyName: formArry[0] || '',
                                                    companyShortName: formArry[1] || '',
                                                    companyLogo: formArry[3] || '',
                                                    companyAddress: formArry[2] || '',
                                                    zipCode: formArry[4] || '',
                                                    contactPhone: formArry[5] || '',
                                                    contactFax: formArry[6] || '',
                                                    contactPerson: formArry[7] || '',
                                                    companyEmail: formArry[8] || '',
                                                    description: formArry[9] || ''
                                                };
                                                $.ajax({
                                                    type: 'post',
                                                    url: '/statistics/company/addOrEditCompany?token=' + token,
                                                    contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                                                    data: {
                                                        companyJson: JSON.stringify(companyJson)
                                                    },
                                                    dataType: 'json',
                                                    success: function(data) {
                                                        //console.log(data)
                                                        if ('0000000' === data.rtnCode) {
                                                            tableObj.fnDraw();
                                                            $("#add_company").dialog("destroy");
                                                        } else {
                                                            message({
                                                                title: '温馨提示',
                                                                msg: data.msg,
                                                                type: 'alert',
                                                                clickHandle: function() {
                                                                    window.location.href = 'login.html';
                                                                }
                                                            });
                                                        }
                                                    },
                                                    beforeSend: function(xhr) {},
                                                    error: function(data) {

                                                    }
                                                });
                                            });
                                        }
                                    }, {
                                        text: "取消",
                                        'class': "btn btn-primary",
                                        click: function() {
                                            $("#add_company").dialog("destroy");
                                        }
                                    }]
                                });
                            })
                        }
                    });
                });
            },
            delete: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    if (!tableObj) {
                        return;
                    }
                    var anSelected = Table.fnGetSelected(tableObj);
                    if (anSelected.length !== 0) {
                        var aData = tableObj.fnGetData(anSelected[0]);
                        var str = '确认删除代理公司' + aData.companyName;
                        message({
                            title: '温馨提示',
                            msg: str,
                            type: 'alert',
                            clickHandle: function() {
                                $.get('/statistics/company/delCompany?id=' + aData.companyId + '&token=' + token, function(data) {
                                    if ('0000000' === data.rtnCode) {
                                        tableObj.fnDraw();
                                    } else {
                                        message({
                                            title: '错误提示',
                                            msg: data.msg,
                                            type: 'alert'
                                        });
                                    }
                                });
                            }
                        });

                    }
                });
            },
            distributionRight: function(elementId) {
                $('#' + elementId).off('click');
                $('#' + elementId).on('click', function(e) {
                    if (!tableObj) {
                        return;
                    }
                    var anSelected = TableInstance.fnGetSelected(tableObj);
                    if (anSelected.length === 0) {
                        return;
                    }
                    var aData = tableObj.fnGetData(anSelected[0]);

                    $.ajax({
                        type: 'post',
                        url: '/statistics/company/companyAuthority?token=' + token,
                        contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                        dataType: 'json',
                        data: {
                           companyCode: aData.companyCode
                        },
                        success: function(data) {
                            if ('0000000' === data.rtnCode) {
                                message({
                                    title: '温馨提示',
                                    msg: '分配成功',
                                    type: 'alert'
                                });
                            } else {
                                message({
                                    title: '错误提示',
                                    msg: data.msg,
                                    type: 'alert'
                                });
                            }
                        }
                    });
                });
            }
        };

        require.async(['static/scripts/index/renderResource'], function(resource) {
            resource(ButtonEvent, token);
        });
    }
});