/**
 * Created by kepeng on 15/9/9.
 */
define(function(require, exports, module) {
    function tip(ele, str) {
        var errorLable = ele.find('p');
        errorLable.html(str);
        errorLable.show(500);
        setTimeout(function() {
            errorLable.hide(500);
        }, 2000)
    }

    if (typeof String.prototype.trim !== 'function') {
        String.prototype.trim = function() {
            return this.replace(/(^\s*)|(\s*$)/g,'');
        };
    }

    function validateForm(callback) {

        //if($.trim($('#outbound_batch_num').val())=='' || $.trim($('#outbound_batch_num').val())=='0'){
        //    tip($('#dep_provinces_batch').parent().parent(), '请输入出库数量');
        //    return;
        //}
        //
        //
        //var  warehouseType = $('#dep_type_batch').find('option:selected').val();
        //if(warehouseType==""){
        //    tip($('#dep_type_batch').parent().parent(), '请选择种类');
        //    return;
        //}

        if($('#card-start').text()=="" || $('#card-end').text()==""){
            tip($('#card-start').parent().parent(), '请先查询卡号区段');
            return;
        }

        var  areaV = $('#dep_provinces_batch').find('option:selected').val();
        var  simpleCode = $('#dep_provinces_batch').find('option:selected').attr('simpleCode');

        if (areaV=="") {
            tip($('#dep_provinces_batch').parent().parent(), '请选择流向地');
            return;
        }
        callback([simpleCode]);
    }



    module.exports = {
        validate: function(callback) {
            validateForm(callback);
        }
    }
});