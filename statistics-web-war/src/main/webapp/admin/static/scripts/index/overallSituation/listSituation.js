/**
 * Created by kepeng on 15/10/12.
 */
define(function(require, exports, module) {
    require('doT');
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

    var TableController = {
        Table: null,
        /**
         * 初始化区域表
         */
        initArea: function(sAjaxSource) {
            var gridType = 'grid-area';
            var that = this;
            that.areaTable = that.Table({
                columns:TableModel.column,
                tableContentId: 'table_content',
                tableId: 'table_body_' + (+new Date()),
                columnDefs: TableModel.columnDefs,
                sAjaxSource: sAjaxSource + '&dateDay=' + $('#time').val()
            });
            that.areaTable.init();
        }
    };

    module.exports = function(id) {
        $('#form_search').html('<div class="control-group date-condition">'
            + '<label class="control-label">时间:</label>'
            + '<input size="16" type="text" readonly class="form_date" id="time">'
            + '<a id="export_excel" href="javascript:void(0)">导出excel</a>'
            + '</div>');
        $('#form_search').css('margin', '0');
        $('#export_excel').on('click', function(e) {
            window.location.href = '/statistics/fans/exportOpFansByClassfy?classfyId=' + id + '&dateDay=' + $('#time').val();
        });
        var url = '/statistics/fans/queryOpFansByClassfy';
        require.async('../datatable', function(Table) {
            TableController.Table = Table;
            require('bootstrap');
            require('datetimepicker');
            require('datetimepickerCN');
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
                TableController.initArea(url + '?classfyId=' + id);
            });
            var Tool = require('../tools');
            $('#time').val(Tool.timeFormat(new Date(new Date().getTime() - 24 * 60 * 60 * 1000), 'yyyy-MM-dd'));
            TableController.initArea(url + '?classfyId=' + id);
        });
    };
});

