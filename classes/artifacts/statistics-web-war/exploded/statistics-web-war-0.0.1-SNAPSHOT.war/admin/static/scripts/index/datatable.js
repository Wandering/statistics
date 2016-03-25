define(function (require, exports, module) {
    require('dataTables');
    require('bootstrap');
    var message = require('./message.js');

    $.fn.dataTableExt.oStdClasses.sPaging = 'dataTables_paginate paging_bootstrap paging_custom';
    jQuery.fn.dataTableExt.oSort['string-case-asc'] = function (x, y) {
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    };

    jQuery.fn.dataTableExt.oSort['string-case-desc'] = function (x, y) {
        return ((x < y) ? 1 : ((x > y) ? -1 : 0));
    };

    function Table(options) {
        this.searchHandle = options.searchHandle; //搜索按钮处理事件
        this.buttonHandle = options.buttonHandle; //tr中按钮的处理事件
        this.trClickHandle = options.trClickHandle; //tr点击事件
        this.tableId = options.tableId;
        this.tableContentId = options.tableContentId;
        this.data = options.data;
        this.dataTable = null;
        this.columns = options.columns;
        this.columnDefs = options.columnDefs;
        this.sAjaxSource = options.sAjaxSource;
        this.initComplete = options.initComplete;
        this.paging = options.paging;
        this.sInfoFiltered = options.sInfoFiltered;
        if (options.sInfoEmpty) {
            this.sInfoEmpty = '';
        } else {
            this.sInfoEmpty = "没有数据";
        }
    }

    Table.prototype = {
        constructor: Table,
        /**
         * 展示右边内容的数据
         * @param {Object} data  展示的数据
         * @param {Object} columns  需要展示的列数
         * @param {object} tableContentId 显示表数据的容器
         */
        init: function (options) {
            $('#' + this.tableContentId).html('<table cellpadding="0" cellspacing="0" border="0" class="display" id="' + this.tableId + '"></table>');
            var that = this;
            this.dataTable = $('#' + this.tableId).dataTable({
                "data": this.data || [],
                "searching": false,
                "ordering": false,
                "paging": this.paging, //分页
                "lengthMenu": [[5, 10, 15], [5, 10, 15]],
                "language": {
                    "sLengthMenu": "每页显示 _MENU_ 条记录",
                    "sZeroRecords": "抱歉， 没有找到相关数据",
                    "sInfo": "从 _START_ 到 _END_ / 共 _TOTAL_ 条数据",
                    "sInfoEmpty": this.sInfoEmpty,
                    "sInfoFiltered": this.sInfoFiltered,
                    "sProcessing": "正在加载数据...",
                    "sProcessing": "<img src='/dist/static/images/loader.gif' />", //这里是给服务器发请求后到等待时间显示的 加载gif,
                    "oPaginate": {
                        "sFirst": "首页",
                        "sPrevious": "前一页",
                        "sNext": "后一页",
                        "sLast": "尾页"
                    }
                },
                "columns": this.columns,
                "columnDefs": this.columnDefs,
                "sZeroRecords": "没有检索到数据",
                "bProcessing": true,
                "bServerSide": true,
                "sAjaxSource": this.sAjaxSource || '',
                "fnServerData": this.retrieveData,
                "fnInitComplete": function (oSettings, json) { //表格初始化完成后调用
                    require('mCustomScrollbar');
                    $("#content").mCustomScrollbar({
                        theme: "minimal"
                    });
                },
                "fnDrawCallback": function () {
                    that.eventHandle();
                }
            }).removeClass('display').addClass('table table-datatable table-custom').on('page.dt', function () {
                if (typeof that.initComplete === 'function') {
                    that.initComplete();
                }
            });
        },
        retrieveData: function (sSource, aoData, fnCallback) {
            var newData = {};
            for (var i = 0, len = aoData.length; i < len; i++) {
                if ('sEcho' === aoData[i].name ||
                    'iDisplayStart' === aoData[i].name ||
                    'iDisplayLength' === aoData[i].name) {
                    newData[aoData[i].name] = aoData[i].value;
                }
            }
            newData.currentPageNo = newData.iDisplayStart / newData.iDisplayLength + 1;
            newData.pageSize = newData.iDisplayLength;
            var that = this;
            $.ajax({
                "type": "get",
                "contentType": "application/json",
                "url": sSource,
                "dataType": "json",
                "data": newData,
                "success": function (resp) {
                    var data = resp;
                    //console.info('resp', resp);
                    if ('0000000' === data.rtnCode) {
                        var list = data.bizData.list;
                        for(var i = 0,len = list.length; i < len; i++) {
                            list[i].index = newData.pageSize * (newData.currentPageNo - 1) + i + 1;
                        }
                        var finallyData = {
                            sEcho: newData.sEcho,
                            iTotalRecords: data.bizData.count,
                            iTotalDisplayRecords: data.bizData.count,
                            aaData: list
                        };
                        fnCallback(finallyData);
                    } else if ('0100014' === data.rtnCode || '0100015' === data.rtnCode || '0100022' === data.rtnCode || '0100023' === data.rtnCode) {
                        message({
                            title: '温馨提示',
                            msg: data.msg,
                            type: 'alert',
                            clickHandle: function () {
                            }
                        });
                        var finallyData = {
                            sEcho: newData.sEcho,
                            iTotalRecords: 0,
                            iTotalDisplayRecords: 0,
                            aaData: []
                        };
                        fnCallback(finallyData);
                    } else if ('0100008' === data.rtnCode) {
                        message({
                            msg: data.msg,
                            clickHandle: function () {
                                window.location.href = 'http://setting.jx.xy189.cn/dist/app/login.html';
                            }
                        });
                        $('.modal-header .close').off('click');
                    } else {
                        var finallyData = {
                            sEcho: newData.sEcho,
                            iTotalRecords: 0,
                            iTotalDisplayRecords: 0,
                            aaData: []
                        };
                        fnCallback(finallyData);
                    }
                }
            });
        },
        eventHandle: function () {
            var that = this;
            $('#' + this.tableId + ' tbody tr').off('click');
            $('#' + this.tableId + ' tbody tr').on('click', function (e) {
                if ($(this).hasClass('row_selected')) {
                    $(this).removeClass('row_selected');
                } else {
                    that.dataTable.$('tr.row_selected').removeClass('row_selected');
                    $(this).addClass('row_selected');
                }
                if (that.trClickHandle && typeof that.trClickHandle === 'function') {
                    if ($(this).hasClass('row_selected')) {
                        that.trClickHandle(that.selectedRecord());
                    }
                }
                e.stopPropagation();
            });

            if (that.buttonHandle && typeof that.buttonHandle === 'function') {
                $('.td-button').off('click');
                $('.td-button').on('click', function (e) {
                    that.buttonHandle();
                    e.stopPropagation();
                });
            }
        },
        fnGetSelected: function (oTable01Local) {
            return oTable01Local.$('tr.row_selected');
        },
        /**
         * 处理选择的数据
         * @returns {Array}
         */
        selectedRecord: function () {
            var anSelected = this.fnGetSelected(this.dataTable);
            var aData = [];
            if (anSelected.length > 0) {
                for (var i = 0, len = anSelected.length; i < len; i++) {
                    aData.push(this.dataTable.fnGetData(anSelected[i]));
                }
            }
            return aData;
        }
    };

    module.exports = function (options) {
        return new Table(options);
    };
});