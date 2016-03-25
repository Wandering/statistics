/**
 * Created by shixin on 15/12/22.
 */
define('static/scripts/index/arrearageImport/arrearageImportChart', [], function(require, exports, module) {
    module.exports = function(){

        var importbtn = '<div>提示：<br/>1：请选择 Excel 2007-2010 文件'
            + '<br/>2：上传时间较长，请耐心等待<br/>'
            + '<label class="control-label">欠费数据导入：</label>'
            + '<br/><input id="importbtn" class="btn btn-default btn-primary" type="button" value="导入" /></div><div id="msg"></div>';
        $('#page_0').html(importbtn);
        $('#importbtn').on('click',function(){
            $('#importbtn').val('正在上传');
            $('#msg').html('');
            $.upload({
                url: '/statistics/billing/uploadFile',
                fileName: 'fileUpload',
                dataType: 'json',
                onSend: function() {
                    return true;
                },
                onComplate: function(data) {
                    $('#importbtn').val('导入');
                    $('#msg').html(data.bizData.msg);
                }
            });
        });

    };

});