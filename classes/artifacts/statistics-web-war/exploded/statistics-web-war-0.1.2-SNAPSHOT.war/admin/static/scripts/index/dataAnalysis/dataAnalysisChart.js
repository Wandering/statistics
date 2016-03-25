/**
 * Created by soopxin on 2015/12/25.
 */
define(function(require, exports, module) {

    module.exports = function() {
        require('datetimepicker');
        require('datetimepickerCN');
        var Tool = require('../tools');

        $('#start_date').datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            weekStart: 1,
            autoclose: true,
            startView: 2,
            minView: 2,
            forceParse: 0
        }).on('changeDate', function(evl) {
            var startDate = $('#start_date').val();
            $('#end_date').datetimepicker('setStartDate', startDate);
            setTimeout(function() {
                var endDate = Tool.timeFormat(new Date(+new Date(startDate) + 30 * 24 * 60 * 60 * 1000), 'yyyy-MM-dd');
                $('#end_date').datetimepicker('setEndDate', endDate);
            }, 100);
        });

        $('#search_btn').on('click',function(){
            $(this).addClass('btn-primary').siblings().removeClass('btn-primary');
        });

        $('#import_btn').on('click',function(){
            $(this).addClass('btn-primary').siblings().removeClass('btn-primary');
            $('#import_btn').html('正在导入');
            //$('#msg').html('');
            $.upload({
                url: '/statistics/arrearData/uploadFile1',
                fileName: 'fileUpload',
                dataType: 'json',
                onSend: function() {
                    return true;
                },
                onComplate: function(data) {
                    $('#import_btn').html('导入数据');
                    //$('#msg').html(data.bizData.msg);
                    alert(data.bizData.msg);
                }
            });
        });

        tableinit();


        $('#search_btn').on('click',function(){
            var fileName = $('#text_name').val();
            var dateDay = $('#start_date').val();
            tableinit(fileName,dateDay);
            $('#cur_date_day').hide();
        });

        function tableinit(fileName,dateDay) {
            require.async('../common/businessTable', function (businessTable) {
                businessTable({
                    fileName:fileName,
                    dateDay: dateDay,
                    url: {
                        areaUrl: '/statistics/arrearData/queryArrearImportHistory',
                    },
                    tableModel: {
                        areaColumn: [{
                            data: 'accountId',
                            title: ''
                        }, {
                            data: 'templateName',
                            title: '任务名称'
                        }, {
                            data: 'importDate',
                            title: '上传时间'
                        }, {
                            data: 'status',
                            title: '操作'
                        }, {
                            data: 'errorData',
                            title: '备注'
                        }],
                        columnDefs: [
                            {
                                "bVisible": false,
                                "aTargets": [0]
                            },
                            {
                                "aTargets": [2],
                                "render": function (data, type, row) {
                                    return Tool.timeFormat(new Date(data), 'yyyy-MM-dd hh:mm:ss');
                                }
                            },
                            {
                                "aTargets": [3],
                                "render": function (data, type, row) {
                                    if (data == '1') {
                                        return '导出报表';
                                    } else {
                                        return '<a href="/statistics/arrearData/downloadReportForm?templateId=' + row.templateId + '&fileName=' + encodeURI(encodeURI(row.templateName.split(".")[0])) + '">导出报表</a>';
                                    }
                                }
                            },
                            {
                                "aTargets": [4],
                                "render": function (data, type, row) {
                                    if (data == '0') {
                                        return '';
                                    } else {
                                        return '<a href="/statistics/arrearData/downloadErrorNumber?templateId=' + row.templateId + '&fileName=' + encodeURI(encodeURI(row.templateName.split(".")[0])) + '">错误数据</a>';
                                    }
                                }
                            }]
                    }
                });
            });
        }

    }
});
