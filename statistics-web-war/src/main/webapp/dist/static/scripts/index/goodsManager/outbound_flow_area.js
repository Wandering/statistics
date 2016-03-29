/**
 * Created by kepeng on 15/9/9.
 */
define('static/scripts/index/goodsManager/outbound_flow_area', [], function(require, exports, module) {
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


        var flowAreaListLen =  $('#flow-area-list [type="checkbox"]:checked').length;
        if(flowAreaListLen=='0'){
            tip($('#flow-area-list').parent(), '至少选择一个流向地');
            return false;
        }


        callback([""]);
    }


    module.exports = {
        validate: function(callback) {
            validateForm(callback);
        }
    }
});