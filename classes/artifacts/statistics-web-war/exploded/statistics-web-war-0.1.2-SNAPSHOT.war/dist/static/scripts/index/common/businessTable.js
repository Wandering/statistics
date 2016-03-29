/**
 * Created by kepeng on 15/9/18.
 */
define('static/scripts/index/common/businessTable', ['sea-modules/dot/doT.min', 'sea-modules/jquery/cookie/jquery.cookie'], function(require, exports, module) {
    require('sea-modules/dot/doT.min');

    require('sea-modules/jquery/cookie/jquery.cookie');
    var token = $.cookie('bizData');

    var TableView = {
        tableCotent: function(data) {
            return doT.template($('#more_table').html())(data);
        }
    };

    var TableController = {
        Table: null,
        dateDay: '',
        dateStart: '',
        dateEnd:'',
        fileName:'',
        url: null,
        tableModel: null,
        method: null,
        showType:'',
        paging:true,
        sInfoFiltered:'(从 _MAX_ 条数据中检索)',
        sInfoEmpty:'没有数据',
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
        init: function(options) {
            this.dateDay = options.dateDay;
            this.dateStart = options.dateStart;
            this.dateEnd = options.dateEnd;
            this.method = options.method;
            this.url = options.url;
            this.tableModel = options.tableModel;
            this.showType = options.showType;
            this.fileName = options.fileName;
            this.paging = options.paging;
            this.sInfoFiltered = options.sInfoFiltered;
            this.sInfoEmpty = options.sInfoEmpty;
            if (this.url.areaUrl) {
                this.initArea();
            }
        },
        /**
         * 初始化区域表
         */
        initArea: function() {
            var gridType = 'grid-area';
            var that = this;
            if (that.dateDay || typeof that.method === 'function') {
                if ($('#cur_date_day').length) {
                    $('#cur_date_day').text(that.dateDay || that.method());
                } else {
                    $('#page_0').append('<div id="cur_date_day">' + (that.dateDay || that.method()) + '</div>')
                }
            }
            if (!$('.' + gridType)[0]) {
                that.areaGridId = (+new Date()) + '_grid';
                $('#page_0').append(TableView.tableCotent({
                    type: 'area',
                    gridId: that.areaGridId
                }));
            }
            var tmpUrl = that.url.areaUrl;
            if (tmpUrl.indexOf('?') > -1) {
                tmpUrl += '&token=' + token;
            } else {
                tmpUrl += '?token=' + token;
            }
            tmpUrl = that.dateDay ? tmpUrl + '&dateDay=' + that.dateDay : tmpUrl;
            tmpUrl = that.dateStart ? tmpUrl + '&dateStart=' + that.dateStart : tmpUrl;
            tmpUrl = that.dateEnd ? tmpUrl + '&dateEnd=' + that.dateEnd : tmpUrl;
            tmpUrl = that.fileName ? tmpUrl + '&fileName=' + that.fileName : tmpUrl;
            that.areaTable = that.Table({
                paging:that.paging,
                sInfoFiltered:that.sInfoFiltered,
                sInfoEmpty:that.sInfoEmpty,
                columns: that.tableModel.areaColumn,
                tableContentId: that.areaGridId,
                tableId: 'table_body_' + (+new Date()),
                columnDefs: that.tableModel.columnDefs,
                sAjaxSource: tmpUrl,
                trClickHandle: function(data) {
                    if (!data[0]) {
                        return;
                    }
                    var areaId = data[0].areaId;
                    if (that.url.schoolUrl) {
                        that.removeInfo();
                        that.removeClass();
                        that.initSchool('school', that.url.schoolUrl, areaId);
                    }
                    if(that.showType == 4){
                        var templateid1 = $('#export_excel').attr('templateid1');
                        var templateid2 = $('#export_excel').attr('templateid2');
                        that.debtDetaileInfo('deviceStatus1', that.url.deviceStatus1Url, areaId,that.dateStart,templateid1,'');
                        that.debtDetaileInfo('deviceStatus2', that.url.deviceStatus2Url, areaId,that.dateEnd,templateid2,'');
                        that.debtDetaileInfo('Effective1', that.url.Effective1Url, areaId,that.dateStart,templateid1,'effective');
                        that.debtDetaileInfo('Effective2', that.url.Effective2Url, areaId,that.dateEnd,templateid2,'effective');
                    }
                },
                initComplete: function() {
                    that.removeInfo();
                    that.removeClass();
                    that.removeSchool();
                }
            });
            that.areaTable.init();
        },
        /**
         * 初始化学校表
         * @param data
         */
        initSchool: function(type, sAjaxSource, areaId) {
            var gridType = 'grid-' + type;
            var that = this;
            if (!$('.' + gridType)[0]) {
                that.schoolGridId = (+new Date()) + '_grid';
                $('#page_0').append(TableView.tableCotent({
                    type: type,
                    gridId: that.schoolGridId
                }));
            }
            var tmpUrl = sAjaxSource;
            if (tmpUrl.indexOf('?') > -1) {
                tmpUrl += '&token=' + token + '&areaId=' + areaId;
            } else {
                tmpUrl += '?token=' + token + '&areaId=' + areaId;
            }
            tmpUrl = this.dateDay ? tmpUrl + '&dateDay=' + this.dateDay : tmpUrl;
            that.schoolTable = that.Table({
                columns: that.tableModel.schoolColumn,
                sInfoEmpty:that.sInfoEmpty,
                tableContentId: that.schoolGridId,
                tableId: 'table_body_' + (+new Date()),
                columnDefs: that.tableModel.columnDefs,
                sAjaxSource: tmpUrl,
                trClickHandle: function(data) {
                    if (that.url.classUrl) {
                        if (!data[0]) {
                            return;
                        }
                        var schoolId = data[0].schoolId;
                        that.initClass('class', that.url.classUrl, schoolId);
                    }
                },
                initComplete: function() {
                    if ($('.grid-class')[0]) {
                        $('.grid-class').remove();
                    }
                }
            });
            that.schoolTable.init();
        },
        /**
         * 初始化班级表
         * @param data
         */
        initClass: function(type, sAjaxSource, schoolId) {
            var gridType = 'grid-' + type;
            var that = this;
            if (!$('.' + gridType)[0]) {
                that.classGridId = (+new Date()) + '_grid';
                $('#page_0').append(TableView.tableCotent({
                    type: type,
                    gridId: that.classGridId
                }));
            }

            var tmpUrl = sAjaxSource;
            if (tmpUrl.indexOf('?') > -1) {
                tmpUrl += '&token=' + token + '&schoolId=' + schoolId;
            } else {
                tmpUrl += '?token=' + token + '&schoolId=' + schoolId;
            }
            tmpUrl = this.dateDay ? tmpUrl + '&dateDay=' + this.dateDay : tmpUrl;
            that.classTable = that.Table({
                columns: that.tableModel.classColumn,
                sInfoEmpty:that.sInfoEmpty,
                tableContentId: that.classGridId,
                tableId: 'table_body_' + (+new Date()),
                columnDefs: that.tableModel.columnDefs,
                sAjaxSource: tmpUrl,
                trClickHandle: function(data) {
                    if (that.url.detailUrl) {
                        if (!data[0]) {
                            return;
                        }
                        var classId = data[0].classId;
                        if(that.showType == 2){
                            that.debtDetaileInfo('parent', that.url.parentUrl, classId);
                            that.debtDetaileInfo('teacher', that.url.teacherUrl, classId);
                        }else {
                            that.debtDetaileInfo('info', that.url.detailUrl, classId);
                        }
                    }
                }
            });
            that.classTable.init();
        },
        /**
         * 有关班级信息的详细信息
         * @param type
         * @param sAjaxSource
         * @param classId
         */
        debtDetaileInfo: function(type, sAjaxSource,areaId,fileName,templateid,dataType,export_excel) {
            var gridType = 'grid-' + type;
            var columnType = type+'Column';
            var tableType = type+'Table';
            var gridIdType = type+'GridId';
            var gridType_exce = gridType+'_exce';
            var that = this;
            if(templateid) {
                var dataName = dataType?"有效数":"用户数";
                var href = '/statistics/arrearData/exportArrearDatail?templateId=' + templateid + '&fileName=' + fileName + '&areaId=' + areaId + '&dataType=' + dataType;
                if(!$('#'+gridType_exce)[0]) {
                    var a = '<div style="width: 100%; overflow: hidden;"><div style="float: left;  width: 50%; padding-left: 55px; color: #ffffff;">' + fileName + '' + dataName + '</div><div style="float: left; width: 50%; text-align: right; padding: 0px 55px 15px 0px;">' +
                        '<a id="' + gridType_exce + '" href="' + href + '"' +
                        ' style=" color: #ffffff;">导出信息</a></div></div>';
                    $('#page_0').append(a);
                }else{
                    $('#'+gridType_exce).attr('href',href);
                }
            }
            if (!$('.' + gridType)[0]) {
                that[gridIdType] = (+new Date()) + '_grid';
                $('#page_0').append(TableView.tableCotent({
                    type: type,
                    gridId: that[gridIdType]
                }));
            }
            var tmpUrl = sAjaxSource;
            if (tmpUrl.indexOf('?') > -1) {
                tmpUrl += '&token=' + token;
            } else {
                tmpUrl += '?token=' + token;
            }
            if(templateid){
                tmpUrl += '&areaId=' + areaId;
            }
            else{
                tmpUrl += '&classId=' + areaId;
            }
            tmpUrl = this.dateDay ? tmpUrl + '&dateDay=' + this.dateDay : tmpUrl;
            if(that.showType == 4){
                tmpUrl += '&templateId='+templateid+'&dataType='+dataType;
            }
            that[tableType] = that.Table({
                columns: that.tableModel[columnType],
                sInfoEmpty:that.sInfoEmpty,
                tableContentId: that[gridIdType],
                tableId: 'table_body_' + (+new Date()),
                columnDefs: that.tableModel.columnDefs,
                sAjaxSource: tmpUrl,
                trClickHandle: function(data) {

                }
            });
            that[tableType].init();
        }
    };

    module.exports = function(options) {
        require.async(['static/scripts/index/datatable'], function(Table) {
            TableController.Table = Table;
            TableController.init(options);
        });
    };
});