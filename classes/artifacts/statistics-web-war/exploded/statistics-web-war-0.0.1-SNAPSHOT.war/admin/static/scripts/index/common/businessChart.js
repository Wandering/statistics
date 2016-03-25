/**
 * Created by kepeng on 15/10/8.
 */
define(function(require, exports, module) {

    require('bootstrap');
    var Chart = require('../chart'),
        Tool = require('../tools'),
        AjaxController = require('../common/ajax');

    var Controller = {
        options: null,
        init: function(options) {
            this.options = options;
            this.initSelectDate();
            this.getFixedTimeData(7);
            this.SelectDateHandle();
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
            }).on('changeDate', function(evl) {
                var endDate = $('#end_date').val();
                $('#start_date').datetimepicker('setEndDate', endDate);
                setTimeout(function() {
                    var startDate = Tool.timeFormat(new Date(+new Date(endDate) - 30 * 24 * 60 * 60 * 1000), 'yyyy-MM-dd');
                    $('#start_date').datetimepicker('setStartDate', startDate);
                }, 100);
            });
        },
        clearDateRange: function() {
            $('#start_date').val('');
            $('#end_date').val('');
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
            $('#recently_7').off('click');
            $('#recently_7').on('click', function(e) {
                that.getFixedTimeData(7);
                $(this).addClass('btn-primary').siblings().removeClass('btn-primary');
                that.clearDateRange();
            })
            $('#recently_30').off('click');
            $('#recently_30').on('click', function(e) {
                that.getFixedTimeData(30);
                $(this).addClass('btn-primary').siblings().removeClass('btn-primary');
                that.clearDateRange();
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
            this.getData(url);
            if ($('.grid-area')[0]) {
                $('.grid-area').remove();
            }
            if ($('.grid-school')[0]) {
                $('.grid-school').remove();
            }
            if ($('.grid-class')[0]) {
                $('.grid-class').remove();
            }
            if ($('.grid-info')[0]) {
                $('.grid-info').remove();
            }
            if ($('.grid-parent')[0]) {
                $('.grid-parent').remove();
            }
            if ($('.grid-teacher')[0]) {
                $('.grid-teacher').remove();
            }
            if ($('#cur_date_day').length) {
                $('#cur_date_day').text('');
            }
        },
        /**
         * 自定义时间处理
         */
        getSelectTimeData: function() {
            var startDate = $('#start_date').val();
            if (!startDate) {
                $('#date_error_tip').text('开始时间为空！ ');
                setTimeout(function() {
                    $('#date_error_tip').text('');
                }, 2000)
                return;
            }
            var endDate = $('#end_date').val();
            if (!endDate) {
                $('#date_error_tip').text('结束时间为空！ ');
                setTimeout(function() {
                    $('#date_error_tip').text('');
                }, 2000)
                return;
            }
            var url = this.url(startDate, endDate);
            this.getData(url);
        },
        /**
         * 获取数据
         * @param url
         */
        getData: function(url) {
            var that = this;
            AjaxController.get(url, function(ret) {
                var bizData = ret.bizData;
                if (bizData && bizData.length > 0) {
                    var dataObj = that.options.data(bizData || []);
                    Chart.draw({
                        ele: 'myChart',
                        handle: that.options.handle,
                        options: {
                            title: that.options.title || '',
                            legendData: that.options.legendData,
                            type: 'category',
                            xAxisData: dataObj.xAxisData,
                            seriesData: dataObj.seriesData
                        }
                    });
                } else {
                    $('#myChart').html('<p class="no-data-chart">暂无数据~~~</p>');
                }
            });
        }
    }

    module.exports = function(options) {
        if (options) {
            Controller.init(options);
        }
    }
});
