/**
 * Created by shixin on 2015/12/26.
 */
define('static/scripts/index/arrearComparData/arrearComparDataChart', function(require, exports, module) {
    module.exports = function() {
        require('datetimepicker');
        require('datetimepickerCN');
        var message = require('static/scripts/index/message');
        $('#start_date1,#start_date2').datetimepicker({
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

        $('#contrast_btn').on('click',function(){

            var dateStart = $('#start_date1').val();
            var dateEnd = $('#start_date2').val();
            if(!dateStart || !dateEnd){
                message({
                    title:'温馨提示',
                    msg: '日期不能为空！',
                    type:'alert',
                    clickHandle: function() {
                    }
                });
                return;
            }

            require.async(['static/scripts/index/common/businessTable'], function(businessTable) {
                businessTable({
                    dateStart: dateStart,
                    dateEnd:dateEnd,
                    paging:false,
                    sInfoFiltered:'',
                    sInfoEmpty:true,
                    url: {
                        areaUrl: '/statistics/arrearData/arrearComparData',
                        deviceStatus1Url:'/statistics/arrearData/queryArrearInfoByPage',
                        deviceStatus2Url:'/statistics/arrearData/queryArrearInfoByPage',
                        Effective1Url:'/statistics/arrearData/queryArrearInfoByPage',
                        Effective2Url:'/statistics/arrearData/queryArrearInfoByPage'
                    },
                    showType:4,
                    tableModel: {
                        areaColumn: [{
                            data: 'areaId',
                            title: ''
                        }, {
                            data: 'areaName',
                            title: '区域'
                        }, {
                            data: 'dateStartTollNo',
                            title: dateStart+'用户数'
                        }, {
                            data: 'dateEndTollNo',
                            title: dateEnd+'用户数'
                        }, {
                            data: 'addTollNo',
                            title: '净增数'
                        }, {
                            data: 'dateStartEffective',
                            title: dateStart+'有效数'
                        }, {
                            data: 'dateEndEffective',
                            title: dateEnd+'有效数'
                        }, {
                            data: 'addEffective',
                            title: '净增有效数'
                        }],
                        deviceStatus1Column: [{
                            data: 'userName',
                            title: '用户'
                        }, {
                            data: 'areaName',
                            title: '区域'
                        }, {
                            data: 'shoolName',
                            title: '学校'
                        }, {
                            data: 'className',
                            title: '班级'
                        }, {
                            data: 'loginNumber',
                            title: '登录账号'
                        },{
                            data: 'userName',
                            title: '家长姓名'
                        }, {
                            data: 'payNumber',
                            title: '计费号码'
                        },{
                            data: 'billingType',
                            title: '计费类型'
                        }, {
                            data: 'arrearAge',
                            title: '欠费月份'
                        }, {
                            data: 'arrearAmount',
                            title: '欠费金额'
                        }],
                        deviceStatus2Column: [{
                            data: 'userName',
                            title: '用户'
                        }, {
                            data: 'areaName',
                            title: '区域'
                        }, {
                            data: 'shoolName',
                            title: '学校'
                        }, {
                            data: 'className',
                            title: '班级'
                        }, {
                            data: 'loginNumber',
                            title: '登录账号'
                        },{
                            data: 'userName',
                            title: '家长姓名'
                        }, {
                            data: 'payNumber',
                            title: '计费号码'
                        },{
                            data: 'billingType',
                            title: '计费类型'
                        }, {
                            data: 'arrearAge',
                            title: '欠费月份'
                        }, {
                            data: 'arrearAmount',
                            title: '欠费金额'
                        }],
                        Effective1Column: [{
                            data: 'userName',
                            title: '用户'
                        }, {
                            data: 'areaName',
                            title: '区域'
                        }, {
                            data: 'shoolName',
                            title: '学校'
                        }, {
                            data: 'className',
                            title: '班级'
                        }, {
                            data: 'loginNumber',
                            title: '登录账号'
                        },{
                            data: 'userName',
                            title: '家长姓名'
                        }, {
                            data: 'payNumber',
                            title: '计费号码'
                        },{
                            data: 'billingType',
                            title: '计费类型'
                        }, {
                            data: 'arrearAge',
                            title: '欠费月份'
                        }, {
                            data: 'arrearAmount',
                            title: '欠费金额'
                        }],
                        Effective2Column: [{
                            data: 'userName',
                            title: '用户'
                        }, {
                            data: 'areaName',
                            title: '区域'
                        }, {
                            data: 'shoolName',
                            title: '学校'
                        }, {
                            data: 'className',
                            title: '班级'
                        }, {
                            data: 'loginNumber',
                            title: '登录账号'
                        },{
                            data: 'userName',
                            title: '家长姓名'
                        }, {
                            data: 'payNumber',
                            title: '计费号码'
                        },{
                            data: 'billingType',
                            title: '计费类型'
                        }, {
                            data: 'arrearAge',
                            title: '欠费月份'
                        }, {
                            data: 'arrearAmount',
                            title: '欠费金额'
                        }],
                        columnDefs: [
                            {
                                "bVisible": false,
                                "aTargets": [0]
                            }
                        ]
                    }
                });
            });

            $('#export_excel').attr('href','/statistics/arrearData/downloadAnalysisExcel?dateStart='+dateStart+'&dateEnd='+dateEnd).hide();

        });
    }

});
