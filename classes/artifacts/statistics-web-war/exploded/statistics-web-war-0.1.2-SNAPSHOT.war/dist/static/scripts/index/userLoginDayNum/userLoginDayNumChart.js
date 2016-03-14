/**
 * Created by kepeng on 15/10/8.
 */

define('static/scripts/index/userLoginDayNum/userLoginDayNumChart', ['sea-modules/bootstrap/bootstrap', 'sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker', 'sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker.zh-CN', 'static/scripts/index/chart', 'static/scripts/index/tools', 'static/scripts/index/common/ajax'], function(require, exports, module) {

    require('sea-modules/bootstrap/bootstrap');
    require('sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker');
    require('sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker.zh-CN');

    var Chart = require('static/scripts/index/chart'),
        Tool = require('static/scripts/index/tools'),
        AjaxController = require('static/scripts/index/common/ajax');

    var Controller = {
        options: null,
        init: function(options) {
            this.options = options;
            this.initSelectDate();
            this.getData(this.options.url + '?dateDay=' + $('#time').val())
        },
        /**
         * 初始化时间选择框
         */
        initSelectDate: function() {
            var that = this;
            $('#time').datetimepicker({
                language: 'zh-CN',
                format: 'yyyy-mm',
                autoclose: true,
                startView: 3,
                minView: 3,
                forceParse: 0
            }).on('changeDate', function(ev) {
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
                if ($('#cur_date_day').length) {
                    $('#cur_date_day').text('');
                }
                that.getData(that.options.url + '?dateDay=' + $('#time').val())
            });
        },
        /**
         * 获取数据
         * @param url
         */
        getData: function(url) {
            var that = this;
            AjaxController.get(url, function(ret) {
                var bizData = ret.bizData;
                var dataObj = that.options.data(bizData);
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
            });
        }
    }

    function getDateRange(index, month, year) {
        var start = 1 + 5 * index;
        var end = 5 + 5 * index;
        var lastDayOfMonth = new Date(new Date(year + '-' + (month + 1) + '-01') - 24 * 60 * 60 * 1000).getDate();
        end = end > lastDayOfMonth ? lastDayOfMonth : end;
        return {
            start: start,
            end: end
        }
    }

    module.exports = function() {

        $('#time').val(Tool.timeFormat(new Date(), 'yyyy-MM'));
        Controller.init({
            url: '/statistics/userLogin/queryUserLoginDateNum',
            handle: function(param) {
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
                require.async(['static/scripts/index/userLoginDayNum/userLoginDayNumTable'], function(realTimeDebt) {
                    var curMonth = $('#time').val();
                    var curMonthArry = curMonth.split('-');
                    var range = getDateRange(param.dataIndex, parseInt(curMonthArry[1]), parseInt(curMonthArry[0]));
                    realTimeDebt(curMonth, range.start, range.end);
                });
            },
            legendData: ['教师-APP', '家长-APP'],
            data: function(bizData) {
                var xAxisData = [],
                    teacherNum = [],
                    parentNum = [];
                for (var i = 0; i < bizData.length; i++) {
                    xAxisData.push(bizData[i].dateDay);
                    teacherNum.push(bizData[i].teacherAppNum);
                    parentNum.push(bizData[i].parentAppNum);
                }

                return {
                    xAxisData: xAxisData,
                    seriesData: [{
                        name: '教师-APP',
                        type: 'bar',
                        barMaxWidth: 30,
                        data: teacherNum
                    },{
                        name: '家长-APP',
                        type: 'bar',
                        barMaxWidth: 30,
                        data: parentNum
                    }]
                };
            }
        });
    }
});

