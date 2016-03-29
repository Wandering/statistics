/**
 * Created by kepeng on 15/10/12.
 */
define('static/scripts/index/overallSituation/listSituation', ['sea-modules/dot/doT.min', 'sea-modules/bootstrap/bootstrap', 'sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker', 'sea-modules/bootstrap/datetimepicker/bootstrap-datetimepicker.zh-CN', 'static/scripts/index/tools'], function(require, exports, module) {
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
                TableController.initArea(url + '?classfyId=' + id);
            });
            var Tool = require('static/scripts/index/tools');
            $('#time').val(Tool.timeFormat(new Date(new Date().getTime() - 24 * 60 * 60 * 1000), 'yyyy-MM-dd'));
            TableController.initArea(url + '?classfyId=' + id);
        });
    };
});

