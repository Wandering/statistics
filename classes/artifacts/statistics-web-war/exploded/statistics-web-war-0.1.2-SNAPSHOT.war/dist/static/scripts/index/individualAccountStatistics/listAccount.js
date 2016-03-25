/**
 * Created by kepeng on 15/10/12.
 */

define('static/scripts/index/individualAccountStatistics/listAccount', ['sea-modules/bootstrap/bootstrap', 'static/scripts/index/chart', 'static/scripts/index/tools', 'static/scripts/index/common/ajax', 'sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker', 'sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker.zh-CN', 'sea-modules/jquery/mCustomScrollbar/jquery.mCustomScrollbar.concat.min'], function(require, exports, module) {

    require('sea-modules/bootstrap/bootstrap');
    var Chart = require('static/scripts/index/chart'),
        Tool = require('static/scripts/index/tools'),
        AjaxController = require('static/scripts/index/common/ajax');

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
            require('sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker');
            require('sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker.zh-CN');
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
        url: function(url, startDate, endDate) {
            return url + '?dateStart=' + startDate + '&dateEnd=' + endDate + '&opAccountId=' + this.options.opAccountId;
        },
        /**
         * 7, 30时间处理
         * @param days
         */
        getFixedTimeData: function(days) {
            var date = this.ShowDataByDate.dateParams(days),
                startDate = date.start,
                endDate = date.end,
                attentionUrl = this.url(this.options.url.attention, startDate, endDate),
                articleUrl = this.url(this.options.url.article, startDate, endDate),
                activityUrl = this.url(this.options.url.activity, startDate, endDate);
            this.getDataForAttention(attentionUrl);
            this.getDataForArticle(articleUrl);
            this.getDataForActivity(activityUrl);
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
            var attentionUrl = this.url(this.options.url.attention, startDate, endDate),
                articleUrl = this.url(this.options.url.article, startDate, endDate),
                activityUrl = this.url(this.options.url.activity, startDate, endDate);
            this.getDataForAttention(attentionUrl);
            this.getDataForArticle(articleUrl);
            this.getDataForActivity(activityUrl);
        },
        /**
         * 获取数据
         * @param url
         */
        getDataForAttention: function(url) {
            var that = this;
            AjaxController.get(url, function(ret) {
                var bizData = ret.bizData;
                if (bizData && bizData.length > 0) {
                    var dataObj = that.options.data(bizData, '累计关注人数');
                    Chart.draw({
                        ele: 'myChartAttention',
                        options: {
                            title: that.options.title.attention || '',
                            legendData: that.options.legendData,
                            type: 'category',
                            xAxisData: dataObj.xAxisData,
                            seriesData: dataObj.seriesData,
                            grid: {
                                x: 80,
                                y: 40,
                                x2: 30,
                                y2: 30
                            }
                        }
                    });
                } else {
                    $('#myChartAttention').html('<p class="no-data-chart">' + (that.options.title.attention || '') + '暂无数据~~~</p>');
                }
            });
        },
        /**
         * 文章发送趋势图
         * @param url
         */
        getDataForArticle: function(url) {
            var that = this;
            AjaxController.get(url, function(ret) {
                var bizData = ret.bizData;
                if (bizData && bizData.length > 0) {
                    var dataObj = that.options.data(bizData, '文章发送数');
                    Chart.draw({
                        ele: 'myChartArticle',
                        options: {
                            title: that.options.title.article || '',
                            legendData: that.options.legendData,
                            type: 'category',
                            xAxisData: dataObj.xAxisData,
                            seriesData: dataObj.seriesData,
                            grid: {
                                x: 80,
                                y: 40,
                                x2: 30,
                                y2: 30
                            }
                        }
                    });
                } else {
                    $('#myChartArticle').html('<p class="no-data-chart">' + (that.options.title.article || '') + '暂无数据~~~</p>');
                }
            });
        },
        /**
         * 活动发送趋势图
         * @param url
         */
        getDataForActivity: function(url) {
            var that = this;
            AjaxController.get(url, function(ret) {
                var bizData = ret.bizData;
                if (bizData && bizData.length > 0) {
                    var dataObj = that.options.data(bizData, '活动发送数');
                    Chart.draw({
                        ele: 'myChartActivity',
                        options: {
                            title: that.options.title.activity || '',
                            legendData: that.options.legendData,
                            type: 'category',
                            xAxisData: dataObj.xAxisData,
                            seriesData: dataObj.seriesData,
                            grid: {
                                x: 80,
                                y: 40,
                                x2: 30,
                                y2: 30
                            }
                        }
                    });
                } else {
                    $('#myChartActivity').html('<p class="no-data-chart">' + (that.options.title.activity || '') + '暂无数据~~~</p>');
                }
            });
        }
    }

    module.exports = function(selectId) {
        $('#form_search').html($('#time_tmpl').html());
        $('#form_search').css('margin', '0');
        $('#table_content').html('<div class="chart" style="height: 280px" id="myChartAttention"></div>' +
            '<div class="chart" style="height: 280px" id="myChartArticle"></div>' +
            '<div class="chart" style="height: 280px" id="myChartActivity"></div>');
        Controller.init({
            opAccountId: selectId,
            title: {
                attention: '累计关注人数趋势图',
                article: '文章发送趋势图',
                activity: '活动发送趋势图'
            },
            url: {
                attention: '/statistics/op/getAccountFans.shtml',
                article: '/statistics/op/getAccountArticles.shtml',
                activity: '/statistics/op/getAccountEvents.shtml'
            },
            legendData: [],
            data: function(bizData, name) {
                var xAxisData = [],
                    count = [];
                for (var i = 0; i < bizData.length; i++) {
                    xAxisData.push(bizData[i].date);
                    count.push(bizData[i].count);
                }

                return {
                    xAxisData: xAxisData,
                    seriesData: [{
                        name: name,
                        type: 'line',
                        barMaxWidth: 30,
                        data: count
                    }]
                };
            }
        });
        require('sea-modules/jquery/mCustomScrollbar/jquery.mCustomScrollbar.concat.min');
        $("#content").mCustomScrollbar({
            theme: "minimal"
        });
    };
});