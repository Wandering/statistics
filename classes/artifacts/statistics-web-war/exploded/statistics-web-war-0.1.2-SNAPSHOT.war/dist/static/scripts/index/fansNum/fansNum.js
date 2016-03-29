/**
 * Created by kepeng on 15/9/18.
 */
define('static/scripts/index/fansNum/fansNum', ['sea-modules/dot/doT.min', 'sea-modules/bootstrap/bootstrap', 'sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker', 'sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker.zh-CN', 'static/scripts/index/tools'], function(require, exports, module) {
    require('sea-modules/dot/doT.min');
    var TableModel = {
        column:[{
            data:'id',
            title:''
        },{
            data:'userName',
            title:'账号'
        },{
            data:'num',
            title:'关注人数'
        }],
        columnDefs:[{
            "bVisible": false,
            "aTargets": [0]
        }]
    };

    var TableView = {
        tableCotent: function(data) {
            return doT.template($('#more_table').html())(data);
        }
    };

    var TableController = {
        Table: null,
        removeSchool: function() {
            if ($('.grid-school')[0]) {
                $('.grid-school').remove();
            }
        },
        removeClass: function() {
            if ($('.grid-class')[0]) {
                $('.grid-class').remove();
            }
        },
        removeInfo: function() {
            if ($('.grid-info')[0]) {
                $('.grid-info').remove();
            }
        },
        /**
         * 初始化区域表
         */
        initArea: function(sAjaxSource) {
            var gridType = 'grid-area';
            var that = this;
            if (!$('.' + gridType)[0]) {
                that.areaGridId = (+new Date()) + '_grid';
                $('#page_0').append(TableView.tableCotent({
                    type: 'area',
                    gridId: that.areaGridId
                }));
            }
            that.areaTable = that.Table({
                columns:TableModel.column,
                tableContentId: that.areaGridId,
                tableId: 'table_body_' + (+new Date()),
                columnDefs: TableModel.columnDefs,
                sAjaxSource: sAjaxSource + '?dateDay=' + $('#time').val(),
                trClickHandle: function(data) {
                },
                initComplete: function() {
                    that.removeInfo();
                    that.removeClass();
                    that.removeSchool();
                }
            });
            that.areaTable.init();
        }
    };

    module.exports = function(url) {
        require.async(['static/scripts/index/datatable'], function(Table) {
            TableController.Table = Table;
            require('sea-modules/bootstrap/bootstrap');
            require('sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker');
            require('sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker.zh-CN');
            $('#time').datetimepicker({
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                weekStart: 1,
                todayBtn: 1,
                autoclose: true,
                todayHighlight: 1,
                startView: 2,
                minView: 2,
                forceParse: 0
            }).on('changeDate', function(ev) {
                TableController.initArea(url);
            });
            var Tool = require('static/scripts/index/tools');
            $('#time').val(Tool.timeFormat(new Date(), 'yyyy-MM-dd'));
            TableController.initArea(url);
        });
    };
});
