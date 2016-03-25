/**
 * Created by soopxin on 2016/1/19.
 */
define(function(require, exports, module) {

    require('bootstrap');
    require('doT');
    require('cookie');
    var token = $.cookie('bizData');
    var Tool = require('../tools');

    var TableModel = {
        columnDefs:[{
            "aTargets": [4],
            "render":function(data, type, row) {
                var Stars = "";
                if(data === 0){
                    Stars = "★";
                }else if(data >= 1 && data < 3){
                    Stars = "★★";
                }else if(data >= 3 && data < 5){
                    Stars = "★★★";
                }else if(data == 5){
                    Stars = "★★★★";
                }else if(data >= 6){
                    Stars = "★★★★★";
                }
                return Stars;
            }
        }]
    };

    var TableView = {
        tableCotent: function(data) {
            return doT.template($('#more_table').html())(data);
        }
    };

    var TableController = {
        Table: null,
        column: [],
        initStatistics: function(sAjaxSource) {
            var gridType = 'grid-statistics';
            var that = this;
            if (!$('.' + gridType)[0]) {
                that.areaGridId = (+new Date()) + '_grid';
                $('#page_0').append(TableView.tableCotent({
                    type: 'statistics',
                    gridId: that.areaGridId
                }));
            }
            that.areaTable = that.Table({
                columns:that.column,
                tableContentId: that.areaGridId,
                tableId: 'table_body_' + (+new Date()),
                columnDefs: TableModel.columnDefs,
                sAjaxSource: sAjaxSource + '&token=' + token,
            });
            that.areaTable.init();
        }
    };


    var Statics = {
        options: null,
        init: function(options) {
            this.renderCondition();
            this.options = options;
            this.initSelectDate();
            this.getFixedTimeData(1);
            this.SelectDateHandle();
        },
        renderCondition: function() {
            $('#page_0').append('<div class="form-inline" id="form_search">'
                + '<div class="control-group date-condition">'
                + '<label class="control-label">时间</label>'
                + '<button type="button" class="ml5 btn btn-default btn-primary" id="yesterday">昨日</button>'
                + '<button type="button" class="ml5 btn btn-default" id="recently_7">最近7日</button>'
                + '<button type="button" class="ml5 btn btn-default" id="recently_30">最近30日</button>'
                + '<button type="button" class="ml5 btn btn-default" id="defined">自定义时间</button>'
                + '<label class="ml5 control-label">开始时间:</label>'
                + '<input size="16" type="text" readonly class="form_date" id="start_date">'
                + '<label class="ml5 control-label">结束时间:</label>'
                + '<input size="16" type="text" readonly class="form_date" id="end_date">'
                + '<span id="date_error_tip" style="margin-left:10px;color:#f00"></span>'
                + '<a id="export_excel" href="javascript:void(0)">导出excel</a>'
                + '</div>'
                + '</div>')
            var that = this;
            $('#export_excel').on('click', function(e) {
                var id = $('.date-condition button.btn-primary').attr('id');
                var getDateRange = {
                    'yesterday': that.ShowDataByDate.dateParams(1),
                    'recently_7': that.ShowDataByDate.dateParams(7),
                    'recently_30': that.ShowDataByDate.dateParams(30),
                    'defined': {
                        start: $('#start_date').val(),
                        end: $('#end_date').val()
                    }
                };

                var date = getDateRange[id];
                if (date.start && date.end) {
                    window.location.href = '/statistics/active/exportUserActiveStatistics?dateStart=' + date.start + '&dateEnd=' + date.end;
                }
            });
        },
        /**
         * 初始化时间选择框
         */
        initSelectDate: function() {
            require('datetimepicker');
            require('datetimepickerCN');
            $('#start_date').datetimepicker({
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                weekStart: 1,
                autoclose: true,
                startView: 2,
                minView: 2,
                forceParse: 0
            }).on('changeDate', function(evl) {
                $('#end_date').val('');
                var startDate = $('#start_date').val();
                $('#end_date').datetimepicker('setStartDate', startDate);
                setTimeout(function() {
                    var endDate = Tool.timeFormat(new Date(+new Date(startDate) + 30 * 24 * 60 * 60 * 1000), 'yyyy-MM-dd');
                    $('#end_date').datetimepicker('setEndDate', endDate);
                }, 100);
            });
            $('#end_date').datetimepicker({
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                weekStart: 1,
                autoclose: true,
                startView: 2,
                minView: 2,
                forceParse: 0
            });
        },
        /**
         * 计算7，30天得开始时间和结束时间
         */
        ShowDataByDate: {
            end: function() {
                return Tool.timeFormat(new Date(), 'yyyy-MM-dd');
            },
            start: function(days) {
                var now = new Date().getTime() - days * 24 * 60 * 60 * 1000;
                return Tool.timeFormat(new Date(now), 'yyyy-MM-dd');
            },
            dateParams: function(days) {
                return {
                    start: this.start(days),
                    end: this.end(days)
                }
            }
        },
        SelectDateHandle: function() {
            var that = this;
            $('#yesterday').off('click');
            $('#yesterday').on('click', function(e) {
                that.getFixedTimeData(1);
                $(this).addClass('btn-primary').siblings().removeClass('btn-primary');
            });

            $('#recently_7').off('click');
            $('#recently_7').on('click', function(e) {
                that.getFixedTimeData(7);
                $(this).addClass('btn-primary').siblings().removeClass('btn-primary');
            })
            $('#recently_30').off('click');
            $('#recently_30').on('click', function(e) {
                that.getFixedTimeData(30);
                $(this).addClass('btn-primary').siblings().removeClass('btn-primary');
            })
            $('#defined').off('click');
            $('#defined').on('click', function(e) {
                that.getSelectTimeData();
                $(this).addClass('btn-primary').siblings().removeClass('btn-primary');
            })
        },
        /**
         * 格式化URL
         * @param dateStart  开始时间
         * @param endDate  结束时间
         * @returns {string}
         */
        url: function(startDate, endDate) {
            return this.options.url + '?dateStart=' + startDate + '&dateEnd=' + endDate;
        },
        /**
         * 7, 30时间处理
         * @param days
         */
        getFixedTimeData: function(days) {
            var date = this.ShowDataByDate.dateParams(days),
                startDate = date.start,
                endDate = date.end,
                url = this.url(startDate, endDate);
            TableController.initStatistics(url);
        },
        /**
         * 自定义时间处理
         */
        getSelectTimeData: function() {
            var startDate = $('#start_date').val();
            if (!startDate) {
                $('#date_error_tip').text('开始时间为空！ ');
                return;
            }
            var endDate = $('#end_date').val();
            if (!endDate) {
                $('#date_error_tip').text('结束时间为空！ ');
                return;
            }
            var url = this.url(startDate, endDate);
            TableController.initStatistics(url);
        }
    };

    module.exports = function(options) {
        require.async('../datatable', function(Table) {
            TableController.Table = Table;
            TableController.column = options.column;
            Statics.init({
                url: options.url
            })
        });
    }
});

